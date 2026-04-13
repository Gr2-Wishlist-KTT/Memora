package com.example.memora.model;

import java.util.List;

public class User {
    private int id;
    private String name;
    private String password;
    private String email;

    public User (int id, String name, String password, String email){
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
    }

   public User(){
   }
// GETTER METHODS
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword(){
        return password;
    }

    public String getEmail() {
        return email;
    }

// SETTER METHODS
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
