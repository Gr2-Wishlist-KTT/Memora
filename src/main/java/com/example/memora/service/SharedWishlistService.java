package com.example.memora.service;

import com.example.memora.model.SharedWishlist;
import com.example.memora.model.Wishlist;
import com.example.memora.repository.SharedWishlistRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SharedWishlistService {

    private final SharedWishlistRepository sharedWishlistRepository;
    private final WishlistService wishlistService;

    public SharedWishlistService(SharedWishlistRepository sharedWishlistRepository, WishlistService wishlistService){
        this.sharedWishlistRepository = sharedWishlistRepository;
        this.wishlistService = wishlistService;
    }

    public List<Wishlist> getWishlistsSharedWithUser(int userId ) {
        List<SharedWishlist> shares = sharedWishlistRepository.findSharesForUser(userId);

        List<Wishlist> wishlists = new ArrayList<>();
        for (SharedWishlist share : shares) {
            Wishlist wishlist = wishlistService.getWishlist(share.getWishlistId());
            wishlists.add(wishlist);
        }

        return wishlists;
    }

    public void shareWishlist(int wishlistId, int userId) {
        sharedWishlistRepository.addShare(wishlistId, userId);
    }
}
