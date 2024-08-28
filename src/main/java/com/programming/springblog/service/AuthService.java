package com.programming.springblog.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import com.programming.springblog.dto.LoginRequest;
import com.programming.springblog.dto.RegisterRequest;
import com.programming.springblog.model.User;
import com.programming.springblog.repository.UserRepository;
import com.programming.springblog.security.JwtProvider;


@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

     @Autowired
    private AuthenticationManager authenticationManager;

     @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private EmailService emailService;


    public User signup(RegisterRequest registerRequest) {

        User user = new User();
        user.setUserName(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encodePassword(registerRequest.getPassword()));

    
        User savedUser = userRepository.save(user);

        // Send registration email
        emailService.sendRegistrationEmail(user.getEmail(), user.getUserName());

        return savedUser;
        
    }


    private String encodePassword(String password) {
        return passwordEncoder.encode(password);

    }
    
    
    public String login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        return jwtProvider.generateToken(authenticate);
    }


    public Optional<org.springframework.security.core.userdetails.User> getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return Optional.of(principal);
    }

    public void authenticate(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
            )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


    
    
}
