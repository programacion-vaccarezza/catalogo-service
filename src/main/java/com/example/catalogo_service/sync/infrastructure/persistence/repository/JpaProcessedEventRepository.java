package com.example.catalogo_service.sync.infrastructure.persistence.repository;

import com.example.catalogo_service.sync.infrastructure.persistence.entity.ProcessedEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repositorio de la entidad ProcessedEventEntity
@Repository
public interface JpaProcessedEventRepository extends JpaRepository<ProcessedEventEntity, String> {
}
