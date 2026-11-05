package com.example.catalogo_service.auth.infrastructure.security.adapter;

import com.example.catalogo_service.auth.domain.ports.out.ServiceCredentialsValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Component
public class ServiceCredentialsValidatorAdapter implements ServiceCredentialsValidator {

    private final String clientId;
    private final String clientSecret;

    public ServiceCredentialsValidatorAdapter(@Value("${service.client-id}") String clientId,
                                               @Value("${service.client-secret}") String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    @Override
    public boolean isValid(String clientId, String clientSecret) {
        if (!this.clientId.equals(clientId) || clientSecret == null) {
            return false;
        }
        return MessageDigest.isEqual(
                this.clientSecret.getBytes(StandardCharsets.UTF_8),
                clientSecret.getBytes(StandardCharsets.UTF_8));
    }
}
