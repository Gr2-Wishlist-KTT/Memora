package com.example.memora.controller;

import com.example.memora.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NavController {

    @GetMapping("/")
    public String frontPage(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "nav/frontpage";
        }
        return "redirect:/wishlists";
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
