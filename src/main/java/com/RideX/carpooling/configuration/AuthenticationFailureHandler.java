package com.RideX.carpooling.configuration;

import com.RideX.carpooling.helpers.Message;
import com.RideX.carpooling.helpers.MessageType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthenticationFailureHandler implements org.springframework.security.web.authentication.AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (exception instanceof DisabledException) {

            HttpSession session = request.getSession();

            Message message = new Message();
            message.setContent("User is disabled, Email with  verification link is sent on your email id !!");
            message.setType(MessageType.red);

            session.setAttribute("message", message);

            response.sendRedirect("/login");

        } else if (exception instanceof AccessDeniedHandler) {
            response.sendRedirect("/403");
        } else {
            response.sendRedirect("/login?error=true");
        }
    }
}
