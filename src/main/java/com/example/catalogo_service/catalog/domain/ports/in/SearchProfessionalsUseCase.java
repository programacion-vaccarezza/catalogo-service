package com.example.catalogo_service.catalog.domain.ports.in;

import com.example.catalogo_service.catalog.domain.model.Professional;
import com.example.catalogo_service.catalog.domain.model.ProfessionalSearchCriteria;

import java.util.List;

// Caso de uso: buscar profesionales con filtros opcionales
public interface SearchProfessionalsUseCase {

    // Devuelve los profesionales que cumplen los filtros recibidos
    List<Professional> searchProfessionals(ProfessionalSearchCriteria criteria);
}
