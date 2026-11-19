package com.example.catalogo_service.auth.domain.ports.out;

// Port de salida: hashear y comparar contraseñas
public interface PasswordHasher {
    // Genera el hash de una contraseña
    String hash(String rawPassword);
    // Compara una contraseña contra un hash ya guardado
    boolean matches(String rawPassword, String hashedPassword);
}
