package com.RideX.carpooling.services;

public interface EmailServices {
    void sendEmail(String to, String subject, String body);
//    void sendEmailWithHTML(String[] to, String subject, String body);
}
