package com.example.memora.controller;

import com.example.memora.model.User;
import com.example.memora.model.Wish;
import com.example.memora.service.WishService;
import jakarta.servlet.http.HttpSession;
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
    public String saveWishes(@ModelAttribute Wish wish, @PathVariable int wishlistId) {
        wishService.saveWishes(wish, wishlistId);
        return "redirect:/wishlists/" + wishlistId;
    }

    @GetMapping("/{wishId}")
    public String findWish(@PathVariable int wishId, Model model, @PathVariable int wishlistId) {

         Wish wish = wishService.findWish(wishId);
        model.addAttribute("wish", wishService.findWish(wishId));
        model.addAttribute("wishlistId", wishlistId);
        return "wish/wish";
    }

    @GetMapping("/{wishID}/edit")
    public String editWish(@PathVariable int wishlistId, @PathVariable int wishID, Model model) {
        model.addAttribute("wish", wishService.findWish(wishID));
        model.addAttribute("wishlist", wishlistId);
        return "wish/editWish";
    }

    @PostMapping("/{wishID}/delete")
    public String removeWish(@PathVariable int wishID, @PathVariable int wishlistId) {
        wishService.removeWish(wishID);
        return "redirect:/wishlists/" + wishlistId;
    }
    @PostMapping("/{wishID}")
    public String updateWish(@PathVariable int wishlistId,
                             @PathVariable int wishID,
                             @ModelAttribute Wish wish) {
        wish.setId(wishID);

        wishService.updateWish(wish);

        return "redirect:/wishlists/" + wishlistId;
    }
}