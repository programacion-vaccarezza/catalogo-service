package com.example.catalogo_service.auth.infrastructure.web.dto;

import lombok.*;

// Respuesta del endpoint interno de usuario: solo los datos que necesita turnos-service
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InternalUserResponse {
    private String login;
    private String firstName;
    private String lastName;
}
