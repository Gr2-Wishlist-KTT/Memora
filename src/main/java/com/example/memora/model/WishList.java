package com.example.memora.model;

public class WishList {
    private int id;
    private int owner;
    private String title;


    // KONTRUKTØR

    public WishList() {
    }

    public WishList(int id, int owner, String title) {
        this.id = id;
        this.owner = owner;
        this.title = title;
    }

    // GETTER'S & SETTER'S
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
