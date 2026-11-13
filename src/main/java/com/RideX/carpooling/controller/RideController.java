package com.RideX.carpooling.controller;

import com.RideX.carpooling.forms.RideForm;
import com.RideX.carpooling.helpers.*;
import com.RideX.carpooling.model.CarDetails;
import com.RideX.carpooling.model.RideRequest;
import com.RideX.carpooling.model.Rides;
import com.RideX.carpooling.model.User;
import com.RideX.carpooling.repositories.RatingRepository;
import com.RideX.carpooling.repositories.RideRepository;
import com.RideX.carpooling.repositories.RidesRequestsRepository;
import com.RideX.carpooling.repositories.UserRepository;
import com.RideX.carpooling.services.CustomUUIDService;
import com.RideX.carpooling.services.EmailServices;
import com.RideX.carpooling.services.ProfileCacheService;
import com.RideX.carpooling.services.RideCacheService;
import com.RideX.carpooling.services.RouteService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.security.SecureRandom;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@Controller
@RequestMapping("/user/ride")
public class RideController {

    private final Logger logger = LoggerFactory.getLogger(RideController.class);

    @Autowired
    private GetCurrentLoggedInUser getCurrentLoggedInUser;

    @Autowired
    private RouteService routeService;

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RidesRequestsRepository ridesRequestsRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private EmailServices emailServices;

    @Autowired
    private CustomUUIDService customUUIDService;

    @Autowired
    private ProfileCacheService profileCacheService;

    @Autowired
    private RideCacheService rideCacheService;

    @GetMapping("/create")
    public String createRide(Model model){
        RideForm rideForm = new RideForm();
        User user = getCurrentLoggedInUser.getCurrentUser();
        CarDetails existingCarDetails = user.getCarDetails();
        List<String> car_details = new ArrayList<>();
        if (existingCarDetails != null) {
            car_details.add(existingCarDetails.getCarMake());
            car_details.add(existingCarDetails.getCarModel());
            car_details.add(existingCarDetails.getCarNumber());
            rideForm.setAvailable_car(car_details);
        } else {
            // Flag that no car is registered — template will show a prominent banner
            model.addAttribute("noCarRegistered", true);
        }
        model.addAttribute("rideForm", rideForm);
        return "user/createRide";
    }

