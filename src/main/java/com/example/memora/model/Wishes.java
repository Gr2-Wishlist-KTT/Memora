package com.example.memora.model;

public class Wishes {
    private int id;
    private String productName;
    private String description;
    private int quantity;
    private double price;
    private String linkToProduct;

    // KONTRUKTØR

    public Wishes() {}

    public Wishes(int id, String productName, String description, int quantity, double price, String linkToProduct) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.linkToProduct = linkToProduct;
    }


    // GETTER'S & SETTER'S
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getLinkToProduct() {
        return linkToProduct;
    }

    public void setLinkToProduct(String linkToProduct) {
        this.linkToProduct = linkToProduct;
    }
}