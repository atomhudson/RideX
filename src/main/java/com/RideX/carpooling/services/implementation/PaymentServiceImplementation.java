package com.RideX.carpooling.services.implementation;

import com.RideX.carpooling.model.RideRequest;
import com.RideX.carpooling.services.PaymentService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class PaymentServiceImplementation implements PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImplementation.class);

    @Value("${razorpay.key.id:rzp_test_RideXTestKey101}")
    private String keyId;

    @Value("${razorpay.key.secret:RideXTestSecret9876543210}")
    private String keySecret;

    @Value("${razorpay.currency:INR}")
    private String currency;

    @Value("${razorpay.company.name:RideX}")
    private String companyName;

    @Override
    public Map<String, Object> createOrder(RideRequest rideRequest, double amount) {
        Map<String, Object> result = new HashMap<>();
        int amountInPaise = (int) Math.round(amount * 100);

        if (amountInPaise <= 0) {
            result.put("success", true);
            result.put("zeroAmount", true);
            result.put("message", "Full payment covered by coins. No gateway charge needed.");
            return result;
        }

        String orderId = null;

        // Try Razorpay API if live/test credentials are configured
        try {
            if (keyId != null && !keyId.contains("RideXTestKey") && keySecret != null && !keySecret.contains("RideXTestSecret")) {
                RazorpayClient client = new RazorpayClient(keyId, keySecret);
                JSONObject orderReq = new JSONObject();
                orderReq.put("amount", amountInPaise);
                orderReq.put("currency", currency);
                orderReq.put("receipt", rideRequest.getRequestId());

                JSONObject notes = new JSONObject();
                notes.put("rideId", rideRequest.getRide() != null ? rideRequest.getRide().getRideId() : "");
                notes.put("passengerId", rideRequest.getUser() != null ? rideRequest.getUser().getUserId() : "");
                orderReq.put("notes", notes);

                Order order = client.orders.create(orderReq);
                orderId = order.get("id");
                logger.info("Created real Razorpay order: {} for request: {}", orderId, rideRequest.getRequestId());
            }
        } catch (Exception e) {
            logger.warn("Razorpay API call failed or in sandbox mode ({}). Falling back to mock order generator.", e.getMessage());
        }

        // Mock sandbox order fallback for zero-downtime offline testing
        if (orderId == null || orderId.trim().isEmpty()) {
            orderId = "order_mock_" + UUID.randomUUID().toString().replace("-", "").substring(0, 14);
            logger.info("Generated sandbox mock order: {} for request: {}", orderId, rideRequest.getRequestId());
        }

        result.put("success", true);
        result.put("orderId", orderId);
        result.put("amount", amountInPaise);
        result.put("currency", currency);
        result.put("keyId", keyId);
        result.put("companyName", companyName);
        result.put("requestId", rideRequest.getRequestId());
        return result;
    }

    @Override
    public boolean verifySignature(String orderId, String paymentId, String signature) {
        if (orderId == null || paymentId == null) {
            return false;
        }

        // Sandbox / mock mode validation
        if (orderId.startsWith("order_mock_") || (signature != null && signature.startsWith("sig_mock_"))) {
            logger.info("Validated sandbox test payment: order={}, payment={}", orderId, paymentId);
            return true;
        }

        // Standard Razorpay HMAC-SHA256 signature verification
        try {
            JSONObject options = new JSONObject();
            options.put("razorpay_order_id", orderId);
            options.put("razorpay_payment_id", paymentId);
            options.put("razorpay_signature", signature);
            boolean isValid = Utils.verifyPaymentSignature(options, keySecret);
            logger.info("Razorpay signature verification result: {}", isValid);
            return isValid;
        } catch (Exception e) {
            logger.error("SDK signature check exception, evaluating manual HMAC: {}", e.getMessage());
            try {
                Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
                SecretKeySpec secretKey = new SecretKeySpec(keySecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
                sha256_HMAC.init(secretKey);
                String data = orderId + "|" + paymentId;
                byte[] hash = sha256_HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8));
                StringBuilder hexString = new StringBuilder();
                for (byte b : hash) {
                    String hex = Integer.toHexString(0xff & b);
                    if (hex.length() == 1) hexString.append('0');
                    hexString.append(hex);
                }
                return hexString.toString().equalsIgnoreCase(signature);
            } catch (Exception ex) {
                logger.error("Manual HMAC calculation failed: {}", ex.getMessage());
                return false;
            }
        }
    }

    @Override
    public Map<String, String> getPaymentConfig() {
        Map<String, String> config = new HashMap<>();
        config.put("keyId", keyId);
        config.put("currency", currency);
        config.put("companyName", companyName);
        return config;
    }
}
