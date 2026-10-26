package com.example.catalogo_service.sync.domain.ports.out;

import com.example.catalogo_service.sync.domain.model.CatalogSyncState;

import java.util.Optional;

public interface CatalogSyncStateRepository {

    void save(CatalogSyncState catalogSyncState);
    Optional<CatalogSyncState> find();
}
