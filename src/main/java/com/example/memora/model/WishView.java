package com.example.memora.model;

public class WishView {
    private Wish wish;
    private boolean reserved;
    private boolean reservedByMe;

    public Wish getWish() {
        return wish;
    }

    public void setWish(Wish wish) {
        this.wish = wish;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public boolean isReservedByMe() {
        return reservedByMe;
    }

    public void setReservedByMe(boolean reservedByMe) {
        this.reservedByMe = reservedByMe;
    }
}