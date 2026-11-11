package com.example.catalogo_service.catalog.infrastructure.persistence.mapper;

import com.example.catalogo_service.catalog.domain.model.Category;
import com.example.catalogo_service.catalog.infrastructure.persistence.entity.CategoryEntity;
import org.springframework.stereotype.Component;

// Mapea entre el modelo de dominio Category y la entidad JPA CategoryEntity
@Component
public class CategoryMapper {

    // Convierte el dominio a entidad para persistir
    public CategoryEntity toEntity(Category category) {
        if (category == null) {
            return null;
        }
        return CategoryEntity.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .enabled(category.isEnabled())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }

    // Convierte la entidad persistida a dominio
    public Category toDomainModel(CategoryEntity entity) {
        if (entity == null) {
            return null;
        }
        return Category.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .enabled(entity.isEnabled())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
