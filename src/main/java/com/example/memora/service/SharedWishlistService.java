package com.example.memora.service;

import com.example.memora.model.SharedWishlist;
import com.example.memora.model.User;
import com.example.memora.model.Wishlist;
import com.example.memora.repository.SharedWishlistRepository;
import com.example.memora.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SharedWishlistService {

    private final SharedWishlistRepository sharedWishlistRepository;
    private final WishlistService wishlistService;
    private final UserRepository userRepository;

    public SharedWishlistService(SharedWishlistRepository sharedWishlistRepository, WishlistService wishlistService, UserRepository userRepository) {
        this.sharedWishlistRepository = sharedWishlistRepository;
        this.wishlistService = wishlistService;
        this.userRepository = userRepository;
    }

    public List<Wishlist> getWishlistsSharedWithUser(int userId) {
        List<SharedWishlist> shares = sharedWishlistRepository.findSharesForUser(userId);

        List<Wishlist> wishlists = new ArrayList<>();
        for (SharedWishlist share : shares) {
            Wishlist wishlist = wishlistService.getWishlist(share.getWishlistId());
            wishlists.add(wishlist);
        }

        return wishlists;
    }

    public List<User> findViewersForWishlist(int wishlistId) {
        List<SharedWishlist> shares = sharedWishlistRepository.findViewersForWishlist(wishlistId);

        List<User> viewers = new ArrayList<>();
        for (SharedWishlist share : shares) {
            User viewer = userRepository.findUserById(share.getSharedWithUserId());
            viewers.add(viewer);
        }

        return viewers;
    }

    public void shareWishlist(int wishlistId, String email) {

        // Find user at dele med
        User targetUser = userRepository.findUserByEmail(email);

        // Hvis brugeren ikke findes
        if (targetUser == null) {
            throw new IllegalArgumentException("Der findes ingen bruger med denne email.");
        }

        int targetUserId = targetUser.getId();

        // Find ejer af ønskeliste
        int ownerId = wishlistService.getWishlist(wishlistId).getOwner();

        // Må ikke dele med sig selv
        if (ownerId == targetUserId) {
            throw new IllegalArgumentException("Du kan ikke dele en ønskeliste med dig selv.");
        }

        // Må ikke dele samme liste med samme bruger flere gange
        if (sharedWishlistRepository.existsShare(wishlistId, targetUserId)) {
            throw new IllegalArgumentException("Listen er allerede delt med denne bruger.");
        }

        // Hvis alt er OK → del listen
        sharedWishlistRepository.addShare(wishlistId, targetUserId);
    }

    public void deleteShare(int wishlistId, int userId) {
        sharedWishlistRepository.deleteShare(wishlistId, userId);
    }
}
