package com.example.catalogo_service.sync.domain.ports.out;

// Port de salida: registro de eventos de Kafka ya procesados, para garantizar idempotencia
public interface ProcessedEventRepository {

    // Indica si ese eventId ya fue procesado antes
    boolean existsByEventId(String eventId);
    // Marca un eventId como procesado
    void markAsProcessed(String eventId);
}
