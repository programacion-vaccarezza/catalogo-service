package com.example.catalogo_service.auth.domain.ports.out;

import com.example.catalogo_service.auth.domain.model.User;

import java.util.List;

// Port de salida: generar y leer JWT, tanto de usuario final como de cuenta técnica
public interface TokenProvider {
    // Genera el JWT de un usuario final, firmado con la clave de usuarios
    String generateToken(User user);
    // Genera el JWT de una cuenta técnica, firmado con la clave de servicio y con ROLE_SERVICE
    String generateServiceToken(String subject);
    // Extrae el login de un token
    String getLoginFromToken(String token);
    // Extrae los roles de un token
    List<String> getRolesFromToken(String token);
    // Valida firma y vigencia de un token
    boolean isTokenValid(String token);
}
