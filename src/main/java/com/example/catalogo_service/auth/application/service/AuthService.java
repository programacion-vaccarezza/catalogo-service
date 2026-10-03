package com.example.catalogo_service.auth.application.service;

import com.example.catalogo_service.auth.domain.model.User;
import com.example.catalogo_service.auth.domain.ports.in.LoginUserUseCase;
import com.example.catalogo_service.auth.domain.ports.in.RegisterUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUserUseCase loginUserUseCase;

    public User registerUser(User user) {
        return registerUserUseCase.registerUser(user);
    }

    public String loginUser(String loginName, String password) {
        return loginUserUseCase.loginUser(loginName, password);
    }
}
