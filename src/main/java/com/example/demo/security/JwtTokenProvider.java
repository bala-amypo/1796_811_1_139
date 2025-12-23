package com.example.demo.security;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

@Component
public class JwtTokenProvider {

    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private final long validityInMs = 60 * 60 * 1000; 

    
    public String createToken(Long userId, String email, Set<String> roles) {

        Claims claims = Jwts.claims();
        claims.put("userId", userId);
        claims.put("email", email);
        claims.put("roles", roles); // Stored as collection (comes back as ArrayList)

        Date now = new Date();
        Date expiry = new Date(now.getTime() + validityInMs);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(secretKey)
                .compact();
    }

    
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    
    public String getEmail(String token) {
        return getClaims(token).get("email", String.class);
    }

    
    public Long getUserId(String token) {
        return getClaims(token).get("userId", Long.class);
    }

    
    @SuppressWarnings("unchecked")
    public Set<String> getRoles(String token) {

        Object rolesObj = getClaims(token).get("roles");
        Set<String> roles = new HashSet<>();

        if (rolesObj instanceof Iterable<?>) {
            for (Object role : (Iterable<?>) rolesObj) {
                roles.add(role.toString());
            }
        }

        return roles;
    }

    
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