    @PostMapping("/process")
    public String processRide(@ModelAttribute("rideForm") RideForm rideForm, HttpSession session){
        User user = getCurrentLoggedInUser.getCurrentUser();

        // Guard: prevent Hibernate PropertyValueException when car_id is NOT NULL in DB
        if (user.getCarDetails() == null) {
            session.setAttribute("message", new Message(
                "Please register your vehicle details before publishing a ride.", MessageType.red));
            return "redirect:/user/car/register";
        }

        Rides rides;

        if (rideForm.getRideId() != null && !rideForm.getRideId().isEmpty()) {
            rides = rideRepository.findByRideId(rideForm.getRideId());
            logger.info(rides.toString());
            if (rides == null) {
                logger.info("Ride with id {} not found", rideForm.getRideId());
                return "redirect:/user/ride/create";
            }
            rides.setUpdatedAt(new Date()); // Update existing ride's timestamp
        } else {
            rides = new Rides();
            System.out.println("No Pre Ride Found So Creating new Ride");
            rides.setRideId("RIDE"+customUUIDService.getUUID()); // Generate new ID for new ride
            rides.setCreatedAt(new Date());
        }
        // Common fields for both new and edit
        rides.setSourceAddress(rideForm.getFromAddress());
        String srcCity = (rideForm.getFromCity() != null && !rideForm.getFromCity().trim().isEmpty()) ? rideForm.getFromCity().trim() : extractCityFromAddress(rideForm.getFromAddress());
        rides.setSourceCity(srcCity);
        rides.setSourceLat(rideForm.getFromLat());
        rides.setSourceLon(rideForm.getFromLng());

        rides.setDestinationAddress(rideForm.getToAddress());
        String destCity = (rideForm.getToCity() != null && !rideForm.getToCity().trim().isEmpty()) ? rideForm.getToCity().trim() : extractCityFromAddress(rideForm.getToAddress());
        rides.setDestinationCity(destCity);
        rides.setDestinationLat(rideForm.getToLat());
        rides.setDestinationLon(rideForm.getToLng());

        String departure_date = rideForm.getDeparture_time();
        LocalDateTime startDateTime;
        try {
            if (departure_date != null && !departure_date.trim().isEmpty()) {
                startDateTime = LocalDateTime.parse(departure_date.trim(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            } else {
                startDateTime = LocalDateTime.now().plusHours(1);
            }
        } catch (Exception e) {
            logger.warn("Could not parse departure date '{}': {}", departure_date, e.getMessage());
            startDateTime = LocalDateTime.now().plusHours(1);
        }
        Date startTime = Date.from(startDateTime.atZone(ZoneId.systemDefault()).toInstant());
        rides.setStartTime(startTime);

        List<String> result = routeService.getDistanceAndDuration(
                rideForm.getFromLat(), rideForm.getFromLng(),
                rideForm.getToLat(), rideForm.getToLng());

        if (!result.isEmpty()) {
            String distance = result.get(0);
            String durationStr = result.get(1);

            int durationInMinutes = (int) Math.round(Double.parseDouble(durationStr));

            LocalDateTime endDateTime = startDateTime.plusMinutes(durationInMinutes);
            Date endTime = Date.from(endDateTime.atZone(ZoneId.systemDefault()).toInstant());

            rides.setDuration(Double.parseDouble(durationStr));
            rides.setDistance(Double.parseDouble(distance));
            rides.setEndTime(endTime);
        } else {
            int defaultDuration = 60;
            LocalDateTime endDateTime = startDateTime.plusMinutes(defaultDuration);
            Date endTime = Date.from(endDateTime.atZone(ZoneId.systemDefault()).toInstant());

            rides.setDuration(defaultDuration);
            rides.setDistance(50.0);
            rides.setEndTime(endTime);
        }

        rides.setAvailableSeats(rideForm.getSeat());
        rides.setTotalSeats(rideForm.getSeat());
        rides.setPrice(rideForm.getPrice());
        rides.setAcAvailable(rideForm.isAc_available());
        rides.setLuggageAllowed(rideForm.isLuggage_allowed());
        rides.setAllowPets(rideForm.isPetsAllowed());
        rides.setAllowSmoking(rideForm.isSmokingAllowed());
        rides.setRideStatus(AppConstants.RIDE_PLANED);
        rides.setUpdatedAt(new Date());
        rides.setDriver(user);
        rides.setCar(user.getCarDetails());

        logger.info("Ride before update or insert {}",rides);

        // Save the ride (this handles both insert and update correctly)

        rideRepository.save(rides);
        rideCacheService.evictRideDetail(rides.getRideId());
        profileCacheService.evictForUser(user.getUserId(), user.getEmail());
        try {
            emailServices.sendEmail(rides.getDriver().getEmail(),"Ride successFully Created","You and Created a Ride from"+rides.getSourceCity()+" to "+ rides.getDestinationCity());
        } catch (Exception e) {
            logger.warn("Failed to send ride confirmation email: {}", e.getMessage());
        }


        // Set a message for user session
        if (rideForm.getRideId() != null && !rideForm.getRideId().isEmpty()) {
            session.setAttribute("message", new Message("Ride updated successfully", MessageType.green));
        } else {
            session.setAttribute("message", new Message("Ride created successfully", MessageType.green));
        }

        return "redirect:/user/ride/create";
    }

    @GetMapping("/edit/{rideId}")
    public String editRide(@PathVariable("rideId") String rideId, Model model){
        Rides rides = rideRepository.findByRideId(rideId);
        User user = getCurrentLoggedInUser.getCurrentUser();

        if (rides != null && rides.getDriver().getEmail().equals(user.getEmail())) {
            RideForm form = new RideForm();

            form.setRideId(rides.getRideId());
            form.setFromAddress(rides.getSourceAddress());
            form.setFromCity(rides.getSourceCity());
            form.setFromLat(rides.getSourceLat());
            form.setFromLng(rides.getSourceLon());

            form.setToAddress(rides.getDestinationAddress());
            form.setToCity(rides.getDestinationCity());
            form.setToLat(rides.getDestinationLat());
            form.setToLng(rides.getDestinationLon());

            form.setDeparture_time(rides.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().toString());
            form.setSeat(rides.getAvailableSeats());
            form.setPrice((int) rides.getPrice());
            form.setAc_available(rides.isAcAvailable());
            form.setLuggage_allowed(rides.isLuggageAllowed());
            form.setPetsAllowed(rides.isAllowPets());
            form.setSmokingAllowed(rides.isAllowSmoking());
            CarDetails carDetails = rides.getCar();
            List<String> result = new ArrayList<>();
            result.add(carDetails.getCarMake());
            result.add(carDetails.getCarModel());
            result.add(carDetails.getCarNumber());
            form.setAvailable_car(result);

            model.addAttribute("rideForm", form);
            return "user/createRide";
        }

        return "redirect:/user/ride/create";
    }

    @GetMapping("/cancelRide/{rideId}")
    public String cancelRide(@PathVariable("rideId") String rideId,HttpSession session){
        Rides rides = rideRepository.findByRideId(rideId);

        if (rides == null) {
            session.setAttribute("message", new Message("Ride not found.", MessageType.red));
            return "redirect:/user/profile";
        }

        rides.setRideStatus(AppConstants.RIDE_CANCELLED);
        rides.setUpdatedAt(new Date());
        try {
            emailServices.sendEmail(rides.getDriver().getEmail(),"Ride Canceled","You cancelled the ride");
        } catch (Exception e) {
            logger.warn("Failed to send cancel email: {}", e.getMessage());
        }
        rideRepository.save(rides);
        rideCacheService.evictRideDetail(rides.getRideId());
        profileCacheService.evictForUser(rides.getDriver().getUserId(), rides.getDriver().getEmail());
        session.setAttribute("message", new Message("Your ride from " + rides.getSourceCity() + " to " + rides.getDestinationCity() + " has been successfully cancelled.", MessageType.red));
        return "redirect:/user/profile";
    }

    @GetMapping("/detail/{rideID}")
    public String detailsRide(@PathVariable("rideID") String rideId, Model model, HttpSession session){
        Rides rides = rideCacheService.getRideWithDetails(rideId);
        if (rides == null) {
            rides = rideRepository.findById(rideId).orElse(null);
        }
        if (rides == null) {
            session.setAttribute("message", new Message("Ride not found or has been removed.", MessageType.red));
            return "redirect:/user/profile";
        }
        User user = getCurrentLoggedInUser.getCurrentUser();
        List<RideRequest> rideRequests = rides.getRideRequests();
        if (rideRequests == null) {
            rideRequests = java.util.Collections.emptyList();
        }
        model.addAttribute("currentUser", user);
        model.addAttribute("ride", rides);
        model.addAttribute("rideRequests", rideRequests);
        model.addAttribute("hasRated", true);
        return "user/rideDetails";
    }

    @GetMapping("/rideJoined/detail/{rideID}")
    public String rideJoinedDetails(@PathVariable("rideID") String rideId, Model model, HttpSession session){
        Rides rides = rideCacheService.getRideWithDetails(rideId);
        if (rides == null) {
            rides = rideRepository.findById(rideId).orElse(null);
        }
        if (rides == null) {
            session.setAttribute("message", new Message("Ride not found or has been removed.", MessageType.red));
            return "redirect:/user/profile?tab=trips";
        }
        User user = getCurrentLoggedInUser.getCurrentUser();
        boolean hasRated = false;
        if (user != null && rides.getDriver() != null) {
            hasRated = ratingRepository.existsByRaterAndRated(user, rides.getDriver());
        }
        model.addAttribute("currentUser", user);
        model.addAttribute("ride", rides);
        model.addAttribute("rideRequests", java.util.Collections.emptyList());
        model.addAttribute("hasRated", hasRated);
        return "user/rideDetails";
    }

    @PostMapping("/requestRide/{requesterId}/{rideId}")
    public String requestRide(@PathVariable("requesterId") String requesterId, @PathVariable("rideId") String rideId,
                              @RequestParam(name = "seat") int seat,
                              @RequestParam(name = "coin", defaultValue = "0") int coins,
                              @RequestParam(name = "finalPrice") double finalPrice,
                              @RequestParam(name = "coDriver") boolean coDriver,
                              HttpSession session) {

        User requester = userRepository.findById(requesterId).orElseThrow(() -> new RuntimeException("User not found"));
        Rides ride = rideRepository.findById(rideId).orElseThrow(() -> new RuntimeException("Ride not found"));

        // Check if there are enough available seats
        if (ride.getAvailableSeats() < seat) {
            session.setAttribute("message", new Message("Not enough available seats", MessageType.red));
            return "fragments/rideRequestResult";
        }

        RideRequest rideRequest = new RideRequest();
        rideRequest.setRequestId("RRQ"+customUUIDService.getUUID());
        rideRequest.setUser(requester);
        rideRequest.setRide(ride);
        rideRequest.setRequestedSeats(seat);
        rideRequest.setCoDriver(coDriver);
        rideRequest.setUsedCoins(coins);
        rideRequest.setFinalPrice(finalPrice);
        rideRequest.setRequestStatus("PENDING");
        ridesRequestsRepository.save(rideRequest);
        ride.setAvailableSeats(ride.getAvailableSeats());
        rideRepository.save(ride);
        rideCacheService.evictRideDetail(ride.getRideId());
        profileCacheService.evictForUser(requester.getUserId(), requester.getEmail());
        profileCacheService.evictForUser(ride.getDriver().getUserId(), ride.getDriver().getEmail());
        try {
            emailServices.sendEmail(ride.getDriver().getEmail(),"Get a Ride Request","You get a Ride Request on RideID: "+rideId);
            emailServices.sendEmail(requester.getEmail(),"Successfully Created Ride Request","You have created a ride Request for ride: "+rideId);
        } catch (Exception e) {
            logger.warn("Failed to send ride request email: {}", e.getMessage());
        }
        session.setAttribute("message",new Message("Your ride request has been submitted.", MessageType.green));
        return "fragments/rideRequestResult";
    }

    @PostMapping("/request/accept/{requestId}")
    public String approveRideRequest(@PathVariable("requestId") String requestId, HttpSession session) {
        RideRequest rideRequest = ridesRequestsRepository.findById(requestId).orElseThrow(() -> new RuntimeException("Request not found"));
        Rides ride = rideRequest.getRide();

        if (rideRequest.getRequestStatus().equals("PENDING")) {
            User passenger = rideRequest.getUser();
            User driver = ride.getDriver();

            // Deduct used coins ONLY if not already processed via PaymentController
            int usedCoins = rideRequest.getUsedCoins();
            if (usedCoins > 0 && !"PAID".equalsIgnoreCase(rideRequest.getPaymentStatus())) {
                if (passenger.getCoins() < usedCoins) {
                    session.setAttribute("message", new Message("Passenger does not have enough coins.", MessageType.red));
                    return "redirect:/user/ride/detail/" + ride.getRideId();
                }
                passenger.setCoins(passenger.getCoins() - usedCoins);
                driver.setCoins(driver.getCoins() + usedCoins);
                userRepository.save(passenger);
                userRepository.save(driver);
            }

            rideRequest.setRequestStatus("APPROVED");

            // ── Generate boarding OTP and send to passenger ──
            String otp = rideRequest.getStartOtp();
            if (otp == null || otp.isEmpty()) {
                otp = generateOtp();
                rideRequest.setStartOtp(otp);
                rideRequest.setOtpVerified(false);
            }

            // Reserve seat if not already counted
            if (!ride.getPassengers().contains(passenger)) {
                ride.getPassengers().add(passenger);
                ride.setAvailableSeats(Math.max(0, ride.getAvailableSeats() - rideRequest.getRequestedSeats()));
                rideRepository.save(ride);
            }

            ridesRequestsRepository.save(rideRequest);
            rideCacheService.evictRideDetail(ride.getRideId());
            profileCacheService.evictForUser(passenger.getUserId(), passenger.getEmail());
            profileCacheService.evictForUser(driver.getUserId(), driver.getEmail());
            try {
                emailServices.sendEmail(
                    rideRequest.getUser().getEmail(),
                    "✅ Ride Confirmed — Your Boarding OTP",
                    "Your ride request for the trip from " + ride.getSourceCity()
                    + " to " + ride.getDestinationCity() + " has been APPROVED!\n\n"
                    + "🔐 Your Boarding OTP: " + otp + "\n\n"
                    + "Share this 4-digit code with your driver at the pickup point to start the ride.\n"
                    + "Do NOT share this code until you are at the pickup location.\n\n"
                    + "Ride ID: " + ride.getRideId() + "\n"
                    + "Departure: " + ride.getStartTime()
                );
            } catch (Exception e) {
                logger.warn("Failed to send boarding OTP email: {}", e.getMessage());
            }
            session.setAttribute("message", new Message("Ride request approved. Passenger has been sent their boarding OTP.", MessageType.green));

        } else {
            session.setAttribute("message",new Message("This request has already been processed.", MessageType.red));
        }
        return "redirect:/user/ride/detail/"+ride.getRideId();
    }

    @PostMapping("/request/reject/{requestId}")
    public String rejectRideRequest(@PathVariable("requestId") String requestId, HttpSession session) {
        RideRequest rideRequest = ridesRequestsRepository.findById(requestId).orElseThrow(() -> new RuntimeException("Request not found"));
        Rides ride = rideRequest.getRide();
        if ("PENDING".equals(rideRequest.getRequestStatus()) || "APPROVED".equals(rideRequest.getRequestStatus())) {
            rideRequest.setRequestStatus("REJECTED");

            // If passenger used coins, refund them
            if (rideRequest.getUsedCoins() > 0) {
                User passenger = rideRequest.getUser();
                passenger.setCoins(passenger.getCoins() + rideRequest.getUsedCoins());
                userRepository.save(passenger);

                User driver = ride.getDriver();
                driver.setCoins(Math.max(0, driver.getCoins() - rideRequest.getUsedCoins()));
                userRepository.save(driver);
            }

            // If passenger was already added to ride passengers list, restore seats
            if (ride.getPassengers().contains(rideRequest.getUser())) {
                ride.getPassengers().remove(rideRequest.getUser());
                ride.setAvailableSeats(Math.min(ride.getTotalSeats(), ride.getAvailableSeats() + rideRequest.getRequestedSeats()));
                rideRepository.save(ride);
            }

            ridesRequestsRepository.save(rideRequest);
            rideCacheService.evictRideDetail(ride.getRideId());
            profileCacheService.evictForUser(rideRequest.getUser().getUserId(), rideRequest.getUser().getEmail());
            profileCacheService.evictForUser(ride.getDriver().getUserId(), ride.getDriver().getEmail());

            try {
                emailServices.sendEmail(rideRequest.getUser().getEmail(),"Ride Request - Updated","Your Ride Request for ride " + ride.getSourceCity() + " → " + ride.getDestinationCity() + " has been rejected. Any reward coins used have been refunded to your wallet.");
            } catch (Exception e) {
                logger.warn("Failed to send reject email: {}", e.getMessage());
            }
            session.setAttribute("message",new Message("Ride request rejected. Used coins (if any) refunded.", MessageType.red));
        } else {
            session.setAttribute("message",new Message("This request has already been processed.", MessageType.red));
        }
        return "redirect:/user/ride/detail/"+ride.getRideId();
    }

    /**
     * JSON API: returns approved passengers for a ride so the driver OTP modal can render them.
     * Used by the AJAX call in ridePublishedCards.html.
     */
    @GetMapping("/approved-requests/{rideId}")
    @ResponseBody
    public ResponseEntity<List<Map<String, Object>>> getApprovedRequests(
            @PathVariable("rideId") String rideId) {

        Rides ride = rideRepository.findById(rideId).orElse(null);
        if (ride == null) return ResponseEntity.notFound().build();

        List<Map<String, Object>> result = new java.util.ArrayList<>();
        if (ride.getRideRequests() != null) {
            ride.getRideRequests().stream()
                .filter(rr -> "APPROVED".equals(rr.getRequestStatus()))
                .forEach(rr -> {
                    Map<String, Object> entry = new HashMap<>();
                    entry.put("requestId",     rr.getRequestId());
                    entry.put("passengerName", rr.getUser().getfName() + " " + rr.getUser().getlName());
                    entry.put("otpVerified",   rr.isOtpVerified());
                    result.add(entry);
                });
        }
        return ResponseEntity.ok(result);
    }

    // ────────────────────────────────────────────────────────────
    //  PHASE 2 — START-RIDE OTP ENDPOINTS
    // ────────────────────────────────────────────────────────────

    /** Generate a random 4-digit boarding OTP */
    private String generateOtp() {
        int otp = 1000 + new SecureRandom().nextInt(9000);
        return String.valueOf(otp);
    }

    /**
     * Driver verifies a single passenger's boarding OTP.
     * When ALL approved passengers are boarded, the ride transitions to ONGOING.
     * Called via AJAX from the driver’s ride detail page.
     */
    @PostMapping("/verify-start-otp")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> verifyStartOtp(
            @RequestParam("requestId") String requestId,
            @RequestParam("enteredOtp") String enteredOtp) {

        Map<String, Object> response = new HashMap<>();

        RideRequest rideRequest = ridesRequestsRepository.findById(requestId)
                .orElse(null);

        if (rideRequest == null) {
            response.put("success", false);
            response.put("message", "Ride request not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        if (!"APPROVED".equals(rideRequest.getRequestStatus())) {
            response.put("success", false);
            response.put("message", "This request is not approved.");
            return ResponseEntity.badRequest().body(response);
        }

        if (rideRequest.isOtpVerified()) {
            response.put("success", true);
            response.put("message", "Passenger already boarded.");
            return ResponseEntity.ok(response);
        }

        if (!enteredOtp.trim().equals(rideRequest.getStartOtp())) {
            response.put("success", false);
            response.put("message", "Incorrect OTP. Please ask the passenger to check their boarding pass.");
            return ResponseEntity.badRequest().body(response);
        }

        // OTP matched — mark passenger as boarded
        rideRequest.setOtpVerified(true);
        ridesRequestsRepository.save(rideRequest);

        Rides ride = rideRequest.getRide();

        // If ALL approved passengers are now boarded, start the ride
        boolean allBoarded = ride.getRideRequests().stream()
                .filter(rr -> "APPROVED".equals(rr.getRequestStatus()))
                .allMatch(RideRequest::isOtpVerified);

        if (allBoarded && "PLANNED".equals(ride.getRideStatus())) {
            ride.setRideStatus(AppConstants.RIDE_ONGOING);
            ride.setUpdatedAt(new Date());
            rideRepository.save(ride);
            rideCacheService.evictRideDetail(ride.getRideId());
            response.put("rideStarted", true);
            response.put("message", "All passengers boarded. Ride is now ONGOING! Have a safe trip 🚗");
            logger.info("Ride {} transitioned to ONGOING — all passengers boarded via OTP.", ride.getRideId());
        } else {
            response.put("rideStarted", false);
            response.put("message", "Passenger boarded! Waiting for other passengers.");
        }

        response.put("success", true);
        rideCacheService.evictRideDetail(ride.getRideId());
        return ResponseEntity.ok(response);
    }

    /**
     * Driver manually completes the ride.
     * Distributes reward coins to driver and all co-riders, then triggers rating emails.
     */
    @PostMapping("/complete-ride/{rideId}")
    public String completeRide(@PathVariable("rideId") String rideId, HttpSession session) {
        User driver = getCurrentLoggedInUser.getCurrentUser();
        Rides ride = rideRepository.findById(rideId).orElse(null);

        if (ride == null) {
            session.setAttribute("message", new Message("Ride not found.", MessageType.red));
            return "redirect:/user/profile";
        }

        if (!ride.getDriver().getUserId().equals(driver.getUserId())) {
            session.setAttribute("message", new Message("Unauthorized action.", MessageType.red));
            return "redirect:/user/profile";
        }

        if (!"ONGOING".equals(ride.getRideStatus())) {
            session.setAttribute("message", new Message("Only an ONGOING ride can be completed.", MessageType.red));
            return "redirect:/user/ride/detail/" + rideId;
        }

        ride.setRideStatus(AppConstants.RIDE_COMPLETED);
        ride.setUpdatedAt(new Date());

        // — Distribute reward coins —
        double distance = ride.getDistance();
        int rewardCoins = (int) Math.round(distance * 0.10);

        driver.setCoins(driver.getCoins() + rewardCoins + 5);
        userRepository.save(driver);

        if (ride.getRideRequests() != null) {
            ride.getRideRequests().stream()
                    .filter(rr -> "APPROVED".equals(rr.getRequestStatus()) && rr.isOtpVerified())
                    .forEach(rr -> {
                        User passenger = rr.getUser();
                        passenger.setCoins(passenger.getCoins() + rewardCoins);
                        userRepository.save(passenger);
                        // Send rating prompt email
                        try {
                            emailServices.sendEmail(
                                passenger.getEmail(),
                                "Rate your RideX trip!",
                                "Your ride from " + ride.getSourceCity() + " to " + ride.getDestinationCity()
                                + " is complete. Please rate your driver " + driver.getfName() + "."
                            );
                        } catch (Exception e) {
                            logger.warn("Rating email failed for passenger {}: {}", passenger.getEmail(), e.getMessage());
                        }
                    });
        }

        rideRepository.save(ride);
        rideCacheService.evictRideDetail(rideId);
        profileCacheService.evictForUser(driver.getUserId(), driver.getEmail());

        try {
            emailServices.sendEmail(driver.getEmail(), "Ride Completed!",
                    "Your ride from " + ride.getSourceCity() + " to " + ride.getDestinationCity()
                    + " has been marked complete. You earned " + (rewardCoins + 5) + " coins!");
        } catch (Exception e) {
            logger.warn("Completion email failed: {}", e.getMessage());
        }

        logger.info("Ride {} manually completed by driver {}.", rideId, driver.getEmail());
        session.setAttribute("message", new Message(
            "Ride completed! You earned " + (rewardCoins + 5) + " coins. Passengers have been notified to rate you.",
            MessageType.green));
        return "redirect:/user/ride/detail/" + rideId;
    }


    private String extractCityFromAddress(String address) {
        if (address == null || address.trim().isEmpty()) return "City";
        String clean = address.trim();
        if (clean.toLowerCase().endsWith(", india")) {
            clean = clean.substring(0, clean.length() - 7).trim();
        }
        String[] parts = clean.split(",");
        if (parts.length == 1) return parts[0].trim();
        String first = parts[0].trim();
        String second = parts[1].trim();
        String lowerFirst = first.toLowerCase();
        if (lowerFirst.contains("sector") || lowerFirst.contains("road") || lowerFirst.contains("street") || lowerFirst.contains("phase") || lowerFirst.contains("colony")) {
            return second;
        }
        return first;
    }
}