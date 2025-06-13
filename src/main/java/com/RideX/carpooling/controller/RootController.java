package com.RideX.carpooling.controller;

import com.RideX.carpooling.helpers.Helper;
import com.RideX.carpooling.model.User;
import com.RideX.carpooling.services.UserServices;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class RootController {
    private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserServices userService;
    @ModelAttribute
    public void addLoggedInUserInformation(Model model, Authentication authentication) {
        if (authentication == null) {
            logger.debug("No authentication object found, user not logged in.");
            return;
        }
        String username = Helper.getEmailOfLoggedInUser(authentication);
        logger.info("Fetching information for user: {}", username);
        User user = userService.getUserByEmail(username);
        if (user == null) {
            logger.error("User not found with email: {}", username);
            return;
        }
        logger.info("User logged in: {}", user);
        if (user.isEnabled()) {
            model.addAttribute("loggedInUser", user);
        } else {
            logger.warn("User account is disabled: {}", username);
        }
    }
}
