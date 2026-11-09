package com.example.catalogo_service.catalog.infrastructure.persistence.mapper;

import com.example.catalogo_service.catalog.domain.model.Professional;
import com.example.catalogo_service.catalog.infrastructure.persistence.entity.ProfessionalEntity;
import org.springframework.stereotype.Component;

// Mapea entre el modelo de dominio Professional y la entidad JPA ProfessionalEntity
@Component
public class ProfessionalMapper {

    // Convierte el dominio a entidad para persistir
    public ProfessionalEntity toEntity(Professional professional) {
        if (professional == null) {
            return null;
        }
        return ProfessionalEntity.builder()
                .id(professional.getId())
                .categoryId(professional.getCategoryId())
                .firstName(professional.getFirstName())
                .lastName(professional.getLastName())
                .enabled(professional.isEnabled())
                .createdAt(professional.getCreatedAt())
                .updatedAt(professional.getUpdatedAt())
                .build();
    }

    // Convierte la entidad persistida a dominio
    public Professional toDomainModel(ProfessionalEntity entity) {
        if (entity == null) {
            return null;
        }
        return Professional.builder()
                .id(entity.getId())
                .categoryId(entity.getCategoryId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .enabled(entity.isEnabled())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
