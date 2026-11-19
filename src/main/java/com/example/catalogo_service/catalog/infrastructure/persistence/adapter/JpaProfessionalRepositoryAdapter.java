package com.example.catalogo_service.catalog.infrastructure.persistence.adapter;

import com.example.catalogo_service.catalog.domain.model.Professional;
import com.example.catalogo_service.catalog.domain.ports.out.ProfessionalRepository;
import com.example.catalogo_service.catalog.infrastructure.persistence.entity.ProfessionalEntity;
import com.example.catalogo_service.catalog.infrastructure.persistence.mapper.ProfessionalMapper;
import com.example.catalogo_service.catalog.infrastructure.persistence.repository.JpaProfessionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

// Adapter que implementa ProfessionalRepository usando JPA
@Component
@RequiredArgsConstructor
public class JpaProfessionalRepositoryAdapter implements ProfessionalRepository {

    private final JpaProfessionalRepository jpaProfessionalRepository;
    private final ProfessionalMapper professionalMapper;

    // Guarda el profesional y devuelve el modelo de dominio ya persistido
    @Override
    public Professional save(Professional professional) {
        ProfessionalEntity professionalEntity = professionalMapper.toEntity(professional);
        ProfessionalEntity savedEntity = jpaProfessionalRepository.save(professionalEntity);
        return professionalMapper.toDomainModel(savedEntity);
    }

    // Indica si hay al menos un profesional guardado
    @Override
    public boolean existsAny() {
        return jpaProfessionalRepository.count() > 0;
    }

    // Busca por id y mapea a dominio si existe
    @Override
    public Optional<Professional> findById(Long id) {
        return jpaProfessionalRepository.findById(id)
                .map(professionalMapper::toDomainModel);
    }

    // Devuelve todos los profesionales mapeados a dominio
    @Override
    public List<Professional> findAll() {
        return jpaProfessionalRepository.findAll().stream()
                .map(professionalMapper::toDomainModel)
                .toList();
    }
}
