package com.RideX.carpooling.services.implementation;

import com.RideX.carpooling.services.OTPService;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OTPServiceImplementation implements OTPService {
    private final Map<String, String> otpMap = new ConcurrentHashMap<>();
    private final SecureRandom secureRandom = new SecureRandom();

    @Override
    public String generateOTP(String email) {
        // Generate a 6-digit OTP
        String otp = String.format("%06d", secureRandom.nextInt(1000000));
        otpMap.put(email, otp);
        return otp;
    }

    @Override
    public boolean verifyOTP(String email, String otp) {
        String storedOTP = otpMap.get(email);
        return storedOTP != null && storedOTP.equals(otp);
    }

    @Override
    public void clearOTP(String email) {
        otpMap.remove(email);
    }
} 