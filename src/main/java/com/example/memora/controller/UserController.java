package com.example.memora.controller;

import com.example.memora.model.User;
import com.example.memora.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/memora")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    // CHECK LOGIN (for at kunne komme videre til næste side)
//    @GetMapping("/{email}")
//    public String UserLogin(@PathVariable String email, Model model) {
//        User user = service.findUserByEmail(email);
//        model.addAttribute("user", user);
//        return "user";
//    }

    // REGISTER USER
    @GetMapping("/add")
    public String registerUser(Model model) {
        model.addAttribute("user", new User());
        return "login/register";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute User user) {
        service.saveUser(user);
        return "redirect:/memora";
    }


    // LOGIN


    @GetMapping("login")
    public String showLogin() {
        return "login/userLogin";
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session, Model model) {
        if (service.login(email, password)) {

        session.setAttribute("user", service.findUserByEmail(email));
       return "redirect:/memora/wishlists";}

        // WRONG INPUT
        model.addAttribute("wrongCredentials", true);
        return "login/userLogin";
    }



    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "xxxxxxxxxxxxx";
    }



}
