package com.RideX.carpooling.configuration;

import com.RideX.carpooling.model.User;
import com.RideX.carpooling.repositories.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

@Component
public class AuthenticationSuccessHandler implements org.springframework.security.web.authentication.AuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        for (GrantedAuthority authority : authentication.getAuthorities()) {
//            if ("ROLE_ADMIN".equals(authority.getAuthority())) {
//                response.sendRedirect("/admin/dashboard");
//                return;
//            }
//        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            String email = ((UserDetails) principal).getUsername();
            response.setHeader("X-User-Email", email);
            Collection<? extends GrantedAuthority> authorities = ((UserDetails) principal).getAuthorities();
            for (GrantedAuthority authority : authorities) {
                if ("ROLE_ADMIN".equals(authority.getAuthority())) {
                    response.sendRedirect("/admin/dashboard");
                    return;
                }
            }
        }
        response.sendRedirect("/user/profile");
    }
}
