package com.example.catalogo_service.sync.infrastructure.client.dto;

import lombok.*;

import java.time.Instant;

// Profesional tal como viene en el JSON del snapshot REST de la cátedra
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalSnapshotDto {

    private Long id;
    private Long categoryId;
    private String firstName;
    private String lastName;
    private boolean enabled;
    private Instant createdAt;
    private Instant updatedAt;
}
