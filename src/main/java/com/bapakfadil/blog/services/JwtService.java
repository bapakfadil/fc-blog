package com.bapakfadil.blog.services;

import com.bapakfadil.blog.properties.SecretProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    @Autowired
    SecretProperties secretProperties;

    // Untuk generate Token
    public String generateToken(UserDetails userDetails) {
        Map<String, String> claims = new HashMap<>();
        claims.put("iss", secretProperties.getJwtIss());
        Instant now = Instant.now();
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(10))) // Set expired di 10 detik
                .signWith(generateKey())
                .compact();
    }

    // Untuk decode secretKey
    private SecretKey generateKey() {
        // decode key harus rutin diganti
        byte[] decodeKey = Base64.getDecoder().decode(secretProperties.getJwtSecret());
        return Keys.hmacShaKeyFor(decodeKey);
    }
}
