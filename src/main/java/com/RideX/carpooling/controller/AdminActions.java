package com.RideX.carpooling.controller;

import com.RideX.carpooling.model.GovtIDVerifyRequest;
import com.RideX.carpooling.model.User;
import com.RideX.carpooling.repositories.UserRepository;
import com.RideX.carpooling.services.GovtIdRequestService;
import com.RideX.carpooling.services.UserServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping("/admin/action")
public class AdminActions {
    private static final Logger logger = LoggerFactory.getLogger(AdminActions.class);

    @Autowired
    private GovtIdRequestService govtIdRequestService;
    @Autowired
    private UserServices userServices;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/verifyGovtId/approve/{requestId}")
    public String approveGovtId(@PathVariable String requestId) {
        // TODO: Add my Approval logic here, e.g. update database, send notification and redirecting to the adminDashboard.
        System.out.println("Approving govt ID verification for requestId: " + requestId);
        GovtIDVerifyRequest request = govtIdRequestService.getPendingRequest(requestId);
        if (request != null) {
            User user = userServices.getUserById(request.getUserId());
            if (user != null) {
                user.setGovtIdVerified(true);
                user.setGovtIdNumber(request.getGovtIdUniqueNumber());
                user.setGovtIdUpload(request.getGovtIdImageUrl());
                user.setGovtIdType(request.getGovtIdType());
                user.setGovtIdVerifyRequest(null);
                user.setDateUpdate(new Date());
                request.setVerified(true);
                request.setUpdated(true);
                govtIdRequestService.save(request);
                userRepository.save(user);
            }
        }
        return "redirect:/admin/dashboard";
    }
    @PostMapping("/verifyGovtId/deny/{requestId}")
    public String rejectGovtId(@PathVariable String requestId){
        System.out.println("Rejecting govt ID verification for requestId: " + requestId);
        GovtIDVerifyRequest request = govtIdRequestService.getPendingRequest(requestId);
        if (request != null) {
            User user = userServices.getUserById(request.getUserId());
            user.setGovtIdVerifyRequest(null);
            logger.info("{}",user);
            user.setDateUpdate(new Date());
            userRepository.save(user);

            request.setVerified(false);
            request.setUpdated(true);
            govtIdRequestService.save(request);
        }
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/user/accountAction/{userId}")
    public String userAccountAction(@PathVariable("userId") String userId, RedirectAttributes redirectAttributes) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEnabled(!user.isEnabled()); // toggle the status
            userRepository.save(user);
            redirectAttributes.addFlashAttribute("message", "User " + (user.isEnabled() ? "enabled" : "disabled") + " successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "User not found.");
        }
        return "redirect:/admin/getUsers";
    }

}
