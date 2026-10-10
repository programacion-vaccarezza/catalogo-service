package com.example.catalogo_service.sync.infrastructure.persistence.adapter;

import com.example.catalogo_service.sync.domain.model.CatalogSyncState;
import com.example.catalogo_service.sync.domain.ports.out.CatalogSyncStateRepository;
import com.example.catalogo_service.sync.infrastructure.persistence.entity.CatalogSyncStateEntity;
import com.example.catalogo_service.sync.infrastructure.persistence.mapper.CatalogSyncStateMapper;
import com.example.catalogo_service.sync.infrastructure.persistence.repository.JpaCatalogSyncStateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaCatalogSyncStateRepositoryAdapter implements CatalogSyncStateRepository {

    private final JpaCatalogSyncStateRepository jpaCatalogSyncStateRepository;
    private final CatalogSyncStateMapper catalogSyncStateMapper;

    @Override
    public void save(CatalogSyncState catalogSyncState) {
        CatalogSyncStateEntity entity = catalogSyncStateMapper.toEntity(catalogSyncState);
        jpaCatalogSyncStateRepository.save(entity);
    }
}
