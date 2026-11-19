package com.example.catalogo_service.catalog.infrastructure.persistence.repository;

import com.example.catalogo_service.catalog.infrastructure.persistence.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repositorio de la entidad CategoryEntity
@Repository
public interface JpaCategoryRepository extends JpaRepository<CategoryEntity, Long> {
}
