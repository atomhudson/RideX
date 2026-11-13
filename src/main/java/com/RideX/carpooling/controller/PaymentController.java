package com.RideX.carpooling.controller;

import com.RideX.carpooling.helpers.GetCurrentLoggedInUser;
import com.RideX.carpooling.model.RideRequest;
import com.RideX.carpooling.model.Rides;
import com.RideX.carpooling.model.User;
import com.RideX.carpooling.repositories.RideRepository;
import com.RideX.carpooling.repositories.RidesRequestsRepository;
import com.RideX.carpooling.repositories.UserRepository;
import com.RideX.carpooling.services.CustomUUIDService;
import com.RideX.carpooling.services.EmailServices;
import com.RideX.carpooling.services.PaymentService;
import com.RideX.carpooling.services.ProfileCacheService;
import com.RideX.carpooling.services.RideCacheService;
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
 * Controller for Razorpay payment operations.
 * Handles:
 *  - Order creation (/api/payment/create-order)
 *  - Signature verification (/api/payment/verify)
 *  - Gateway config retrieval (/api/payment/config)
 */
@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private RidesRequestsRepository ridesRequestsRepository;

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GetCurrentLoggedInUser getCurrentLoggedInUser;

    @Autowired
    private CustomUUIDService customUUIDService;

    @Autowired
    private RideCacheService rideCacheService;

    @Autowired
    private ProfileCacheService profileCacheService;

    @Autowired
    private EmailServices emailServices;

    /**
     * Create Razorpay Order before checkout popup opens.
     */
    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(@RequestBody Map<String, Object> payload) {
        User currentUser = getCurrentLoggedInUser.getCurrentUser();
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Please log in to proceed with payment."));
        }

        String existingRequestId = (String) payload.get("requestId");

        // Case 1: Paying for an existing request from 'My Trips'
        if (existingRequestId != null && !existingRequestId.trim().isEmpty()) {
            RideRequest request = ridesRequestsRepository.findById(existingRequestId).orElse(null);
            if (request == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Booking request not found."));
            }
            if (!request.getUser().getUserId().equals(currentUser.getUserId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "Unauthorized access."));
            }
            if ("PAID".equalsIgnoreCase(request.getPaymentStatus())) {
                return ResponseEntity.badRequest().body(Map.of("error", "This booking is already paid."));
            }

            Map<String, Object> orderRes = paymentService.createOrder(request, request.getFinalPrice());
            return ResponseEntity.ok(orderRes);
        }

        // Case 2: New booking directly from rideDetails page
        String rideId = (String) payload.get("rideId");
        int seat = parseInteger(payload.get("seat"), 1);
        int coins = parseInteger(payload.get("coin"), 0);
        double finalPrice = parseDouble(payload.get("finalPrice"), 0.0);
        boolean coDriver = Boolean.parseBoolean(String.valueOf(payload.get("coDriver")));

        if (rideId == null || rideId.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Ride ID is required."));
        }

        Rides ride = rideRepository.findById(rideId).orElse(null);
        if (ride == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Ride not found."));
        }

        if (ride.getDriver().getUserId().equals(currentUser.getUserId())) {
            return ResponseEntity.badRequest().body(Map.of("error", "Drivers cannot book their own ride."));
        }

        if (ride.getAvailableSeats() < seat) {
            return ResponseEntity.badRequest().body(Map.of("error", "Not enough available seats left."));
        }

        // Create initial pending request
        RideRequest newRequest = new RideRequest();
        newRequest.setRequestId("RRQ" + customUUIDService.getUUID());
        newRequest.setUser(currentUser);
        newRequest.setRide(ride);
        newRequest.setRequestedSeats(seat);
        newRequest.setCoDriver(coDriver);
        newRequest.setUsedCoins(coins);
        newRequest.setFinalPrice(finalPrice);
        newRequest.setRequestStatus("PENDING");
        newRequest.setPaymentStatus("UNPAID");
        newRequest.setRequestedAt(new Date());

        ridesRequestsRepository.save(newRequest);

        // Evict caches
        rideCacheService.evictRideDetail(ride.getRideId());
        profileCacheService.evictForUser(currentUser.getUserId(), currentUser.getEmail());
        profileCacheService.evictForUser(ride.getDriver().getUserId(), ride.getDriver().getEmail());

        // Zero-cost booking covered by coins
        if (finalPrice <= 0) {
            newRequest.setPaymentStatus("PAID");
            newRequest.setRequestStatus("APPROVED");

            // Generate Boarding OTP immediately
            int otpInt = 1000 + new java.security.SecureRandom().nextInt(9000);
            String otp = String.valueOf(otpInt);
            newRequest.setStartOtp(otp);
            newRequest.setOtpVerified(false);

            // Transfer coins from passenger to driver
            if (coins > 0) {
                currentUser.setCoins(Math.max(0, currentUser.getCoins() - coins));
                userRepository.save(currentUser);
                User driver = ride.getDriver();
                driver.setCoins(driver.getCoins() + coins);
                userRepository.save(driver);
            }

            // Reserve seat
            if (!ride.getPassengers().contains(currentUser)) {
                ride.getPassengers().add(currentUser);
                ride.setAvailableSeats(Math.max(0, ride.getAvailableSeats() - seat));
                rideRepository.save(ride);
            }

            ridesRequestsRepository.save(newRequest);

            // Send confirmation email with boarding OTP
            try {
                emailServices.sendEmail(currentUser.getEmail(),
                        "✅ RideX Booking Confirmed — Boarding OTP",
                        "Your seat has been confirmed for the ride: " + ride.getSourceCity() + " → " + ride.getDestinationCity() + "!\n\n"
                                + "🔐 Your Boarding OTP: " + otp + "\n\n"
                                + "Share this code with your driver at pickup to start your trip.");
            } catch (Exception e) {
                logger.warn("Confirmation email error: {}", e.getMessage());
            }

            Map<String, Object> zeroRes = new HashMap<>();
            zeroRes.put("success", true);
            zeroRes.put("zeroAmount", true);
            zeroRes.put("requestId", newRequest.getRequestId());
            zeroRes.put("message", "Booking confirmed! 100% paid using Reward Coins.");
            return ResponseEntity.ok(zeroRes);
        }

        // Create Razorpay Order
        Map<String, Object> orderRes = paymentService.createOrder(newRequest, finalPrice);
        if (orderRes.containsKey("orderId")) {
            newRequest.setRazorpayOrderId((String) orderRes.get("orderId"));
            ridesRequestsRepository.save(newRequest);
        }

        return ResponseEntity.ok(orderRes);
    }

    /**
     * Verify payment signature after client completes checkout in Razorpay modal.
     */
    @PostMapping("/verify")
    public ResponseEntity<?> verifyPayment(@RequestBody Map<String, String> payload) {
        String requestId = payload.get("requestId");
        String razorpayOrderId = payload.get("razorpay_order_id");
        String razorpayPaymentId = payload.get("razorpay_payment_id");
        String razorpaySignature = payload.get("razorpay_signature");

        if (requestId == null || razorpayOrderId == null || razorpayPaymentId == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Missing payment verification parameters."));
        }

        RideRequest request = ridesRequestsRepository.findById(requestId).orElse(null);
        if (request == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Ride request not found."));
        }

        boolean isValid = paymentService.verifySignature(razorpayOrderId, razorpayPaymentId, razorpaySignature);
        if (!isValid) {
            logger.warn("Razorpay signature mismatch for order: {}, payment: {}", razorpayOrderId, razorpayPaymentId);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Payment signature verification failed."));
        }

        // Update booking to PAID and APPROVED
        request.setPaymentStatus("PAID");
        request.setRequestStatus("APPROVED");
        request.setRazorpayOrderId(razorpayOrderId);
        request.setRazorpayPaymentId(razorpayPaymentId);
        request.setRazorpaySignature(razorpaySignature);

        // Generate Boarding OTP if not already generated
        if (request.getStartOtp() == null || request.getStartOtp().isEmpty()) {
            int otpInt = 1000 + new java.security.SecureRandom().nextInt(9000);
            request.setStartOtp(String.valueOf(otpInt));
            request.setOtpVerified(false);
        }

        // Deduct coins if used and credit driver
        Rides ride = request.getRide();
        if (request.getUsedCoins() > 0) {
            User passenger = request.getUser();
            passenger.setCoins(Math.max(0, passenger.getCoins() - request.getUsedCoins()));
            userRepository.save(passenger);

            User driver = ride.getDriver();
            driver.setCoins(driver.getCoins() + request.getUsedCoins());
            userRepository.save(driver);
        }

        // Reserve seat
        if (!ride.getPassengers().contains(request.getUser())) {
            ride.getPassengers().add(request.getUser());
            ride.setAvailableSeats(Math.max(0, ride.getAvailableSeats() - request.getRequestedSeats()));
            rideRepository.save(ride);
        }

        ridesRequestsRepository.save(request);

        // Evict caches
        rideCacheService.evictRideDetail(request.getRide().getRideId());
        profileCacheService.evictForUser(request.getUser().getUserId(), request.getUser().getEmail());
        profileCacheService.evictForUser(request.getRide().getDriver().getUserId(), request.getRide().getDriver().getEmail());

        // Send confirmation emails
        try {
            emailServices.sendEmail(request.getUser().getEmail(),
                    "RideX Booking & Payment Confirmed",
                    "Your payment of ₹" + request.getFinalPrice() + " (Payment ID: " + razorpayPaymentId + ") for ride "
                            + request.getRide().getSourceCity() + " → " + request.getRide().getDestinationCity() + " is confirmed!\n\n"
                            + "🔐 Your Boarding OTP: " + request.getStartOtp() + "\n\n"
                            + "Please share this code with the driver at pickup to begin your ride.");

            emailServices.sendEmail(request.getRide().getDriver().getEmail(),
                    "New Paid Ride Request Confirmed",
                    request.getUser().getfName() + " has booked and prepaid for " + request.getRequestedSeats() + " seat(s) on your ride ("
                            + request.getRide().getSourceCity() + " → " + request.getRide().getDestinationCity() + ").\n"
                            + "The booking is confirmed. Ask the passenger for their 4-digit boarding OTP at pickup!");
        } catch (Exception e) {
            logger.warn("Notification email dispatch failed: {}", e.getMessage());
        }

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Payment verified and booking confirmed!");
        response.put("paymentId", razorpayPaymentId);
        response.put("orderId", razorpayOrderId);
        response.put("otp", request.getStartOtp());
        response.put("requestId", requestId);
        return ResponseEntity.ok(response);
    }

    /**
     * Get public gateway parameters.
     */
    @GetMapping("/config")
    public ResponseEntity<?> getPaymentConfig() {
        return ResponseEntity.ok(paymentService.getPaymentConfig());
    }

    private int parseInteger(Object val, int fallback) {
        if (val == null) return fallback;
        try {
            if (val instanceof Number num) return num.intValue();
            return Integer.parseInt(val.toString());
        } catch (Exception e) {
            return fallback;
        }
    }

    private double parseDouble(Object val, double fallback) {
        if (val == null) return fallback;
        try {
            if (val instanceof Number num) return num.doubleValue();
            return Double.parseDouble(val.toString());
        } catch (Exception e) {
            return fallback;
        }
    }
}
