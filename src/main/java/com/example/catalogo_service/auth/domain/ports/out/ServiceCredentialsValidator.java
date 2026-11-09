package com.example.catalogo_service.auth.domain.ports.out;

// Port de salida: validar las credenciales de la cuenta técnica configurada
public interface ServiceCredentialsValidator {
    // Compara clientId/clientSecret contra los configurados
    boolean isValid(String clientId, String clientSecret);
}
