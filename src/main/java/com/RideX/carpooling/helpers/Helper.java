package com.RideX.carpooling.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Helper {

    private static Logger logger = LoggerFactory.getLogger(Helper.class);

    @Value("${server.baseUrl}")
    private String baseUrl;

    public static String getEmailOfLoggedInUser(Authentication authentication) {
        if (authentication instanceof OAuth2AuthenticationToken) {

            var aOAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
            var clientId = aOAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

            var oauth2User = (OAuth2User) authentication.getPrincipal();
            String username = "";

            if (clientId.equalsIgnoreCase("google")) {
                // sign with google
                System.out.println("Getting email from google");
                username = oauth2User.getAttribute("email").toString();

            } else if (clientId.equalsIgnoreCase("github")) {
                // sign with github
                System.out.println("Getting email from github");
                username = oauth2User.getAttribute("email") != null ? oauth2User.getAttribute("email").toString()
                        : oauth2User.getAttribute("login").toString() + "@github.com";
            }
            // sign with facebook
            return username;

        } else {
            System.out.println("Getting data from local database");
            return authentication.getName();
        }
    }

    public String getLinkForEmailVerification(String emailToken) {
        return this.baseUrl + "/auth/verify-email?token=" + emailToken;
    }

    public boolean isGmailAddress(String str) {
        String gmailRegex = "^[A-Za-z0-9._%+-]+@gmail\\.com$";
        Pattern pattern = Pattern.compile(gmailRegex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
