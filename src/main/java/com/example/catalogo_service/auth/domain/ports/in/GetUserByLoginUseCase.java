package com.example.catalogo_service.auth.domain.ports.in;

import com.example.catalogo_service.auth.domain.model.User;

// Caso de uso: buscar un usuario final por su login
public interface GetUserByLoginUseCase {
    // Devuelve el usuario con ese login o lanza excepción si no existe
    User getUserByLogin(String loginName);
}
