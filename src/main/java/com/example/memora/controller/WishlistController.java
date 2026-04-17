package com.example.memora.controller;

import com.example.memora.model.User;
import com.example.memora.model.WishList;
import com.example.memora.service.WishlistService;
import jakarta.servlet.http.HttpSession;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/memora")
public class WishlistController {
    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping("/wishlists")
    public String getWishlists(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("wishlists", wishlistService.getWishlists(user.getId()));
        return "showWishlists";
    }
    @GetMapping("/wishlist/new")
    public String createWishlistForm(Model model) {
        model.addAttribute("wishlist", new WishList());
        return "createWishlist";

    }
    @PostMapping("/wishlist")
    public String createWishlist(@ModelAttribute WishList wishlist,
                                 HttpSession session) {

        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        wishlistService.createWishlist(
                wishlist.getTitle(),
                user.getId()
        );

        return "redirect:/memora/wishlists";
    }
}
