package com.programming.springblog.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programming.springblog.dto.LoginRequest;
import com.programming.springblog.dto.RegisterRequest;
import com.programming.springblog.service.AuthService;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    
    @Autowired
    private AuthService authService;

    @SuppressWarnings("rawtypes")
    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody RegisterRequest registerRequest) {
        authService.signup(registerRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

    
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {
        try {
            String token = authService.login(loginRequest); // Authenticate user and get token
            return ResponseEntity.ok(Map.of("token", token)); // Return token in JSON format
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("errorMessage", e.getMessage())); // Return error message
        }
    }
    
}

