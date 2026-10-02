package com.example.catalogo_service.auth.domain.ports.out;

import com.example.catalogo_service.auth.domain.model.User;

public interface TokenProvider {
    String generateToken(User user);
    String getLoginFromToken(String token);
    boolean isTokenValid(String token);
}
