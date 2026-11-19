package com.example.catalogo_service.sync.domain.ports.in;

import com.example.catalogo_service.sync.domain.model.CatalogUpdatedEvent;

// Caso de uso: procesar un evento CatalogUpdated recibido por Kafka
public interface HandleCatalogUpdatedUseCase {

    // Procesa el evento de forma idempotente
    void handle(CatalogUpdatedEvent event);
}
