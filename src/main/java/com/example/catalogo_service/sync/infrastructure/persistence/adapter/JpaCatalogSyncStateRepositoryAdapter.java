package com.example.catalogo_service.sync.infrastructure.persistence.adapter;

import com.example.catalogo_service.sync.domain.model.CatalogSyncState;
import com.example.catalogo_service.sync.domain.ports.out.CatalogSyncStateRepository;
import com.example.catalogo_service.sync.infrastructure.persistence.entity.CatalogSyncStateEntity;
import com.example.catalogo_service.sync.infrastructure.persistence.mapper.CatalogSyncStateMapper;
import com.example.catalogo_service.sync.infrastructure.persistence.repository.JpaCatalogSyncStateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

// Adapter que implementa CatalogSyncStateRepository
@Component
@RequiredArgsConstructor
public class JpaCatalogSyncStateRepositoryAdapter implements CatalogSyncStateRepository {

    // Siempre hay una sola fila: la version local vigente
    private static final Long SINGLETON_ID = 1L;

    private final JpaCatalogSyncStateRepository jpaCatalogSyncStateRepository;
    private final CatalogSyncStateMapper catalogSyncStateMapper;

    // Guarda la version local
    @Override
    public void save(CatalogSyncState catalogSyncState) {
        CatalogSyncStateEntity entity = catalogSyncStateMapper.toEntity(catalogSyncState);
        jpaCatalogSyncStateRepository.save(entity);
    }

    // Busca la fila única y la mapea a dominio si existe
    @Override
    public Optional<CatalogSyncState> find() {
        return jpaCatalogSyncStateRepository.findById(SINGLETON_ID)
                .map(catalogSyncStateMapper::toDomainModel);
    }
}
