package com.example.memora.service;

import com.example.memora.model.Wish;
import com.example.memora.repository.WishRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishService {
    private final WishRepository repository;

    public WishService(WishRepository repository) {
        this.repository = repository;
    }

    public List<Wish> getWishes(int id) {
        return repository.getWishes(id);
    }

    public void updateWish(Wish wish) {
        repository.updateWish(wish);
    }

    public void removeWish(int wishId) {
        repository.removeWish(wishId);
    }

    public int saveWishes(Wish wish, int wishlistId) {
        return repository.saveWishes(wish, wishlistId);
    }

    public Wish findWish(int id) {
        return repository.findWish(id);
    }
}
