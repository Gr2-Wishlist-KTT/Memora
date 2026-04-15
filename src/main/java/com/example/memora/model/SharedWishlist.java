package com.example.memora.model;

public class SharedWishlist {

    private int id;
    private int wishlistId;
    private int sharedWithUserId;

    public SharedWishlist(){}

    public SharedWishlist(int id, int wishlistId, int sharedWithUserId) {
        this.id = id;
        this.wishlistId = wishlistId;
        this.sharedWithUserId = sharedWithUserId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(int wishlistId) {
        this.wishlistId = wishlistId;
    }

    public int getSharedWithUserId() {
        return sharedWithUserId;
    }

    public void setSharedWithUserId(int sharedWithUserId) {
        this.sharedWithUserId = sharedWithUserId;
    }
}
