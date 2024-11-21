package com.project.capstone.config;

import com.project.capstone.service.impl.DefaultUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.authentication.AuthenticationManager;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Autowired
    private DefaultUserServiceImpl customUserDetailsService;

    @Autowired
    private AuthenticationSuccessHandler successHandler;

    // Define password encoder bean
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Authentication provider to set custom UserDetailsService and password encoder
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(customUserDetailsService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    // AuthenticationManager bean to configure authentication provider
    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(authenticationProvider());
        return authenticationManagerBuilder.build();
    }

    // Security filter chain to configure HTTP security
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(configurer -> configurer.disable()) // Disable CSRF if necessary
                .authorizeRequests(authorize -> authorize
                        // Allow access to registration, Swagger, and API docs without authentication
                        .requestMatchers("/registration", "/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        // Role-based access control
                        .requestMatchers("/viewFeedbacks", "/buses","/home/admin").hasRole("ADMIN") // Only ADMIN can view feedbacks and add buses
                        .requestMatchers("/searchBuses", "/feedback", "/allBookings","/home/user").hasRole("USER") // Only USER can access these pages

                        // Any other request needs to be authenticated
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        // Custom login page URL
                        .loginPage("/login")
                        // Use the success handler for redirect after login
                        .successHandler(successHandler)
                        // Allow access to the login page for all users
                        .permitAll()
                )
                .logout(logout -> logout
                        // Invalidate the session and clear authentication on logout
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        // Define the logout request URL (matches the `/logout` path)
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        // Redirect to the login page after logout
                        .logoutSuccessUrl("/login?logout")
                        // Allow access to logout for all users
                        .permitAll()
                );

        return http.build();
    }

}