package com.programming.springblog.controller;

import com.programming.springblog.dto.CommentDto;
import com.programming.springblog.dto.LoginRequest;
import com.programming.springblog.dto.PostDto;
import com.programming.springblog.dto.RegisterRequest;
import com.programming.springblog.model.User;
import com.programming.springblog.service.AuthService;
import com.programming.springblog.service.CommentService;
import com.programming.springblog.service.EmailService;
import com.programming.springblog.service.PostService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.validation.Valid;

@Controller
public class ViewController {

    @Autowired
    private AuthService authService;

    @Autowired 
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "registerform";
    }

    @PostMapping("/register")
    public String registerUser(@Valid RegisterRequest registerRequest, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "registerform"; // Return to the registration form if there are errors
        }
        try {
            authService.signup(registerRequest); // Call the AuthService to handle registration
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Registration failed: " + e.getMessage());
            return "registerform"; // Return to the form with an error message
        }
        return "redirect:/login?success"; // Redirect to login on successful registration
    }


    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@Valid LoginRequest loginRequest, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "login"; // Return to the login form if there are errors
        }
        try {
            authService.authenticate(loginRequest); // Call the AuthService to handle authentication
            // Send an email with the home page URL
            
            
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Login failed: " + e.getMessage());
            return "login"; // Return to the form with an error message
        }
        return "redirect:/?success=true"; // Redirect to home page on successful login
    }
    
    @PostMapping("/{postId}/comment")
    public String addComment(@PathVariable("postId") int postId, @RequestParam("content") String content) {
        CommentDto commentDto = new CommentDto();
        commentDto.setContent(content);
        commentDto.setId(postId);
        commentService.createComment(commentDto, postId);
        return "redirect:/posts/" + postId;
    }


}
