package com.example.memora.service;


import com.example.memora.model.WishList;
import com.example.memora.repository.WishListRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {
    private final WishListRepository repository;

    public WishlistService(WishListRepository repository) {this.repository = repository;}

    public List<WishList> getWishlist (int id) {return repository.getWishList(id);}


    public int createWishlist(String title, int ownerId) {return repository.createWishlist(title, ownerId);}

    public void updateWishlist(int id, String title) {repository.updateWishlist(id, title);}
}

