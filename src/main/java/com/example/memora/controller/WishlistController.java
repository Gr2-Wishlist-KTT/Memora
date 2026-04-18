package com.example.memora.controller;

import com.example.memora.model.User;
import com.example.memora.model.Wish;
import com.example.memora.model.WishView;
import com.example.memora.model.Wishlist;
import com.example.memora.service.ReservationService;
import com.example.memora.service.SharedWishlistService;
import com.example.memora.service.WishService;
import com.example.memora.service.WishlistService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/wishlists")
public class WishlistController {
    private final WishlistService wishlistService;
    private final WishService wishService;
    private final SharedWishlistService sharedWishlistService;
    private final ReservationService reservationService;

    public WishlistController(WishlistService wishlistService, WishService wishService, SharedWishlistService sharedWishlistService, ReservationService reservationService) {
        this.wishlistService = wishlistService;
        this.wishService = wishService;
        this.sharedWishlistService = sharedWishlistService;
        this.reservationService = reservationService;
    }

    @GetMapping
    public String getMyWishlists(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }

        model.addAttribute("myWishlists", wishlistService.getWishlists(user.getId()));
        model.addAttribute("sharedWishlists", sharedWishlistService.getWishlistsSharedWithUser(user.getId()));
        return "wishlist/myWishlists";
    }

    @GetMapping("/create")
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
    public String showWishlist(@PathVariable int wishlistID, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Wishlist wishlist = wishlistService.getWishlist(wishlistID);
        boolean canEdit = wishlistService.canEdit(user, wishlist);

        List<Wish> wishes = wishService.getWishes(wishlistID);

        List<WishView> wishViews = new ArrayList<>();

        for (Wish wish : wishes) {
            WishView view = new WishView();
            view.setWish(wish);
            view.setReserved(reservationService.isReserved(wish.getId()));
            view.setReservedByMe(reservationService.isReservedByUser(wish.getId(), user.getId()));
            wishViews.add(view);
        }

        model.addAttribute("wishlist", wishlist);
        model.addAttribute("canEdit", canEdit);
        model.addAttribute("wishViews", wishViews);
        model.addAttribute("user", user);

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
        return "redirect:/wishlists";
    }

    @PostMapping("/{wishlistID}/delete")
    public String deleteWishlist(@PathVariable int wishlistID) {
        wishlistService.deleteWishlist(wishlistID);
        return "redirect:/wishlists";
    }

    @PostMapping("/{wishlistId}/wishes/{wishId}/reserve")
    public String reserveWish(@PathVariable int wishlistId,
                              @PathVariable int wishId,
                              HttpSession session) {

        User user = (User) session.getAttribute("user");
        reservationService.reserveWish(wishId, user.getId());

        return "redirect:/wishlists/" + wishlistId;
    }

    @PostMapping("/{wishlistId}/wishes/{wishId}/unreserve")
    public String unreserveWish(@PathVariable int wishlistId, @PathVariable int wishId) {

        reservationService.unreserveWish(wishId);

        return "redirect:/wishlists/" + wishlistId;
    }
}