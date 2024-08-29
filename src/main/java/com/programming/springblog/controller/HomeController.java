package com.programming.springblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {


    @GetMapping("/")
    public String homePage(Model model) {
       
        return "main"; // returns the view to display all posts
    }
}
