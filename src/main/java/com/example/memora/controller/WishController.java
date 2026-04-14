package com.example.memora.controller;

import com.example.memora.model.Wish;
import com.example.memora.service.WishService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/wishlists/{wishlistId}/wishes")
public class WishController {
    private final WishService wishService;

    public WishController(WishService wishService){
        this.wishService = wishService;
    }

    @GetMapping("/new")
    public String newWish(@PathVariable int wishlistId, Model model) {
        model.addAttribute("wish", new Wish());
        model.addAttribute("wishlistID", wishlistId);
        return "wish/addNewWish";
    }

    @PostMapping
    public String saveWish(@ModelAttribute Wish wish, @PathVariable int wishlistId) {
        wishService.saveWishes(wish, wishlistId);
        return "redirect:/wishlists/" + wishlistId;
    }
}