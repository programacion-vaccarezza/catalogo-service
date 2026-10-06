package com.example.catalogo_service.auth.application.usecases;

import com.example.catalogo_service.auth.application.exception.InvalidCredentialsException;
import com.example.catalogo_service.auth.domain.ports.in.AuthenticateServiceUseCase;
import com.example.catalogo_service.auth.domain.ports.out.ServiceCredentialsValidator;
import com.example.catalogo_service.auth.domain.ports.out.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticateServiceUseCaseImpl implements AuthenticateServiceUseCase {

    private final ServiceCredentialsValidator serviceCredentialsValidator;
    private final TokenProvider tokenProvider;

    @Override
    public String authenticateService(String clientId, String clientSecret) {
        if (!serviceCredentialsValidator.isValid(clientId, clientSecret)) {
            throw new InvalidCredentialsException();
        }

        return tokenProvider.generateServiceToken(clientId);
    }
}
