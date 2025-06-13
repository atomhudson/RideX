package com.RideX.carpooling.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    @Autowired
    private SecurityCustomUserDetailService userDetailService;

    @Autowired
    private OAuthAuthenticationSuccessHandler handler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(authorize -> {
            // authorization rules
            authorize.requestMatchers("admin/**").hasRole("ADMIN");
            authorize.requestMatchers("/user/**").authenticated();
            authorize.anyRequest().permitAll();
        });

        httpSecurity.formLogin(formLogin -> {
            formLogin.loginPage("/login");
            formLogin.loginProcessingUrl("/authenticate");
            formLogin.successHandler(authenticationSuccessHandler);
            formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");
            formLogin.failureHandler(authenticationFailureHandler);
        });

        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        httpSecurity.oauth2Login(oauth -> {
            oauth.loginPage("/login");
            oauth.successHandler(handler);
        });

        httpSecurity.exceptionHandling(oauth -> {
            oauth.accessDeniedPage("/user/403");
        });

        httpSecurity.logout(logoutForm -> {
            logoutForm.logoutUrl("/do-logout");
            logoutForm.logoutSuccessUrl("/login?logout=true");
        });

        httpSecurity.rememberMe(rememberMe -> {
            rememberMe.key("uniqueAndSecret")
                    .tokenValiditySeconds(86400) // 24 hours
                    .rememberMeParameter("remember-me");
        });

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
