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
//        model.addAttribute("matchedRides", new ArrayList<>());
        return "fragments/searchRide";
    }

    @PostMapping("/process-search")
    public String processSearchRide(
            @ModelAttribute("searchForm") SearchRideForm form,
            @RequestParam(value = "sortBy", defaultValue = "startTime") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            Model model) {
        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        List<Rides> matchedRides = rideRepository.findUpcomingRides(form.getFrom(), form.getTo(), form.getDate(), sort);
        // Filter only PLANNED or ONGOING rides
        List<Rides> filteredRides = matchedRides.stream()
                .filter(r -> r.getRideStatus().equals("PLANNED") || r.getRideStatus().equals("ONGOING"))
                .collect(Collectors.toList());

        logger.info("filteredRides: {}", filteredRides);

        // Now compute counts from filteredRides only
        long acCount = filteredRides.stream().filter(Rides::isAcAvailable).count();
        long luggageCount = filteredRides.stream().filter(Rides::isLuggageAllowed).count();
        long smokingCount = filteredRides.stream().filter(Rides::isAllowSmoking).count();
        long petsCount = filteredRides.stream().filter(Rides::isAllowPets).count();

        long morningCount = filteredRides.stream()
                .filter(r -> {
                    LocalTime time = r.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
                    return !time.isBefore(LocalTime.of(6, 0)) && !time.isAfter(LocalTime.of(12, 0));
                }).count();

        long afternoonCount = filteredRides.stream()
                .filter(r -> {
                    LocalTime time = r.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
                    return time.isAfter(LocalTime.of(12, 0)) && !time.isAfter(LocalTime.of(18, 0));
                }).count();

        long eveningCount = filteredRides.stream()
                .filter(r -> {
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

        model.addAttribute("searchForm", form);
        model.addAttribute("matchedRides", filteredRides);


//        model.addAttribute("searchForm", form);
//        model.addAttribute("matchedRides", matchedRides);

        return "fragments/searchRide";
    }


}
