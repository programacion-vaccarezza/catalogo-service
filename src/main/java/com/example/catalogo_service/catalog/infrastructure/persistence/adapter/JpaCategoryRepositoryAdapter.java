package com.example.catalogo_service.catalog.infrastructure.persistence.adapter;

import com.example.catalogo_service.catalog.domain.model.Category;
import com.example.catalogo_service.catalog.domain.ports.out.CategoryRepository;
import com.example.catalogo_service.catalog.infrastructure.persistence.entity.CategoryEntity;
import com.example.catalogo_service.catalog.infrastructure.persistence.mapper.CategoryMapper;
import com.example.catalogo_service.catalog.infrastructure.persistence.repository.JpaCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

// Adapter que implementa CategoryRepository usando JPA
@Component
@RequiredArgsConstructor
public class JpaCategoryRepositoryAdapter implements CategoryRepository {

    private final JpaCategoryRepository jpaCategoryRepository;
    private final CategoryMapper categoryMapper;

    // Guarda la categoría y devuelve el modelo de dominio ya persistido
    @Override
    public Category save(Category category) {
        CategoryEntity categoryEntity = categoryMapper.toEntity(category);
        CategoryEntity savedEntity = jpaCategoryRepository.save(categoryEntity);
        return categoryMapper.toDomainModel(savedEntity);
    }

    // Devuelve todas las categorías mapeadas a dominio
    @Override
    public List<Category> findAll() {
        return jpaCategoryRepository.findAll().stream()
                .map(categoryMapper::toDomainModel)
                .toList();
    }
}
