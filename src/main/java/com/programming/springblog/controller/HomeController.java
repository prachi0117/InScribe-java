package com.programming.springblog.controller;

import com.programming.springblog.dto.PostDto;
import com.programming.springblog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private final PostService postService;

    @Autowired
    public HomeController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public String homePage(Model model) {
       
        return "main"; // returns the view to display all posts
    }
}
