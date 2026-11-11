package com.example.catalogo_service.catalog.infrastructure.persistence.repository;

import com.example.catalogo_service.catalog.infrastructure.persistence.entity.ProfessionalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repositorio de la entidad ProfessionalEntity
@Repository
public interface JpaProfessionalRepository extends JpaRepository<ProfessionalEntity, Long> {
}
