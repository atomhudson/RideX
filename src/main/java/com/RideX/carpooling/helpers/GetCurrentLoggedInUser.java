package com.RideX.carpooling.helpers;

import com.RideX.carpooling.model.User;
import com.RideX.carpooling.services.UserServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class GetCurrentLoggedInUser {
    @Autowired
    private UserServices userServices;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public String getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            logger.warn("User not authenticated.");
            return "";
        }
        String username = Helper.getEmailOfLoggedInUser(authentication);
        logger.info("Currently logged-in user: {}", username);
        return username;
    }
    public User getCurrentUser() {
        String username = getLoggedInUser();
        return userServices.getUserByEmail(username);
    }
}
