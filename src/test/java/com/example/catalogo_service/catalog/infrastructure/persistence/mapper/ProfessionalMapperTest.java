package com.example.catalogo_service.catalog.infrastructure.persistence.mapper;

import com.example.catalogo_service.catalog.domain.model.Professional;
import com.example.catalogo_service.catalog.infrastructure.persistence.entity.ProfessionalEntity;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class ProfessionalMapperTest {

    private final ProfessionalMapper mapper = new ProfessionalMapper();

    @Test
    void debeConvertirAEntity_cuandoElProfesionalEsValido() {
        Instant createdAt = Instant.parse("2026-07-01T12:00:00Z");
        Instant updatedAt = Instant.parse("2026-09-10T13:40:00Z");
        Professional professional = Professional.builder()
                .id(15L)
                .categoryId(1L)
                .firstName("Ana")
                .lastName("Gomez")
                .enabled(true)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();

        ProfessionalEntity entity = mapper.toEntity(professional);

        assertThat(entity).isNotNull();
        assertThat(entity)
                .extracting("id", "categoryId", "firstName", "lastName", "enabled", "createdAt", "updatedAt")
                .containsExactly(15L, 1L, "Ana", "Gomez", true, createdAt, updatedAt);
    }

    @Test
    void debeDevolverNull_cuandoElProfesionalATransformarEsNull() {
        ProfessionalEntity entity = mapper.toEntity(null);

        assertThat(entity).isNull();
    }

    @Test
    void debeConvertirADominio_cuandoLaEntidadEsValida() {
        Instant createdAt = Instant.parse("2026-07-01T12:00:00Z");
        Instant updatedAt = Instant.parse("2026-09-10T13:40:00Z");
        ProfessionalEntity entity = ProfessionalEntity.builder()
                .id(16L)
                .categoryId(2L)
                .firstName("Carlos")
                .lastName("Diaz")
                .enabled(false)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();

        Professional professional = mapper.toDomainModel(entity);

        assertThat(professional).isNotNull();
        assertThat(professional)
                .extracting("id", "categoryId", "firstName", "lastName", "enabled", "createdAt", "updatedAt")
                .containsExactly(16L, 2L, "Carlos", "Diaz", false, createdAt, updatedAt);
    }

    @Test
    void debeDevolverNull_cuandoLaEntidadATransformarEsNull() {
        Professional professional = mapper.toDomainModel(null);

        assertThat(professional).isNull();
    }
}
