package com.example.catalogo_service.auth.domain.ports.in;

import com.example.catalogo_service.auth.domain.model.User;

// Caso de uso: registrar un usuario final nuevo
public interface RegisterUserUseCase {
    // Guarda el usuario (hasheando la contraseña) y lo devuelve ya persistido
    User registerUser(User user);
}
