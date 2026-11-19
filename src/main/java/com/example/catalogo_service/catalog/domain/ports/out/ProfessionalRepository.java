package com.example.catalogo_service.catalog.domain.ports.out;

import com.example.catalogo_service.catalog.domain.model.Professional;

import java.util.List;
import java.util.Optional;

// Port de salida del repositorio de profesionales
public interface ProfessionalRepository {

    // Crea o actualiza un profesional
    Professional save(Professional professional);
    // Indica si hay al menos un profesional guardado, usado para saber si la base está vacía
    boolean existsAny();
    // Busca un profesional por id
    Optional<Professional> findById(Long id);
    // Devuelve todos los profesionales
    List<Professional> findAll();
}
