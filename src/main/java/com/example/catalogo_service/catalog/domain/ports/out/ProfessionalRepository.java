package com.example.catalogo_service.catalog.domain.ports.out;

import com.example.catalogo_service.catalog.domain.model.Professional;

import java.util.List;
import java.util.Optional;

public interface ProfessionalRepository {

    Professional save(Professional professional);
    boolean existsAny();
    Optional<Professional> findById(Long id);
    List<Professional> findAll();
}
