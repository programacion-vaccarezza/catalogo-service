package com.example.catalogo_service.catalog.infrastructure.persistence.adapter;

import com.example.catalogo_service.catalog.domain.model.Professional;
import com.example.catalogo_service.catalog.domain.ports.out.ProfessionalRepository;
import com.example.catalogo_service.catalog.infrastructure.persistence.entity.ProfessionalEntity;
import com.example.catalogo_service.catalog.infrastructure.persistence.mapper.ProfessionalMapper;
import com.example.catalogo_service.catalog.infrastructure.persistence.repository.JpaProfessionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaProfessionalRepositoryAdapter implements ProfessionalRepository {

    private final JpaProfessionalRepository jpaProfessionalRepository;
    private final ProfessionalMapper professionalMapper;

    @Override
    public Professional save(Professional professional) {
        ProfessionalEntity professionalEntity = professionalMapper.toEntity(professional);
        ProfessionalEntity savedEntity = jpaProfessionalRepository.save(professionalEntity);
        return professionalMapper.toDomainModel(savedEntity);
    }
}
