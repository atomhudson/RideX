package com.RideX.carpooling.controller;

import com.RideX.carpooling.helpers.Message;
import com.RideX.carpooling.helpers.MessageType;
import com.RideX.carpooling.model.User;
import com.RideX.carpooling.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/verify-email")
    public String verifyEmail(@RequestParam("token") String token, HttpSession session) {
        User user = userRepository.findByEmailToken(token).orElse(null);

        if (user != null) {
            if (user.getEmailToken().equals(token)) {
                user.setEmailVerified(true);
                user.setEnabled(true);
                userRepository.save(user);
                Message message = new Message();
                message.setContent("You email is verified. Now you can login  ");
                message.setType(MessageType.green);
                session.setAttribute("message", message);
                return "fragments/success";
            }
            Message message = new Message();
            message.setContent("Email not verified ! Token is not associated with user .");
            message.setType(MessageType.red);
            session.setAttribute("message", message);
            return "fragments/error";

        }
        Message message = new Message();
        message.setContent("Email not verified ! Token is not associated with user .");
        message.setType(MessageType.red);
        session.setAttribute("message", message);

        return "fragments/error";
    }

}
