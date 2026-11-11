package com.example.catalogo_service.sync.infrastructure.persistence.mapper;

import com.example.catalogo_service.sync.domain.model.CatalogSyncState;
import com.example.catalogo_service.sync.infrastructure.persistence.entity.CatalogSyncStateEntity;
import org.springframework.stereotype.Component;

// Mapea entre el modelo de dominio CatalogSyncState y la entidad JPA CatalogSyncStateEntity
@Component
public class CatalogSyncStateMapper {

    private static final Long SINGLETON_ID = 1L;

    // Convierte el dominio a entidad, fijando siempre el mismo id
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

    // Convierte la entidad persistida a dominio
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
