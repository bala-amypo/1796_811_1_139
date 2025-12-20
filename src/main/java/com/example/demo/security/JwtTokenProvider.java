package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.Set;

public class JwtTokenProvider {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private final long validityInMilliseconds = 3600000; // 1 hour

    public String createToken(Long userId, String email, Set<String> roles) {

        Claims claims = Jwts.claims();
        claims.put("userId", userId);
        claims.put("email", email);
        claims.put("roles", roles);

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Long getUserId(String token) {
        return getClaims(token).get("userId", Long.class);
    }

    public String getEmail(String token) {
        return getClaims(token).get("email", String.class);
    }

    @SuppressWarnings("unchecked")
    public Set<String> getRoles(String token) {
        return (Set<String>) getClaims(token).get("roles");
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
