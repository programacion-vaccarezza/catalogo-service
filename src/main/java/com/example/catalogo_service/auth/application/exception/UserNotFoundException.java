package com.example.catalogo_service.auth.application.exception;

// Se lanza cuando se busca un usuario por login y no existe
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String identifier) {
        super("Usuario no encontrado: " + identifier);
    }
}
