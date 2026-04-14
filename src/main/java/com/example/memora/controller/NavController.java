package com.example.memora.controller;

import com.example.memora.model.User;
import com.example.memora.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("memora")
public class NavController {

//    private final UserService userService;
//
//    public NavController (UserService service){
//        this.userService = service;
//    }
//
//    @GetMapping()
//    public String loggedIn (HttpSession sessionStatus){
//        if (userService.isLoggedIn(sessionStatus)){
//            return frontPage();
//        }
//        else return aboutUs();
//    }

    @GetMapping()
    public String frontPage() {
        return "frontpage";
    }

    @GetMapping("/about")
    public String aboutUs() {
        return "about";
    }

    @GetMapping("/contact")
    public String contactInformation() {
        return "contact";
    }

}
