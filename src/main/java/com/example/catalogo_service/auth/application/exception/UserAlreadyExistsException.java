package com.example.catalogo_service.auth.application.exception;

// Se lanza cuando se intenta registrar un login que ya está en uso
public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String identifier) {
        super("El usuario ya existe: " + identifier);
    }
}
