package com.example.backend.exception;

public class IssueLimitExceededException extends RuntimeException {

    public IssueLimitExceededException(String message) {
        super(message);
    }
}