package com.RideX.carpooling.controller;

import com.RideX.carpooling.helpers.Message;
import com.RideX.carpooling.helpers.MessageType;
import com.RideX.carpooling.model.User;
import com.RideX.carpooling.repositories.UserRepository;
import com.RideX.carpooling.services.EmailServices;
import com.RideX.carpooling.services.OTPService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class ForgotPasswordController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailServices emailServices;

    @Autowired
    private OTPService otpService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/forgot-password")
    public String showForgotPasswordPage() {
        return "fragments/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam String email, HttpSession session) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            Message message = new Message();
            message.setContent("No user found with this email address.");
            message.setType(MessageType.red);
            session.setAttribute("message", message);
            return "redirect:/forgot-password";
        }

        String otp = otpService.generateOTP(email);
        String emailBody = String.format("Your OTP for password reset is: %s. This OTP is valid for 5 minutes.", otp);
        emailServices.sendEmail(email, "Password Reset OTP", emailBody);

        Message message = new Message();
        message.setContent("OTP has been sent to your email address.");
        message.setType(MessageType.green);
        session.setAttribute("message", message);
        session.setAttribute("resetEmail", email);

        return "redirect:/reset-password";
    }

    @GetMapping("/reset-password")
    public String showResetPasswordPage(Model model, HttpSession session) {
        String email = (String) session.getAttribute("resetEmail");
        if (email == null) {
            return "redirect:/forgot-password";
        }
        model.addAttribute("email", email);
        return "fragments/reset-password";
    }

    @PostMapping("/reset-password")
    public String processResetPassword(@RequestParam String email,
                                     @RequestParam String otp,
                                     @RequestParam String newPassword,
                                     @RequestParam String confirmPassword,
                                     HttpSession session) {
        if (!newPassword.equals(confirmPassword)) {
            Message message = new Message();
            message.setContent("Passwords do not match.");
            message.setType(MessageType.red);
            session.setAttribute("message", message);
            return "redirect:/reset-password";
        }

        if (!otpService.verifyOTP(email, otp)) {
            Message message = new Message();
            message.setContent("Invalid OTP.");
            message.setType(MessageType.red);
            session.setAttribute("message", message);
            return "redirect:/reset-password";
        }

        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            User userToUpdate = user.get();
            userToUpdate.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(userToUpdate);
            otpService.clearOTP(email);
            session.removeAttribute("resetEmail");

            Message message = new Message();
            message.setContent("Password has been reset successfully.");
            message.setType(MessageType.green);
            session.setAttribute("message", message);
            return "redirect:/login";
        }

        return "redirect:/forgot-password";
    }
} 