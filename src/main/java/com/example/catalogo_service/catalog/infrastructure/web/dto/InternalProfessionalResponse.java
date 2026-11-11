package com.example.catalogo_service.catalog.infrastructure.web.dto;

import lombok.*;

// Respuesta del endpoint interno de profesional, para turnos-service
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InternalProfessionalResponse {

    private Long id;
    private Long categoryId;
    private String firstName;
    private String lastName;
    private boolean enabled;
}
