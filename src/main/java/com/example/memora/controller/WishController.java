package com.example.memora.controller;

import com.example.memora.model.Wish;
import com.example.memora.service.WishService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/memora")
public class WishController {
    private final WishService wishService;

    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @GetMapping("/{wishlistID}")
    public String getWishes(Model model, @PathVariable int wishlistID) {
        model.addAttribute("wishes", wishService.getWishes(wishlistID));
        return "wishlist";
    }

    @GetMapping("/{wishlistID}/newWish")
    public String addNewWish(Model model, @PathVariable int wishlistID) {
        model.addAttribute("wish", new Wish());
        model.addAttribute("wishlistID", wishlistID);
        return "addNewWish";
    }

    @PostMapping("/{wishlistID}/saveWish")
    public String saveWish(@ModelAttribute Wish wish, @PathVariable int wishlistID) {
        wishService.saveWishes(wish, wishlistID);
        return "redirect:/memora/" + wishlistID;
    }

    @GetMapping("/wish/{id}/edit")
    public String editWish(@PathVariable int id, Model model) {
        Wish wish = wishService.findWishById(id);
        model.addAttribute("wish", wish);
        return "editWish";
    }
}
