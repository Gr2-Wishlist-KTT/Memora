package com.example.memora.controller;

import com.example.memora.model.User;
import com.example.memora.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    // REGISTER USER
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/auth/login";
    }

    // LOGIN
    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        if (userService.login(email, password)) {
            session.setAttribute("user", userService.findUserByEmail(email));
            return "redirect:/wishlists";
        }
        // WRONG INPUT
        model.addAttribute("wrongCredentials", true);
        return "auth/login";
    }

    @GetMapping("/editProfile")
    public String showEditProfile(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        return "auth/editProfile";
    }

    @PostMapping("/editProfile")
    public String editProfile(@ModelAttribute User profile, HttpSession session){
        userService.editProfile(profile);

        session.setAttribute("user", profile);
        return "redirect:/wishlists";

    }

    @GetMapping("/forgot-password")
    public String showForgotPassword(){
        return "auth/forgot-password";

    }

    @PostMapping("/forgot-password")
    public String forgotPassword (@RequestParam String email,
                                  @RequestParam String newPassword, Model model){

        User user = userService.findUserByEmail(email);

        if (user == null) {
            model.addAttribute("EmailNotFound", true);
            return "auth/forgot-password";
        }
        user.setPassword(newPassword);
        userService.editProfile(user);

        return "redirect:/auth/login";
    }



    // MANGLER DENNE METOEDE !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! AT BLIVE IMPLEMENTERET
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}