package com.RideX.carpooling.configuration;

import com.RideX.carpooling.helpers.AppConstants;
import com.RideX.carpooling.helpers.Message;
import com.RideX.carpooling.helpers.MessageType;
import com.RideX.carpooling.helpers.Providers;
import com.RideX.carpooling.model.User;
import com.RideX.carpooling.repositories.UserRepository;
import com.RideX.carpooling.services.CustomUUIDService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.stereotype.Component;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

    Logger logger = LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUUIDService customUUIDService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        logger.info("OAuthAuthenticationSuccessHandler");

        // Ensure that we have an OAuth2AuthenticationToken
        if (!(authentication instanceof OAuth2AuthenticationToken)) {
            logger.error("Authentication is not OAuth2AuthenticationToken");
            return;
        }

        // Identify the provider
        OAuth2AuthenticationToken oauth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        String authorizedClientRegistrationId = oauth2AuthenticationToken.getAuthorizedClientRegistrationId();
        logger.info("Provider: {}", authorizedClientRegistrationId);

        DefaultOAuth2User oauthUser = (DefaultOAuth2User) authentication.getPrincipal();
//        oauthUser.getAttributes().forEach((key, value) -> logger.info("{} : {}", key, value));

        // Create a new user object
        User user = new User();
        user.setUserId("USR"+customUUIDService.getUUID());
        user.setRoleList(List.of(AppConstants.ROLE_USER)); // Default role
        user.setEmailVerified(true);
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode("dummy"));

        // Populate user fields based on provider
        if ("google".equalsIgnoreCase(authorizedClientRegistrationId)) {
            user.setEmail(oauthUser.getAttribute("email"));
            user.setProfilePic(oauthUser.getAttribute("picture"));
            String fullNameStr = oauthUser.getAttribute("name");
            extractName(oauthUser, user, fullNameStr);
            user.setProvider(Providers.GOOGLE);
            user.setDateCreate(new Date());
            user.setDateUpdate(new Date());
        } else if ("github".equalsIgnoreCase(authorizedClientRegistrationId)) {
            String email = oauthUser.getAttribute("email") != null
                    ? oauthUser.getAttribute("email")
                    : oauthUser.getAttribute("login") + "@github.com";
            user.setEmail(email);
            user.setProfilePic(oauthUser.getAttribute("avatar_url"));
            String fullNameStr = oauthUser.getAttribute("login");
            extractName(oauthUser, user, fullNameStr);
            user.setProvider(Providers.GITHUB);
            user.setDateCreate(new Date());
            user.setDateUpdate(new Date());
        } else {
            logger.info("Unknown provider");
        }
        // Save or update user in database
        logger.info("----------- fetching user");
        User existingUser = userRepository.findByEmailNative(user.getEmail());
        logger.info("----------- existing user");
        if (existingUser == null) {
            userRepository.save(user);
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String email = ((UserDetails) principal).getUsername();
                response.setHeader("X-User-Email", email);
            }
            new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
            logger.info("User created: {}",user);
            logger.info("New user saved: {}", user.getEmail());
        } else {
            user = existingUser;
            if (!user.isEnabled()) {
                logger.warn("OAuthAuthenticationSuccessHandler2");
                Message message = new Message();
                message.setContent("User is disabled, Email with verification link is sent on your email id or Contact Us!!");
                message.setType(MessageType.red);
                request.getSession().setAttribute("message", message);
                logger.info("Email not verified");
                new DefaultRedirectStrategy().sendRedirect(request, response, "/login?error=true");
                return;

            } else {
                Object principal = authentication.getPrincipal();
                if (principal instanceof UserDetails) {
                    String email = ((UserDetails) principal).getUsername();
                    response.setHeader("X-User-Email", email);
                }
                new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
                return;
            }
        }
        logger.info("Existing user loaded: {}", user.getEmail());
    }

    private void extractName(DefaultOAuth2User oauthUser, User user, String fullNameStr) {
        String[] nameParts = fullNameStr.trim().split(" ");
        String firstName = nameParts[0];
        String lastName = nameParts.length > 1
                ? String.join(" ", Arrays.copyOfRange(nameParts, 1, nameParts.length))
                : "";

        user.setfName(firstName);
        user.setlName(lastName);
        user.setProviderUserId(oauthUser.getName());
    }
}
