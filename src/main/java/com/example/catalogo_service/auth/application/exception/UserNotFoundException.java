package com.example.catalogo_service.auth.application.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String identifier) {
        super("Usuario no encontrado: " + identifier);
    }
}
