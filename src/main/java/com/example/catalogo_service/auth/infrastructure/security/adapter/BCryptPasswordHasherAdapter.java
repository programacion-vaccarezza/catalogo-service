package com.example.catalogo_service.auth.infrastructure.security.adapter;

import com.example.catalogo_service.auth.domain.ports.out.PasswordHasher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

// Adapter que implementa PasswordHasher con BCrypt
@Component
public class BCryptPasswordHasherAdapter implements PasswordHasher {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // Genera el hash BCrypt de la contraseña
    @Override
    public String hash(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    // Compara la contraseña contra el hash BCrypt guardado
    @Override
    public boolean matches(String rawPassword, String hashedPassword) {
        return encoder.matches(rawPassword, hashedPassword);
    }
}
