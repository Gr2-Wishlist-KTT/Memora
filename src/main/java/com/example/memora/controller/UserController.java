package com.example.memora.controller;

import com.example.memora.model.User;
import com.example.memora.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String registerUser(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "registernewuser";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute User user){
        service.saveUser(user);
        return "redirect:/user";
    }
}
