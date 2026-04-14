package com.example.memora.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NavController {

    @GetMapping("/")
    public String frontPage() {
        return "nav/frontpage";
    }

    @GetMapping("/about")
    public String about() {
        return "nav/about";
    }

    @GetMapping("/contact")
    public String contact() {
        return "nav/contact";
    }
}
