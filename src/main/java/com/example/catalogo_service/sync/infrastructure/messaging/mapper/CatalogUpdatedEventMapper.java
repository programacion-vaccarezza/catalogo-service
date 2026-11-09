package com.example.catalogo_service.sync.infrastructure.messaging.mapper;

import com.example.catalogo_service.sync.domain.model.CatalogUpdatedEvent;
import com.example.catalogo_service.sync.infrastructure.messaging.dto.CatalogUpdatedEventDto;
import org.springframework.stereotype.Component;

// Mapea el DTO del evento de Kafka al modelo de dominio
@Component
public class CatalogUpdatedEventMapper {

    // Convierte el DTO a dominio
    public CatalogUpdatedEvent toDomainModel(CatalogUpdatedEventDto dto) {
        if (dto == null) {
            return null;
        }
        return CatalogUpdatedEvent.builder()
                .eventId(dto.getEventId())
                .newVersion(dto.getNewVersion())
                .occurredAt(dto.getOccurredAt())
                .source(dto.getSource())
                .build();
    }
}
