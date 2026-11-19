package com.example.catalogo_service.auth.application.exception;

// Se lanza cuando el login falla porque el usuario o la contraseña no coinciden
public class InvalidCredentialsException extends RuntimeException {
    // Mensaje fijo, no expone si el usuario existe o no
    public InvalidCredentialsException() {
        super("Usuario o contraseña incorrectos");
    }
}
