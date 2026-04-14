package com.example.memora.controller;

import com.example.memora.model.User;
import com.example.memora.model.Wishlist;
import com.example.memora.service.WishService;
import com.example.memora.service.WishlistService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/wishlists")
public class WishlistController {
    private final WishlistService wishlistService;
    private final WishService wishService;

    public WishlistController(WishlistService wishlistService, WishService wishService) {
        this.wishlistService = wishlistService;
        this.wishService = wishService;
    }

    @GetMapping
    public String getMyWishlists(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }

        model.addAttribute("wishlists", wishlistService.getWishlists(user.getId()));
        return "wishlist/myWishlists";
    }

    @GetMapping("/new")
    public String newWishlist(Model model) {
        model.addAttribute("wishlist", new Wishlist());
        return "wishlist/addNewWishlist";
    }

    @PostMapping
    public String saveWishlist(@ModelAttribute Wishlist wishlist, HttpSession session) {
        User user = (User) session.getAttribute("user");
        wishlistService.saveWishlist(wishlist, user.getId());
        return "redirect:/wishlists";
    }

    @GetMapping("/{wishlistID}")
    public String showWishlist(@PathVariable int wishlistID, Model model) {
        model.addAttribute("wishlistID", wishlistID);
        model.addAttribute("wishes", wishService.getWishes(wishlistID));
        return "wishlist/wishlist";
    }

    @GetMapping("/{wishlistID}/edit")
    public String editWishlist(@PathVariable int wishlistID, Model model) {
        model.addAttribute("wishlist", wishlistService.getWishlist(wishlistID));
        return "wishlist/editWishlist";
    }

    @PostMapping("/{wishlistID}")
    public String updateWishlist(@ModelAttribute Wishlist wishlist, @PathVariable int wishlistID) {
        wishlistService.updateWishlist(wishlistID, wishlist);
        return "redirect:/wishlists/" + wishlistID;
    }

    // mangler delete metode i service
//    @PostMapping("/{wishlistID}/delete")
//    public String deleteWishlist(@PathVariable int wishlistID) {
//        wishlistService.deleteWishlist(wishlistID);
//        return "redirect:/wishlists";
//    }
}