package com.example.memora.service;

import com.example.memora.model.Wishes;
import com.example.memora.repository.WishRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishService {
    private final WishRepository repository;

    public WishService(WishRepository repository) {this.repository = repository;}

    public List<Wishes> getWishes (int id) {return repository.getWishes(id);}

    public void updateWish(Wishes wish) {repository.updateWish(wish);}

    public void removeWish(int wishId) {repository.removeWish(wishId);}

    public int saveWishes(Wishes wishes, int wishlistId) {return repository.saveWishes(wishes, wishlistId);}




}
