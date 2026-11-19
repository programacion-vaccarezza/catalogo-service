package com.example.catalogo_service.auth.infrastructure.web.dto;

import lombok.*;

// Respuesta de client credentials: el JWT técnico del servicio
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceTokenResponse {
    private String token;
}
