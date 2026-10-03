package com.example.catalogo_service.auth.application.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String identifier) {
        super("User already exists: " + identifier);
    }
}
