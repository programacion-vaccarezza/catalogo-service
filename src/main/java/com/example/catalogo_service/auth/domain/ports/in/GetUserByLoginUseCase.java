package com.example.catalogo_service.auth.domain.ports.in;

import com.example.catalogo_service.auth.domain.model.User;

public interface GetUserByLoginUseCase {
    User getUserByLogin(String loginName);
}
