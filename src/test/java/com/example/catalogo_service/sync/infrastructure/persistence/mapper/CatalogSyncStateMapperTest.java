package com.example.catalogo_service.sync.infrastructure.persistence.mapper;

import com.example.catalogo_service.sync.domain.model.CatalogSyncState;
import com.example.catalogo_service.sync.infrastructure.persistence.entity.CatalogSyncStateEntity;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class CatalogSyncStateMapperTest {

    private final CatalogSyncStateMapper mapper = new CatalogSyncStateMapper();

    @Test
    void debeConvertirAEntity_cuandoElEstadoEsValido() {
        Instant updatedAt = Instant.parse("2026-09-10T13:40:00Z");
        CatalogSyncState state = CatalogSyncState.builder()
                .version(7L)
                .updatedAt(updatedAt)
                .build();

        CatalogSyncStateEntity entity = mapper.toEntity(state);

        assertThat(entity).isNotNull();
        assertThat(entity)
                .extracting("id", "version", "updatedAt")
                .containsExactly(1L, 7L, updatedAt);
    }

    @Test
    void debeDevolverNull_cuandoElEstadoATransformarEsNull() {
        CatalogSyncStateEntity entity = mapper.toEntity(null);

        assertThat(entity).isNull();
    }

    @Test
    void debeConvertirADominio_cuandoLaEntidadEsValida() {
        Instant updatedAt = Instant.parse("2026-09-10T13:40:00Z");
        CatalogSyncStateEntity entity = CatalogSyncStateEntity.builder()
                .id(1L)
                .version(8L)
                .updatedAt(updatedAt)
                .build();

        CatalogSyncState state = mapper.toDomainModel(entity);

        assertThat(state).isNotNull();
        assertThat(state)
                .extracting("version", "updatedAt")
                .containsExactly(8L, updatedAt);
    }

    @Test
    void debeDevolverNull_cuandoLaEntidadATransformarEsNull() {
        CatalogSyncState state = mapper.toDomainModel(null);

        assertThat(state).isNull();
    }
}
