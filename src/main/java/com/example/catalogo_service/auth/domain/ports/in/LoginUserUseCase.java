package com.example.catalogo_service.auth.domain.ports.in;

public interface LoginUserUseCase {
    String loginUser(String loginName, String password);
}
