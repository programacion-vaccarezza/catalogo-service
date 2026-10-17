package com.example.catalogo_service.catalog.domain.ports.in;

import com.example.catalogo_service.catalog.domain.model.Professional;

public interface GetProfessionalByIdUseCase {

    Professional getProfessionalById(Long id);
}
