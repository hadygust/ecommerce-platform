package com.hadygust.ecommerce.security;

import com.hadygust.ecommerce.config.JWTConfig;
import com.hadygust.ecommerce.entity.User;
import com.hadygust.ecommerce.entity.enums.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JWTConfig config;

    private SecretKey getSignKey(){
        return Keys.hmacShaKeyFor(config.getSecret().getBytes());
    }

    public String generateToken(User user){
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("role", user.getRole())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + config.getExpiration()))
                .signWith(getSignKey())
                .compact();
    }

    public Claims extractClaims(String token){
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractEmail(String token){
        return extractClaims(token).getSubject();
    }

    public UserRole extractRole(String token){
        UserRole role;
        return extractClaims(token).get("role", UserRole.class);
    }

    public Boolean isValid(String token, User user){
        return user.getEmail().equals(extractEmail(token));
    }

}
