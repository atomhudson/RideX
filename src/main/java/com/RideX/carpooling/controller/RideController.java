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
        }
        model.addAttribute("rideForm", rideForm);
        return "user/createRide";
    }

    @PostMapping("/process")
    public String processRide(@ModelAttribute("rideForm") RideForm rideForm, HttpSession session){
        logger.info(rideForm.toString());
        User user = getCurrentLoggedInUser.getCurrentUser();
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
        rides.setSourceCity(rideForm.getFromCity());
        rides.setSourceLat(rideForm.getFromLat());
        rides.setSourceLon(rideForm.getFromLng());

        rides.setDestinationAddress(rideForm.getToAddress());
        rides.setDestinationCity(rideForm.getToCity());
        rides.setDestinationLat(rideForm.getToLat());
        rides.setDestinationLon(rideForm.getToLng());

        String departure_date = rideForm.getDeparture_time();
        LocalDateTime startDateTime = LocalDateTime.parse(departure_date, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        Date startTime = Date.from(startDateTime.atZone(ZoneId.systemDefault()).toInstant());
        rides.setStartTime(startTime);

        List<String> result = routeService.getDistanceAndDuration(
                rideForm.getFromLat(), rideForm.getFromLng(),
                rideForm.getToLat(), rideForm.getToLng());

        if (!result.isEmpty()) {
            String distance = result.get(0);
            String durationStr = result.get(1);

            int durationInMinutes = Integer.parseInt(durationStr);

            LocalDateTime endDateTime = startDateTime.plusMinutes(durationInMinutes);
            Date endTime = Date.from(endDateTime.atZone(ZoneId.systemDefault()).toInstant());

            rides.setDuration(Double.parseDouble(durationStr));
            rides.setDistance(Double.parseDouble(distance));
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
        emailServices.sendEmail(rides.getDriver().getEmail(),"Ride successFully Created","You and Created a Ride from"+rides.getSourceCity()+" to"+ rides.getDestinationCity());


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
        emailServices.sendEmail(rides.getDriver().getEmail(),"Ride Canceled","You cancelled the ride");
        rideRepository.save(rides);
        session.setAttribute("message", new Message("Your ride from " + rides.getSourceCity() + " to " + rides.getDestinationCity() + " has been successfully cancelled.", MessageType.red));
        return "redirect:/user/profile";
    }

    @GetMapping("/detail/{rideID}")
    public String detailsRide(@PathVariable("rideID") String rideId, Model model){
        Rides rides = rideRepository.findByRideId(rideId);
        User user = getCurrentLoggedInUser.getCurrentUser();
        List<RideRequest> rideRequests = rides.getRideRequests();
        model.addAttribute("currentUser", user);
        model.addAttribute("ride", rides);
        model.addAttribute("rideRequests", rideRequests);
        model.addAttribute("hasRated", true);
        return "user/rideDetails";
    }

    @GetMapping("/rideJoined/detail/{rideID}")
    public String rideJoinedDetails(@PathVariable("rideID") String rideId, Model model){
        Rides rides = rideRepository.findByRideId(rideId);
        User user = getCurrentLoggedInUser.getCurrentUser();
        boolean hasRated = ratingRepository.existsByRaterAndRated(user, rides.getDriver());
        model.addAttribute("currentUser", user);
        model.addAttribute("ride", rides);
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
        System.out.println("RequesterId :"+requesterId);
        System.out.println("RideId :"+rideId);
        System.out.println("Seat :"+seat);
        System.out.println("CoDriver :"+coDriver);
        System.out.println("Used Coins: "+coins);
        System.out.println("FinalPrice :"+finalPrice);

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
        emailServices.sendEmail(ride.getDriver().getEmail(),"Get a Ride Request","You get a Ride Request on RideID: "+rideId);
        emailServices.sendEmail(requester.getEmail(),"Successfully Created Ride Request","You have created a ride Request for ride: "+rideId);
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

            // Deduct used coins from passenger
            int usedCoins = rideRequest.getUsedCoins();
            if (passenger.getCoins() < usedCoins) {
                session.setAttribute("message", new Message("Passenger does not have enough coins.", MessageType.red));
                return "redirect:/user/ride/detail/" + ride.getRideId();
            }

            passenger.setCoins(passenger.getCoins() - usedCoins);
            driver.setCoins(driver.getCoins() + usedCoins);

            rideRequest.setRequestStatus("APPROVED");
            ride.setAvailableSeats(ride.getAvailableSeats() - rideRequest.getRequestedSeats());

            ride.getPassengers().add(passenger);
            userRepository.save(passenger);
            userRepository.save(driver);
            rideRepository.save(ride);
            ridesRequestsRepository.save(rideRequest);
            emailServices.sendEmail(rideRequest.getUser().getEmail(),"Ride Request - Updated","Your Ride Request Accepted for rideID: "+ride.getRideId());
            session.setAttribute("message",new Message("Your ride request has been approved.", MessageType.green));
        } else {
            session.setAttribute("message",new Message("This request has already been processed.", MessageType.red));
        }
        return "redirect:/user/ride/detail/"+ride.getRideId();
    }

    @PostMapping("/request/reject/{requestId}")
    public String rejectRideRequest(@PathVariable("requestId") String requestId, HttpSession session) {
        RideRequest rideRequest = ridesRequestsRepository.findById(requestId).orElseThrow(() -> new RuntimeException("Request not found"));
        Rides ride = rideRequest.getRide();
        if (rideRequest.getRequestStatus().equals("PENDING")) {
            rideRequest.setRequestStatus("REJECTED");
            ridesRequestsRepository.save(rideRequest);
            emailServices.sendEmail(rideRequest.getUser().getEmail(),"Ride Request - Updated","Your Ride Request Rejected for rideID: "+ride.getRideId());
            session.setAttribute("message",new Message("Your ride request has been rejected.", MessageType.red));
        } else {
            session.setAttribute("message",new Message("This request has already been processed.", MessageType.red));
        }
        return "redirect:/user/ride/detail/"+ride.getRideId();
    }





}
