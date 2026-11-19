package com.example.catalogo_service.sync.infrastructure.messaging.dto;

import lombok.*;

import java.time.Instant;

// Evento CatalogUpdated tal como lo publica Kafka
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CatalogUpdatedEventDto {

    private String eventId;
    private String eventType;
    private long newVersion;
    private Instant occurredAt;
    private String source;
    private int schemaVersion;
}
