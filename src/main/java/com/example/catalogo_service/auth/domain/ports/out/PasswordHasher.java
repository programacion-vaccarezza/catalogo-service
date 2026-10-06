package com.example.catalogo_service.auth.domain.ports.out;

public interface PasswordHasher {
    String hash(String rawPassword);
    boolean matches(String rawPassword, String hashedPassword);
}
