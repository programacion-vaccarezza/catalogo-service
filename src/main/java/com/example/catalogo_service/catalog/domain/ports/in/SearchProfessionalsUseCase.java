package com.example.catalogo_service.catalog.domain.ports.in;

import com.example.catalogo_service.catalog.domain.model.Professional;
import com.example.catalogo_service.catalog.domain.model.ProfessionalSearchCriteria;

import java.util.List;

public interface SearchProfessionalsUseCase {

    List<Professional> searchProfessionals(ProfessionalSearchCriteria criteria);
}
