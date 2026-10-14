package com.example.catalogo_service.auth.domain.ports.in;

public interface AuthenticateServiceUseCase {
    String authenticateService(String clientId, String clientSecret);
}
