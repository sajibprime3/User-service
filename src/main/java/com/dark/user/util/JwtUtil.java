package com.dark.user.util;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    long expiration = 1000 * 60 * 60;

    public String generateTokenWithRoles(String id, List<String> roles) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(id)
                .claim("roles", roles)
                .signWith(generateKey(secret))
                .issuedAt(Date.from(now))
                .issuer("user-service")
                .expiration(Date.from(now.plus(1, ChronoUnit.DAYS)))
                .compact();
    }

    public String extractId(String token) {
        return Jwts.parser().verifyWith(generateKey(secret)).build().parseSignedClaims(token).getPayload().getSubject();
    }

    public Boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(generateKey(secret)).build().parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    private SecretKey generateKey(String secret) {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

}
