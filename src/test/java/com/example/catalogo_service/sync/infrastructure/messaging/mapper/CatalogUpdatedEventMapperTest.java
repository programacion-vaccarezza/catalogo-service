package com.example.catalogo_service.sync.infrastructure.messaging.mapper;

import com.example.catalogo_service.sync.domain.model.CatalogUpdatedEvent;
import com.example.catalogo_service.sync.infrastructure.messaging.dto.CatalogUpdatedEventDto;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class CatalogUpdatedEventMapperTest {

    private final CatalogUpdatedEventMapper mapper = new CatalogUpdatedEventMapper();

    @Test
    void debeMapearTodosLosCamposRelevantes_cuandoElDtoEsValido() {
        Instant occurredAt = Instant.parse("2026-10-20T10:00:00Z");
        CatalogUpdatedEventDto dto = new CatalogUpdatedEventDto(
                "evt-123", "CatalogUpdated", 9L, occurredAt, "catedra", 1);

        CatalogUpdatedEvent domainModel = mapper.toDomainModel(dto);

        assertThat(domainModel).isNotNull();
        assertThat(domainModel.getEventId()).isEqualTo("evt-123");
        assertThat(domainModel.getNewVersion()).isEqualTo(9L);
        assertThat(domainModel.getOccurredAt()).isEqualTo(occurredAt);
        assertThat(domainModel.getSource()).isEqualTo("catedra");
    }

    @Test
    void debeRetornarNull_cuandoElDtoEsNull() {
        CatalogUpdatedEvent domainModel = mapper.toDomainModel(null);

        assertThat(domainModel).isNull();
    }
}
