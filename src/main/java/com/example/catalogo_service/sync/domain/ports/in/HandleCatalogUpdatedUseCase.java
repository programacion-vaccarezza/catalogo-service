package com.example.catalogo_service.sync.domain.ports.in;

import com.example.catalogo_service.sync.domain.model.CatalogUpdatedEvent;

public interface HandleCatalogUpdatedUseCase {

    void handle(CatalogUpdatedEvent event);
}
