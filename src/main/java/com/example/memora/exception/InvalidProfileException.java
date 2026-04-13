package com.example.memora.exception;

public class InvalidProfileException extends RuntimeException {
    public InvalidProfileException(String message) {
        super(message);
    }
}
