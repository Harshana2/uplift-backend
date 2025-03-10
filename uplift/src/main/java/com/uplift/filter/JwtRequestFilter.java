package com.uplift.filter;

import com.uplift.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        // Check if the Authorization header is present and starts with "Bearer "
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);  // Remove "Bearer " prefix
            try {
                // Extract username from the token
                username = jwtUtil.extractUsername(jwt);
            } catch (ExpiredJwtException e) {
                // If token is expired, send an Unauthorized error response
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token Expired");
                return;
            }
        }

        // Check if the username is not null and if the authentication context is not already set
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtUtil.validateToken(jwt, username)) {
                // Extract roles from the JWT token
                String role = jwtUtil.extractRole(jwt);  // Assuming `extractRole()` method exists in JwtUtil

                // Assign authorities based on the role extracted from the token
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);

                // Create an authenticated user with the role(s)
                User authenticatedUser = new User(username, "", Arrays.asList(authority));

                // Set the authentication context
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                        authenticatedUser, null, authenticatedUser.getAuthorities()
                ));
            }
        }

        // Continue with the request filter chain
        chain.doFilter(request, response);
    }
}
