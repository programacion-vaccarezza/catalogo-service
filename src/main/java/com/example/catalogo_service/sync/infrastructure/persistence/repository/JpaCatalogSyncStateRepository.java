package com.example.catalogo_service.sync.infrastructure.persistence.repository;

import com.example.catalogo_service.sync.infrastructure.persistence.entity.CatalogSyncStateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCatalogSyncStateRepository extends JpaRepository<CatalogSyncStateEntity, Long> {
}
