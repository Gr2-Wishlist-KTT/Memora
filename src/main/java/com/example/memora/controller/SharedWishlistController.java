package com.example.memora.controller;

import com.example.memora.model.User;
import com.example.memora.service.SharedWishlistService;
import com.example.memora.service.WishlistService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller()
@RequestMapping("/wishlists/{wishlistId}/share")
public class SharedWishlistController {

    private final SharedWishlistService sharedWishlistService;
    private final WishlistService wishlistService;

    public SharedWishlistController(SharedWishlistService sharedWishlistService, WishlistService wishlistService) {
        this.sharedWishlistService = sharedWishlistService;
        this.wishlistService = wishlistService;
    }

    @GetMapping()
    public String addShareWishlist(Model model, @PathVariable int wishlistId, HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }

        model.addAttribute("wishlist", wishlistService.getWishlist(wishlistId));
        model.addAttribute("viewers", sharedWishlistService.findViewersForWishlist(wishlistId));
        return "wishlist/addShareWishlist";
    }

    @PostMapping()
    public String saveShareWishlist(@PathVariable int wishlistId, @RequestParam String email, Model model) {

        try {
            sharedWishlistService.shareWishlist(wishlistId, email);
            return "redirect:/wishlists/" + wishlistId + "/share";

        } catch (IllegalArgumentException e) {

            model.addAttribute("errorMessage", e.getMessage());

            model.addAttribute("wishlist", wishlistService.getWishlist(wishlistId));
            model.addAttribute("viewers", sharedWishlistService.findViewersForWishlist(wishlistId));

            return "wishlist/addShareWishlist";
        }
    }

    @PostMapping("/delete")
    public String deleteShareWishlist(@PathVariable int wishlistId,
                                      @RequestParam int viewerId,
                                      @RequestParam String source) {

        sharedWishlistService.deleteShare(wishlistId, viewerId);

        if (source.equals("front")) {
            return "redirect:/wishlists";
        }

        return "redirect:/wishlists/" + wishlistId + "/share" +
                "";
    }
}
