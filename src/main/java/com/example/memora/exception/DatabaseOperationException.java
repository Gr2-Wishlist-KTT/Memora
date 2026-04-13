package com.example.memora.exception;

public class DatabaseOperationException extends RuntimeException {
    public DatabaseOperationException(String message, Throwable casuse) {
        super(message, casuse);
    }
}
