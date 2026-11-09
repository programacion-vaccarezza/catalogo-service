package com.example.catalogo_service.catalog.infrastructure.web.dto;

import lombok.*;

// Respuesta pública de profesional
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalResponse {

    private Long id;
    private Long categoryId;
    private String firstName;
    private String lastName;
    private boolean enabled;
}
