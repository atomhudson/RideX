package com.RideX.carpooling.services;

import com.RideX.carpooling.model.RideRequest;
import java.util.Map;

public interface PaymentService {

    /**
     * Creates an order with Razorpay (or sandbox fallback) for the ride request.
     */
    Map<String, Object> createOrder(RideRequest rideRequest, double amount);

    /**
     * Verifies the Razorpay payment signature HMAC-SHA256.
     */
    boolean verifySignature(String orderId, String paymentId, String signature);

    /**
     * Gets public configuration for Razorpay frontend checkout.
     */
    Map<String, String> getPaymentConfig();
}
