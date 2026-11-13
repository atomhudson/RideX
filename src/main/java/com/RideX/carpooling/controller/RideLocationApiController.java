package com.RideX.carpooling.controller;

import com.RideX.carpooling.helpers.GetCurrentLoggedInUser;
import com.RideX.carpooling.helpers.RideLocationStore;
import com.RideX.carpooling.model.Rides;
import com.RideX.carpooling.model.User;
import com.RideX.carpooling.repositories.RideRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * REST API Controller for live location streaming and tracking.
 * - Driver streams GPS coordinates via POST /api/ride/location/update
 * - Passengers/family fetch live location via GET /api/ride/location/{rideId} or public endpoint
 */
@RestController
@RequestMapping("/api/ride/location")
public class RideLocationApiController {

    private static final Logger logger = LoggerFactory.getLogger(RideLocationApiController.class);

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private RideLocationStore locationStore;

    @Autowired
    private GetCurrentLoggedInUser getCurrentLoggedInUser;

    /**
     * Driver posts their current GPS coordinates.
     * Allowed only for the driver of the ride when ride is PLANNED or ONGOING.
     */
    @PostMapping("/update")
    public ResponseEntity<?> updateLocation(@RequestBody Map<String, Object> payload) {
        String rideId = (String) payload.get("rideId");
        if (rideId == null || rideId.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "rideId is required"));
        }

        double lat = parseDouble(payload.get("lat"));
        double lon = parseDouble(payload.get("lon"));
        double heading = parseDouble(payload.get("heading"));
        double speed = parseDouble(payload.get("speed"));

        if (lat == 0.0 && lon == 0.0) {
            return ResponseEntity.badRequest().body(Map.of("error", "Valid latitude and longitude are required"));
        }

        User currentUser = getCurrentLoggedInUser.getCurrentUser();
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "User not authenticated"));
        }

        Rides ride = rideRepository.findByRideId(rideId);
        if (ride == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Ride not found"));
        }

        if (ride.getDriver() == null || !ride.getDriver().getUserId().equals(currentUser.getUserId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "Only the ride driver can update location"));
        }

        // Cache in memory for ultra-fast passenger polling
        locationStore.updateLocation(rideId, lat, lon, heading, speed);

        // Persist to database so refreshes keep the latest position
        ride.setCurrentLat(String.valueOf(lat));
        ride.setCurrentLon(String.valueOf(lon));
        ride.setCurrentHeading(heading);
        ride.setLastLocationUpdate(new Date());
        rideRepository.save(ride);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("rideId", rideId);
        response.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.ok(response);
    }

    /**
     * Authenticated endpoint for passengers or driver to query live location.
     */
    @GetMapping("/{rideId}")
    public ResponseEntity<?> getRideLocation(
            @PathVariable("rideId") String rideId,
            @RequestParam(value = "token", required = false) String token) {
        Rides ride = rideRepository.findWithDetailsByRideId(rideId);
        if (ride == null) {
            ride = rideRepository.findByRideId(rideId);
        }
        if (ride == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Ride not found"));
        }

        User currentUser = getCurrentLoggedInUser.getCurrentUser();
        boolean hasValidToken = token != null && !token.trim().isEmpty() && token.trim().equals(ride.getTrackingToken());
        if (currentUser == null && !hasValidToken) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Please log in or provide a valid tracking token"));
        }

        return ResponseEntity.ok(buildLocationResponse(ride));
    }

    /**
     * Public endpoint for family members tracking via tokenized link.
     * No login required, secured by the unguessable trackingToken query parameter.
     */
    @GetMapping("/public/{rideId}")
    public ResponseEntity<?> getPublicRideLocation(
            @PathVariable("rideId") String rideId,
            @RequestParam(value = "token", required = false) String token) {

        Rides ride = rideRepository.findWithDetailsByRideId(rideId);
        if (ride == null) {
            ride = rideRepository.findByRideId(rideId);
        }
        if (ride == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Ride not found"));
        }

        if (token == null || !token.trim().equals(ride.getTrackingToken())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "Invalid or missing tracking token"));
        }

        return ResponseEntity.ok(buildLocationResponse(ride));
    }

    private Map<String, Object> buildLocationResponse(Rides ride) {
        Map<String, Object> data = new HashMap<>();
        data.put("success", true);
        data.put("rideId", ride.getRideId());
        data.put("rideStatus", ride.getRideStatus());
        data.put("sourceCity", ride.getSourceCity());
        data.put("sourceAddress", ride.getSourceAddress());
        data.put("sourceLat", ride.getSourceLat());
        data.put("sourceLon", ride.getSourceLon());
        data.put("destinationCity", ride.getDestinationCity());
        data.put("destinationAddress", ride.getDestinationAddress());
        data.put("destinationLat", ride.getDestinationLat());
        data.put("destinationLon", ride.getDestinationLon());
        data.put("trackingToken", ride.getTrackingToken());

        // Check in-memory store first
        RideLocationStore.LocationSnapshot snap = locationStore.getLocation(ride.getRideId());
        if (snap != null) {
            data.put("currentLat", snap.getLat());
            data.put("currentLon", snap.getLon());
            data.put("currentHeading", snap.getHeading());
            data.put("speed", snap.getSpeed());
            data.put("lastLocationUpdate", snap.getTimestamp());
        } else {
            // Fall back to entity properties
            double curLat = parseDouble(ride.getCurrentLat());
            double curLon = parseDouble(ride.getCurrentLon());
            data.put("currentLat", curLat != 0.0 ? curLat : parseDouble(ride.getSourceLat()));
            data.put("currentLon", curLon != 0.0 ? curLon : parseDouble(ride.getSourceLon()));
            data.put("currentHeading", ride.getCurrentHeading() != null ? ride.getCurrentHeading() : 0.0);
            data.put("speed", 0.0);
            data.put("lastLocationUpdate", ride.getLastLocationUpdate() != null ? ride.getLastLocationUpdate().getTime() : 0L);
        }

        if (ride.getDriver() != null) {
            data.put("driverName", ride.getDriver().getfName() + " " + ride.getDriver().getlName());
            data.put("driverAvatar", ride.getDriver().getProfilePic() != null ? ride.getDriver().getProfilePic() : "");
            data.put("driverPhone", ride.getDriver().getPhone() != null ? ride.getDriver().getPhone() : "");
        }

        if (ride.getCar() != null) {
            data.put("carMake", ride.getCar().getCarMake());
            data.put("carModel", ride.getCar().getCarModel());
            data.put("carColor", ride.getCar().getColor());
            data.put("carNumber", ride.getCar().getCarNumber());
        }

        return data;
    }

    private double parseDouble(Object val) {
        if (val == null) return 0.0;
        try {
            if (val instanceof Number num) return num.doubleValue();
            return Double.parseDouble(val.toString());
        } catch (Exception e) {
            return 0.0;
        }
    }
}
