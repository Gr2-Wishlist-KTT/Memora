package com.example.memora.model;

public class WishList {
    private int id;
    private String owner;
    private String title;


    // KONTRUKTØR
    public WishList(int id, String owner, String title) {
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
