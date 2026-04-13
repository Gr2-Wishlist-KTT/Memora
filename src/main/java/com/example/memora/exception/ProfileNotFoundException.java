package com.example.memora.exception;

public class ProfileNotFoundException extends RuntimeException {
    public ProfileNotFoundException(int id) {
        super("User not found");
    }
}
