package com.example.catalogo_service.auth.domain.ports.in;

// Caso de uso: autenticar la cuenta técnica de un servicio y devolverle un JWT de servicio
public interface AuthenticateServiceUseCase {
    // Valida las credenciales de la cuenta técnica y genera su JWT
    String authenticateService(String clientId, String clientSecret);
}
