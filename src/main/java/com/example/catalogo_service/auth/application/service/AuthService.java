package com.example.catalogo_service.auth.application.service;

import com.example.catalogo_service.auth.domain.model.User;
import com.example.catalogo_service.auth.domain.ports.in.AuthenticateServiceUseCase;
import com.example.catalogo_service.auth.domain.ports.in.GetUserByLoginUseCase;
import com.example.catalogo_service.auth.domain.ports.in.LoginUserUseCase;
import com.example.catalogo_service.auth.domain.ports.in.RegisterUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUserUseCase loginUserUseCase;
    private final AuthenticateServiceUseCase authenticateServiceUseCase;
    private final GetUserByLoginUseCase getUserByLoginUseCase;

    public User registerUser(User user) {
        return registerUserUseCase.registerUser(user);
    }

    public String loginUser(String loginName, String password) {
        return loginUserUseCase.loginUser(loginName, password);
    }

    public String authenticateService(String clientId, String clientSecret) {
        return authenticateServiceUseCase.authenticateService(clientId, clientSecret);
    }

    public User getUserByLogin(String loginName) {
        return getUserByLoginUseCase.getUserByLogin(loginName);
    }
}
