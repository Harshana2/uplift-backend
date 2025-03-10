package com.uplift.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))  // Configure CORS
                .csrf(csrf -> csrf.disable())  // Disable CSRF for testing
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // Stateless session
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/login").permitAll()  // Allow public access to login route

                        // Allow public access to all other routes
                        .requestMatchers("/api/admin/**").permitAll()  // Admin access to /admin
                        .requestMatchers("/api/coaches/**").permitAll()  // Coach access to /coaches
                        .requestMatchers("/api/members/**").permitAll()  // Member access to /members

                        // Allow all requests
                        .anyRequest().permitAll()  // Ensure all requests are permitted
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));  // Frontend URL
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);  // Allow all endpoints
        return source;
    }
}
