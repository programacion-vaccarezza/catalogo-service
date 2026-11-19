package com.example.catalogo_service.catalog.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.Instant;

// Entidad JPA de la tabla categories, separada del modelo de dominio Category
@Getter
@Setter
@Entity
@Table(name = "categories")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryEntity {

    @Id
    private Long id;

    private String name;
    private String description;
    private boolean enabled;
    private Instant createdAt;
    private Instant updatedAt;
}
