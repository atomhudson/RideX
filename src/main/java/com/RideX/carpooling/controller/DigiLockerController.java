package com.RideX.carpooling.controller;

import com.RideX.carpooling.dto.DigiLockerVerificationDto;
import com.RideX.carpooling.helpers.GetCurrentLoggedInUser;
import com.RideX.carpooling.helpers.Message;
import com.RideX.carpooling.helpers.MessageType;
import com.RideX.carpooling.model.User;
import com.RideX.carpooling.services.DigiLockerService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user/digilocker")
public class DigiLockerController {

    private static final Logger logger = LoggerFactory.getLogger(DigiLockerController.class);

    @Autowired
    private DigiLockerService digiLockerService;

    @Autowired
    private GetCurrentLoggedInUser getCurrentLoggedInUser;

    /**
     * Entry point: User clicks "Verify with DigiLocker"
     */
    @GetMapping("/initiate")
    public String initiateDigiLocker(HttpSession session) {
        User currentUser = getCurrentLoggedInUser.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }

        if (currentUser.isGovtIdVerified()) {
            session.setAttribute("message", new Message("Your Government ID is already verified via DigiLocker!", MessageType.green));
            return "redirect:/user/profile?tab=account";
        }

        String targetUrl = digiLockerService.getInitiateAuthUrl("user_" + currentUser.getUserId());
        return "redirect:" + targetUrl;
    }

    /**
     * Government DigiLocker Consent & Document Selection Portal
     */
    @GetMapping("/portal")
    public String showDigiLockerPortal(@RequestParam(value = "state", required = false) String state,
                                       Model model,
                                       HttpSession session) {
        User currentUser = getCurrentLoggedInUser.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }

        if (currentUser.isGovtIdVerified()) {
            session.setAttribute("message", new Message("Your Government ID is already verified!", MessageType.green));
            return "redirect:/user/profile?tab=account";
        }

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("bonusCoins", digiLockerService.getBonusTrustCoins());
        model.addAttribute("isSandbox", digiLockerService.isSandboxEnabled());
        return "user/digilockerPortal";
    }

    /**
     * Process verification after user selects document & provides OTP
     */
    @PostMapping("/verify")
    public String processVerification(@RequestParam(value = "docType", defaultValue = "Aadhaar") String docType,
                                      @RequestParam(value = "otp", required = false) String otp,
                                      @RequestParam(value = "customDocNum", required = false) String customDocNum,
                                      HttpSession session) {
        User currentUser = getCurrentLoggedInUser.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }

        // Validate OTP format (6 digits)
        if (otp != null && !otp.trim().isEmpty() && !otp.trim().matches("\\d{6}")) {
            session.setAttribute("message", new Message("Invalid OTP. Please enter a valid 6-digit verification code.", MessageType.red));
            return "redirect:/user/digilocker/portal";
        }

        // Generate verified document data
        DigiLockerVerificationDto docDto = digiLockerService.getSimulatedDocument(docType, currentUser);
        if (customDocNum != null && !customDocNum.trim().isEmpty()) {
            docDto.setDocNumber(customDocNum.trim());
        }

        boolean success = digiLockerService.verifyAndApproveUser(currentUser, docDto);
        if (success) {
            session.setAttribute("message", new Message(
                    "🎉 Identity Verified! Your " + docDto.getDocType() + " (" + docDto.getDocNumber() + ") is now certified by DigiLocker. +" + digiLockerService.getBonusTrustCoins() + " Trust Coins awarded!",
                    MessageType.green
            ));
            return "redirect:/user/profile?tab=account";
        } else {
            session.setAttribute("message", new Message("DigiLocker verification could not be completed. Please try again or use manual upload.", MessageType.red));
            return "redirect:/user/verifyGovtId";
        }
    }

    /**
     * Production OAuth 2.0 callback for live MeriPehchaan DigiLocker gateway
     */
    @GetMapping("/callback")
    public String handleOAuthCallback(@RequestParam(value = "code", required = false) String code,
                                      @RequestParam(value = "state", required = false) String state,
                                      @RequestParam(value = "error", required = false) String error,
                                      HttpSession session) {
        if (error != null) {
            logger.warn("DigiLocker OAuth error: {}", error);
            session.setAttribute("message", new Message("DigiLocker authorization cancelled or rejected.", MessageType.red));
            return "redirect:/user/verifyGovtId";
        }

        User currentUser = getCurrentLoggedInUser.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }

        // In live mode with code exchange, fallback to simulated document if in sandbox mode
        DigiLockerVerificationDto docDto = digiLockerService.getSimulatedDocument("Aadhaar", currentUser);
        boolean success = digiLockerService.verifyAndApproveUser(currentUser, docDto);

        if (success) {
            session.setAttribute("message", new Message("DigiLocker KYC successfully verified! +50 Trust Coins added.", MessageType.green));
        } else {
            session.setAttribute("message", new Message("Failed to process DigiLocker KYC.", MessageType.red));
        }

        return "redirect:/user/profile?tab=account";
    }
}
