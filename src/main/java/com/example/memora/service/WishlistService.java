package com.example.memora.service;


import com.example.memora.model.Wishlist;
import com.example.memora.repository.WishlistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {
    private final WishlistRepository repository;

    public WishlistService(WishlistRepository repository) {
        this.repository = repository;
    }

    public Wishlist getWishlist(int wishlistID) {
        return repository.getWishList(wishlistID);
    }

    public List<Wishlist> getWishlists(int owner) {
        return repository.getWishLists(owner);
    }

    public void saveWishlist(Wishlist wishlist, int ownerId) {
        repository.createWishlist(wishlist, ownerId);
    }

    public void updateWishlist(int id, Wishlist wishlist) {
        repository.updateWishlist(id, wishlist);
    }
}

