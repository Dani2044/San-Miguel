package com.example.demo.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Component
public class JWTGenerator {

    @Value("${app.jwt.expiration:86400000}")
    private long expirationTime;

    @Value("${app.jwt.secret:YourSuperSecretKeyThatShouldBeAtLeast32CharactersLong!123}")
    private String secret;

    private static final MacAlgorithm ALGORITHM = io.jsonwebtoken.Jwts.SIG.HS512;

    private SecretKey getKey() {
        try {
            // For HS512, we need at least 64 bytes (512 bits)
            // Use SHA-512 to derive a fixed-length key from the secret
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            byte[] keyBytes = digest.digest(secret.getBytes(StandardCharsets.UTF_8));
            // SHA-512 produces exactly 64 bytes, which is perfect for HS512
            return Keys.hmacShaKeyFor(keyBytes);
        } catch (NoSuchAlgorithmException e) {
            // Fallback: use the secret bytes directly (less secure but should work)
            byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
            if (keyBytes.length < 64) {
                // Pad by repeating if too short
                byte[] paddedKey = new byte[64];
                for (int i = 0; i < 64; i++) {
                    paddedKey[i] = keyBytes[i % keyBytes.length];
                }
                keyBytes = paddedKey;
            } else if (keyBytes.length > 64) {
                // Truncate if too long
                byte[] truncatedKey = new byte[64];
                System.arraycopy(keyBytes, 0, truncatedKey, 0, 64);
                keyBytes = truncatedKey;
            }
            return Keys.hmacShaKeyFor(keyBytes);
        }
    }

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + expirationTime);

        return Jwts.builder()
                .subject(username)
                .issuedAt(currentDate)
                .expiration(expirationDate)
                .signWith(getKey(), ALGORITHM)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public Boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}