package com.example.catalogo_service.auth.infrastructure.web.dto;

import lombok.*;

// Respuesta del login: el JWT del usuario
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String token;
}
