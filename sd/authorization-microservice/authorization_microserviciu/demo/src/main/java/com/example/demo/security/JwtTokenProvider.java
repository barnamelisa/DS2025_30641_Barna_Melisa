package com.example.demo.security;

import com.example.demo.entities.AuthorizationUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    private static final long EXPIRATION_MS = 86400000; // 1 zi

    // Crează o cheie sigură pentru HS512
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public String generateToken(AuthorizationUser user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                // 🚀 CORECȚIE ESENȚIALĂ: INCLUDEM ID-UL (UUID) ÎN TOKEN
                .claim("userId", user.getId().toString())

                .claim("roles", user.getRoles().stream().collect(Collectors.toList()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(key)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
