package com.fastcampus.blog.services;

import com.fastcampus.blog.properties.SecretProperties;
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

    public String generateToken(UserDetails userDetails) {
        Map<String, String> claims = new HashMap<>();
        // menambahkan website issuer (yg membuat token session)
        claims.put("iss", secretProperties.getJwtIss());
        Instant now = Instant.now();
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(10*60)))
                .signWith(generateKey())
                .compact();
    }

    private SecretKey generateKey() {
        byte[] decodedKey = Base64.getDecoder().decode(secretProperties.getJwtSecretKey());
        return Keys.hmacShaKeyFor(decodedKey);
    }
}
