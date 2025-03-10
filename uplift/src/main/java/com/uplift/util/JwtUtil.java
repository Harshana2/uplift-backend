package com.uplift.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "01234567890123456789012345678901"; // exactly 32 bytes
    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour

    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    // Generate token including username, role, and id
    public String generateToken(String username, String role, String id) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        claims.put("id", id);  // Add the user ID to the token

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Validate the token (checks if it's properly signed and not expired)
    public boolean validateToken(String token, String username) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return username.equals(extractUsername(token)) && !isTokenExpired(token); // Ensure the username matches
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // Extract the username from the token
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Extract the role from the token
    public String extractRole(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }

    // Extract the user ID from the token
    public String extractId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("id", String.class);  // Get the ID from token claims
    }

    // Check if the token is expired
    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }
}
