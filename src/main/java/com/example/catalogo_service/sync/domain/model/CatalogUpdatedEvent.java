package com.example.catalogo_service.sync.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatalogUpdatedEvent {

    private String eventId;
    private long newVersion;
    private Instant occurredAt;
    private String source;
}
