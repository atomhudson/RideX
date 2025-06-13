package com.RideX.carpooling.services;

public interface OTPService {
    String generateOTP(String email);
    boolean verifyOTP(String email, String otp);
    void clearOTP(String email);
} 