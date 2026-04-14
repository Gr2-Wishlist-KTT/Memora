package com.example.memora.controller;

import com.example.memora.service.WishlistService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/memora")
public class WishlistController {
    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping("/wishlists/{owner}")
    public String getWishlists(Model model,@PathVariable int owner) {
        model.addAttribute("wishlists", wishlistService.getWishlists(owner));
        return "showWishlists";
    }
}
