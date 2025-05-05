package com.websocket.webteck.jwt;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.websocket.webteck.model.Role;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
@Component
public class JwtUtil {
    private static final String secretKey = "ZY9KssUP3Wx19ROeLGEoITsKaybEx20lK8JKWvYhgwA=";
    public String generateToken(String email, Role role){
        return Jwts.builder()
            .setSubject(email)
            .claim("role", role.name())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }
    public String extractEmail(String token){
        return Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }
    public Role extractRole(String token){
        String role = Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .getBody()
            .get("role", String.class);
        return Role.valueOf(role);
    }
    public boolean validateToken(String email, String token){
        String extractedEmail = extractEmail(token);
        return (extractedEmail.equals(email) && !isTokenExpired(token));
    }
    public boolean isTokenExpired(String token){
        Date expiration = Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .getBody()
            .getExpiration();
        return expiration.before(new Date());
    }
} 
