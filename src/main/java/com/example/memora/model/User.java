package com.example.memora.model;

import java.util.List;

public class User {
    private int id;
    private String userName;
    private String password;
    private String email;

    public User (int id, String name, String password, String email){
        this.id = id;
        this.userName = name;
        this.password = password;
        this.email = email;
    }

   public User(){
   }
// GETTER METHODS
    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
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

    public void setUserName(String name) {
        this.userName = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
