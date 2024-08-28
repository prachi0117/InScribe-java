package com.programming.springblog.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import java.security.Key;

@Service
public class JwtProvider {

    private Key key;
    
    public JwtProvider() {
        try {
            this.key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
            if (this.key == null) {
                throw new IllegalStateException("JWT key could not be initialized.");
            }
            System.out.println("JWT key successfully initialized.");
        } catch (Exception e) {
            throw new IllegalStateException("Exception occurred while initializing JWT key.", e);
        }
    }


    public String generateToken(Authentication authentication) {

        if (key == null) {
            throw new IllegalStateException("JWT key is not initialized.");
        }

        User principal = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .signWith(key) 
                .compact();
    }
    

    public boolean validateToken(String jwt) {
        
            JwtParser parser = Jwts.parserBuilder()
                                   .setSigningKey(key)
                                   .build();
            parser.parseClaimsJws(jwt);
            return true;
       
    }

    public String getUsernameFromJWT(String token) {
        @SuppressWarnings("deprecation")
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}
