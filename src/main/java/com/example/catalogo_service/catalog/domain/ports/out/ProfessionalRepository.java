package com.example.catalogo_service.catalog.domain.ports.out;

import com.example.catalogo_service.catalog.domain.model.Professional;

public interface ProfessionalRepository {

    Professional save(Professional professional);
}
