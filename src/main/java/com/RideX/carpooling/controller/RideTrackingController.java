package com.RideX.carpooling.controller;

import com.RideX.carpooling.helpers.GetCurrentLoggedInUser;
import com.RideX.carpooling.model.Rides;
import com.RideX.carpooling.model.User;
import com.RideX.carpooling.repositories.RideRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Controller to render the Live Ride Tracking & Family Safety screen.
 * Accessible to:
 *   1. Logged-in Driver & Passengers of the ride (token optional)
 *   2. Family / Friends / Public via tokenized link: /track-trip/{rideId}?token=...
 */
@Controller
public class RideTrackingController {

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private GetCurrentLoggedInUser getCurrentLoggedInUser;

    @Value("${server.baseUrl:http://localhost:8080}")
    private String baseUrl;

    @GetMapping("/track-trip/{rideId}")
    public String showLiveTracking(
            @PathVariable("rideId") String rideId,
            @RequestParam(value = "token", required = false) String token,
            Model model,
            HttpServletRequest request) {

        Rides ride = rideRepository.findWithDetailsByRideId(rideId);
        if (ride == null) {
            ride = rideRepository.findByRideId(rideId);
        }

        if (ride == null) {
            model.addAttribute("errorTitle", "Trip Not Found");
            model.addAttribute("errorMessage", "The requested ride does not exist or has been removed.");
            return "user/liveTrackingError";
        }

        User currentUser = getCurrentLoggedInUser.getCurrentUser();
        boolean isDriver = currentUser != null && ride.getDriver() != null
                && ride.getDriver().getUserId().equals(currentUser.getUserId());

        boolean isPassenger = false;
        if (currentUser != null && ride.getRideRequests() != null) {
            isPassenger = ride.getRideRequests().stream()
                    .anyMatch(rr -> rr.getUser() != null
                            && rr.getUser().getUserId().equals(currentUser.getUserId())
                            && ("APPROVED".equalsIgnoreCase(rr.getRequestStatus()) || "ACCEPTED".equalsIgnoreCase(rr.getRequestStatus())));
        }

        boolean isTokenValid = token != null && !token.trim().isEmpty() && token.trim().equals(ride.getTrackingToken());

        // Security check: must be driver, passenger, or have valid family token
        if (!isDriver && !isPassenger && !isTokenValid) {
            model.addAttribute("errorTitle", "Access Restricted");
            model.addAttribute("errorMessage", "This live tracking link is private or the security token is invalid. Please request a new link from your driver or passenger.");
            return "user/liveTrackingError";
        }

        String trackingToken = ride.getTrackingToken();
        // Construct canonical tracking URL
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String currentHost = scheme + "://" + serverName + ((serverPort == 80 || serverPort == 443) ? "" : ":" + serverPort);
        String liveTrackingUrl = currentHost + "/track-trip/" + ride.getRideId() + "?token=" + trackingToken;

        String whatsappMsg = "🚗 Track my live ride with RideX from " + ride.getSourceCity() + " to " + ride.getDestinationCity() + " here:\n" + liveTrackingUrl;
        String whatsappShareUrl = "https://api.whatsapp.com/send?text=" + URLEncoder.encode(whatsappMsg, StandardCharsets.UTF_8);

        model.addAttribute("ride", ride);
        model.addAttribute("isDriver", isDriver);
        model.addAttribute("isPassenger", isPassenger);
        model.addAttribute("isPublicViewer", !isDriver && !isPassenger && isTokenValid);
        model.addAttribute("trackingToken", trackingToken);
        model.addAttribute("liveTrackingUrl", liveTrackingUrl);
        model.addAttribute("whatsappShareUrl", whatsappShareUrl);
        model.addAttribute("currentUser", currentUser);

        return "user/liveTracking";
    }
}
