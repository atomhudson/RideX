package com.RideX.carpooling.controller;

import com.RideX.carpooling.forms.SearchRideForm;
import com.RideX.carpooling.model.Rides;
import com.RideX.carpooling.repositories.RideRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class SearchController {

    private final Logger logger = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    private RideRepository rideRepository;

    @GetMapping("/searchRide")
    public String searchRide(Model model) {
        SearchRideForm form = new SearchRideForm();
        model.addAttribute("searchForm", form);

        Date todayStart = Date.from(java.time.LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Sort sort = Sort.by("startTime").ascending();
        List<Rides> allUpcoming = rideRepository.findAllUpcomingWithDriver(todayStart, sort);
        List<Rides> activeRides = allUpcoming.stream()
                .filter(r -> "PLANNED".equalsIgnoreCase(r.getRideStatus()) || "ONGOING".equalsIgnoreCase(r.getRideStatus()))
                .collect(Collectors.toList());

        // Fallback if no future rides exist in dev DB: show all active rides so user immediately sees available rides
        if (activeRides.isEmpty()) {
            activeRides = rideRepository.findAll(sort).stream()
                    .filter(r -> "PLANNED".equalsIgnoreCase(r.getRideStatus()) || "ONGOING".equalsIgnoreCase(r.getRideStatus()))
                    .collect(Collectors.toList());
        }

        populateFilterCounts(model, activeRides);
        model.addAttribute("matchedRides", activeRides);
        return "fragments/searchRide";
    }

    @PostMapping("/process-search")
    public String processSearchRide(
            @ModelAttribute("searchForm") SearchRideForm form,
            @RequestParam(value = "sortBy", defaultValue = "startTime") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            Model model) {
        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        
        String rawFrom = form.getFrom() != null ? form.getFrom().trim() : "";
        String rawTo = form.getTo() != null ? form.getTo().trim() : "";
        
        // Strip common noise like trailing ", India", ", IN"
        String from = cleanLocationInput(rawFrom);
        String to = cleanLocationInput(rawTo);

        Date searchDate = form.getDate();
        boolean hasDateFilter = (searchDate != null);
        if (searchDate == null) {
            searchDate = Date.from(java.time.LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
            form.setDate(searchDate);
        }

        logger.info("Search requested: from='{}' (clean: '{}'), to='{}' (clean: '{}'), date='{}'", 
                rawFrom, from, rawTo, to, searchDate);

        // Step 1: Query exact string match with date
        List<Rides> matchedRides = rideRepository.findUpcomingRides(from, to, searchDate, sort);

        // Step 2: If empty and input contains commas (e.g. "Connaught Place, New Delhi"), extract primary city token
        if (matchedRides.isEmpty() && (from.contains(",") || to.contains(","))) {
            String fromToken = extractCityToken(from);
            String toToken = extractCityToken(to);
            logger.info("Retrying with primary city tokens: fromToken='{}', toToken='{}'", fromToken, toToken);
            matchedRides = rideRepository.findUpcomingRides(fromToken, toToken, searchDate, sort);
        }

        // Step 3: If empty, try without date restriction (if origin or destination specified)
        if (matchedRides.isEmpty() && (!from.isEmpty() || !to.isEmpty())) {
            logger.info("Retrying without date restriction for from='{}', to='{}'", from, to);
            matchedRides = rideRepository.findUpcomingRides(from, to, null, sort);
            if (matchedRides.isEmpty() && (from.contains(",") || to.contains(","))) {
                matchedRides = rideRepository.findUpcomingRides(extractCityToken(from), extractCityToken(to), null, sort);
            }
            if (!matchedRides.isEmpty()) {
                model.addAttribute("searchNotice", "No scheduled rides found on your chosen date. Showing upcoming rides for this route:");
            }
        }

        // Step 4: If still empty, try flexible matching (either pickup OR drop-off matches)
        if (matchedRides.isEmpty() && (!from.isEmpty() || !to.isEmpty())) {
            String fromToken = extractCityToken(from);
            String toToken = extractCityToken(to);
            matchedRides = rideRepository.findUpcomingRidesFlexible(fromToken, toToken, null, sort);
            if (!matchedRides.isEmpty()) {
                model.addAttribute("searchNotice", "No direct route found. Here are nearby active rides connecting your corridor:");
            }
        }

        // Step 5: If still empty and both from and to were empty or no rides matched, show all active rides
        if (matchedRides.isEmpty()) {
            Date todayStart = Date.from(java.time.LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
            matchedRides = rideRepository.findAllUpcomingWithDriver(todayStart, sort);
            if (matchedRides.isEmpty()) {
                matchedRides = rideRepository.findAll(sort);
            }
            if (!matchedRides.isEmpty() && (!from.isEmpty() || !to.isEmpty())) {
                model.addAttribute("searchNotice", "No rides matched '" + (from.isEmpty() ? to : from) + "'. Here are all available upcoming rides across India:");
            }
        }

        List<Rides> filteredRides = matchedRides.stream()
                .filter(r -> "PLANNED".equalsIgnoreCase(r.getRideStatus()) || "ONGOING".equalsIgnoreCase(r.getRideStatus()))
                .collect(Collectors.toList());

        logger.info("Final search results returned: {} active rides", filteredRides.size());

        populateFilterCounts(model, filteredRides);

        model.addAttribute("searchForm", form);
        model.addAttribute("matchedRides", filteredRides);

        return "fragments/searchRide";
    }

    private String cleanLocationInput(String input) {
        if (input == null) return "";
        String s = input.trim();
        // Remove trailing ", India" or ", Bharat"
        if (s.toLowerCase().endsWith(", india")) {
            s = s.substring(0, s.length() - 7).trim();
        } else if (s.toLowerCase().endsWith(", in")) {
            s = s.substring(0, s.length() - 4).trim();
        }
        return s;
    }

    private String extractCityToken(String location) {
        if (location == null || location.trim().isEmpty()) return "";
        String[] parts = location.split(",");
        if (parts.length == 1) return parts[0].trim();
        // If there are multiple parts, often the first part is a landmark or city,
        // or the second part is the city (e.g. "Sector 62, Noida" -> "Noida").
        // Return the first part that is likely a city name.
        String first = parts[0].trim();
        if (parts.length > 1) {
            String second = parts[1].trim();
            // If first part has "sector" or "street" or "colony", second part is likely the city
            String lowerFirst = first.toLowerCase();
            if (lowerFirst.contains("sector") || lowerFirst.contains("road") || lowerFirst.contains("street") || lowerFirst.contains("phase") || lowerFirst.contains("block")) {
                return second;
            }
        }
        return first;
    }

    private void populateFilterCounts(Model model, List<Rides> rides) {
        long acCount = rides.stream().filter(Rides::isAcAvailable).count();
        long luggageCount = rides.stream().filter(Rides::isLuggageAllowed).count();
        long smokingCount = rides.stream().filter(Rides::isAllowSmoking).count();
        long petsCount = rides.stream().filter(Rides::isAllowPets).count();

        long morningCount = rides.stream()
                .filter(r -> {
                    if (r.getStartTime() == null) return false;
                    LocalTime time = r.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
                    return !time.isBefore(LocalTime.of(6, 0)) && !time.isAfter(LocalTime.of(12, 0));
                }).count();

        long afternoonCount = rides.stream()
                .filter(r -> {
                    if (r.getStartTime() == null) return false;
                    LocalTime time = r.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
                    return time.isAfter(LocalTime.of(12, 0)) && !time.isAfter(LocalTime.of(18, 0));
                }).count();

        long eveningCount = rides.stream()
                .filter(r -> {
                    if (r.getStartTime() == null) return false;
                    LocalTime time = r.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
                    return time.isAfter(LocalTime.of(18, 0));
                }).count();

        model.addAttribute("morningCount", morningCount);
        model.addAttribute("afternoonCount", afternoonCount);
        model.addAttribute("eveningCount", eveningCount);

        model.addAttribute("acCount", acCount);
        model.addAttribute("luggageCount", luggageCount);
        model.addAttribute("smokingCount", smokingCount);
        model.addAttribute("petsCount", petsCount);
    }
}
