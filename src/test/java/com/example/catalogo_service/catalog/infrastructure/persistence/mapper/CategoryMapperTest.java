package com.example.catalogo_service.catalog.infrastructure.persistence.mapper;

import com.example.catalogo_service.catalog.domain.model.Category;
import com.example.catalogo_service.catalog.infrastructure.persistence.entity.CategoryEntity;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryMapperTest {

    private final CategoryMapper mapper = new CategoryMapper();

    @Test
    void debeConvertirAEntity_cuandoLaCategoriaEsValida() {
        Instant createdAt = Instant.parse("2026-07-01T12:00:00Z");
        Instant updatedAt = Instant.parse("2026-09-10T13:40:00Z");
        Category category = Category.builder()
                .id(1L)
                .name("Clinica medica")
                .description("Atencion clinica general")
                .enabled(true)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();

        CategoryEntity entity = mapper.toEntity(category);

        assertThat(entity).isNotNull();
        assertThat(entity)
                .extracting("id", "name", "description", "enabled", "createdAt", "updatedAt")
                .containsExactly(1L, "Clinica medica", "Atencion clinica general", true, createdAt, updatedAt);
    }

    @Test
    void debeDevolverNull_cuandoLaCategoriaATransformarEsNull() {
        CategoryEntity entity = mapper.toEntity(null);

        assertThat(entity).isNull();
    }

    @Test
    void debeConvertirADominio_cuandoLaEntidadEsValida() {
        Instant createdAt = Instant.parse("2026-07-01T12:00:00Z");
        Instant updatedAt = Instant.parse("2026-09-10T13:40:00Z");
        CategoryEntity entity = CategoryEntity.builder()
                .id(2L)
                .name("Kinesiologia")
                .description(null)
                .enabled(false)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();

        Category category = mapper.toDomainModel(entity);

        assertThat(category).isNotNull();
        assertThat(category)
                .extracting("id", "name", "description", "enabled", "createdAt", "updatedAt")
                .containsExactly(2L, "Kinesiologia", null, false, createdAt, updatedAt);
    }

    @Test
    void debeDevolverNull_cuandoLaEntidadATransformarEsNull() {
        Category category = mapper.toDomainModel(null);

        assertThat(category).isNull();
    }
}
