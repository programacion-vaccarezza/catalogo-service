package com.example.catalogo_service.sync.infrastructure.persistence.mapper;

import com.example.catalogo_service.sync.domain.model.CatalogSyncState;
import com.example.catalogo_service.sync.infrastructure.persistence.entity.CatalogSyncStateEntity;
import org.springframework.stereotype.Component;

@Component
public class CatalogSyncStateMapper {

    private static final Long SINGLETON_ID = 1L;

    public CatalogSyncStateEntity toEntity(CatalogSyncState catalogSyncState) {
        if (catalogSyncState == null) {
            return null;
        }
        return CatalogSyncStateEntity.builder()
                .id(SINGLETON_ID)
                .version(catalogSyncState.getVersion())
                .updatedAt(catalogSyncState.getUpdatedAt())
                .build();
    }

    public CatalogSyncState toDomainModel(CatalogSyncStateEntity entity) {
        if (entity == null) {
            return null;
        }
        return CatalogSyncState.builder()
                .version(entity.getVersion())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
