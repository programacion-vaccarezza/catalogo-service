package com.example.catalogo_service.sync.domain.ports.out;

import com.example.catalogo_service.sync.domain.model.CatalogSyncState;

import java.util.Optional;

// Port de salida del repositorio de la version local del catálogo
public interface CatalogSyncStateRepository {

    // Guarda la version local
    void save(CatalogSyncState catalogSyncState);
    // Devuelve la version local, si ya se sincronizó alguna vez
    Optional<CatalogSyncState> find();
}
