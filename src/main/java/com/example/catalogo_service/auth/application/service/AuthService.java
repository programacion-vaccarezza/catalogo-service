package com.example.catalogo_service.auth.application.service;

import com.example.catalogo_service.auth.domain.model.User;
import com.example.catalogo_service.auth.domain.ports.in.AuthenticateServiceUseCase;
import com.example.catalogo_service.auth.domain.ports.in.GetUserByLoginUseCase;
import com.example.catalogo_service.auth.domain.ports.in.LoginUserUseCase;
import com.example.catalogo_service.auth.domain.ports.in.RegisterUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// Fachada que agrupa los casos de uso de autenticación para que el controller hable con una sola clase
@Service
@RequiredArgsConstructor
public class AuthService {

    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUserUseCase loginUserUseCase;
    private final AuthenticateServiceUseCase authenticateServiceUseCase;
    private final GetUserByLoginUseCase getUserByLoginUseCase;

    // Registra un usuario final nuevo
    public User registerUser(User user) {
        return registerUserUseCase.registerUser(user);
    }

    // Autentica un usuario final y devuelve su JWT
    public String loginUser(String loginName, String password) {
        return loginUserUseCase.loginUser(loginName, password);
    }

    // Autentica la cuenta técnica de turnos-service y devuelve su JWT de servicio
    public String authenticateService(String clientId, String clientSecret) {
        return authenticateServiceUseCase.authenticateService(clientId, clientSecret);
    }

    // Busca un usuario final por su login
    public User getUserByLogin(String loginName) {
        return getUserByLoginUseCase.getUserByLogin(loginName);
    }
}
