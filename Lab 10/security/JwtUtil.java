package com.ws101.maningcay.ecommerceapi.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expiration;

    public String generateToken(
            UserDetails userDetails
    ) {

        return Jwts.builder()
                .subject(
                        userDetails.getUsername()
                )
                .issuedAt(
                        new Date()
                )
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + expiration
                        )
                )
                .signWith(
                        getSigningKey(),
                        SignatureAlgorithm.HS256
                )
                .compact();
    }

    public String extractUsername(
            String token
    ) {

        return Jwts.parser()
                .verifyWith(
                        getSigningKey()
                )
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean isTokenValid(
            String token,
            UserDetails userDetails
    ) {

        String username =
                extractUsername(token);

        return username.equals(
                userDetails.getUsername()
        );
    }

    private SecretKey getSigningKey() {

        byte[] keyBytes =
                Decoders.BASE64.decode(
                        secretKey
                );

        return Keys.hmacShaKeyFor(
                keyBytes
        );
    }
}