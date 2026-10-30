package com.example.catalogo_service.sync.domain.ports.out;

public interface ProcessedEventRepository {

    boolean existsByEventId(String eventId);
    void markAsProcessed(String eventId);
}
