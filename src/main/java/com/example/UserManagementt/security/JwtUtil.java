package com.example.UserManagementt.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private final String jwtIssuer = "UserManagementApp";
    private final long jwtExpirationMs = 3600000; //1 jam
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); //otomatic key

    public String generateToken(String email){
        return Jwts.builder()
                .setSubject(email)
                .setIssuer(jwtIssuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() +jwtExpirationMs))
                .signWith(key)
                .compact();
    }

    public  String getEmailFromToken(String token){
        return parseToken(token).getBody().getSubject();
    }

    public boolean validateToken(String token){
        try {
            parseToken(token);
            return true;
        }catch (JwtException | IllegalArgumentException e){
            return false;
        }
    }

    private Jws<Claims> parseToken(String token){
        return  Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }
}
