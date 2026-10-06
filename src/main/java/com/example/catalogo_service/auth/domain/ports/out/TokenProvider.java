package com.example.catalogo_service.auth.domain.ports.out;

import com.example.catalogo_service.auth.domain.model.User;

import java.util.List;

public interface TokenProvider {
    String generateToken(User user);
    String generateServiceToken(String subject);
    String getLoginFromToken(String token);
    List<String> getRolesFromToken(String token);
    boolean isTokenValid(String token);
}
