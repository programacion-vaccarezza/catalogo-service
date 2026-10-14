package com.example.catalogo_service.auth.domain.ports.out;

public interface ServiceCredentialsValidator {
    boolean isValid(String clientId, String clientSecret);
}
