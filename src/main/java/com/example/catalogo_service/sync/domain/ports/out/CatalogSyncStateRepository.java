package com.example.catalogo_service.sync.domain.ports.out;

import com.example.catalogo_service.sync.domain.model.CatalogSyncState;

public interface CatalogSyncStateRepository {

    void save(CatalogSyncState catalogSyncState);
}
