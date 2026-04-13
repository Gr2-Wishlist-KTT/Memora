package com.example.memora.controller;

import com.example.memora.model.User;
import com.example.memora.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
// @RequestMapping("memora")
public class UserController {

    private final UserService service;

    public UserController (UserService service){
        this.service = service;
    }

// CHECK LOGIN (for at kunne komme videre til næste side)
    @GetMapping("/{email}")
    public String UserLogin(@PathVariable String email, Model model){
        User user = service.findUserById(email);
        model.addAttribute("user", user);
        return "user";
    }

    // REGISTER USER
    @GetMapping("/add")
    public String registerUser
}
