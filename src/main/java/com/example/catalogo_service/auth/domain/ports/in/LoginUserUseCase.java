package com.example.catalogo_service.auth.domain.ports.in;

// Caso de uso: autenticar a un usuario final con login y contraseña
public interface LoginUserUseCase {
    // Valida las credenciales y devuelve el JWT del usuario
    String loginUser(String loginName, String password);
}
