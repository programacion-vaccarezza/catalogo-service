package com.example.catalogo_service.catalog.infrastructure.persistence.adapter;

import com.example.catalogo_service.catalog.domain.model.Category;
import com.example.catalogo_service.catalog.domain.ports.out.CategoryRepository;
import com.example.catalogo_service.catalog.infrastructure.persistence.entity.CategoryEntity;
import com.example.catalogo_service.catalog.infrastructure.persistence.mapper.CategoryMapper;
import com.example.catalogo_service.catalog.infrastructure.persistence.repository.JpaCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaCategoryRepositoryAdapter implements CategoryRepository {

    private final JpaCategoryRepository jpaCategoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public Category save(Category category) {
        CategoryEntity categoryEntity = categoryMapper.toEntity(category);
        CategoryEntity savedEntity = jpaCategoryRepository.save(categoryEntity);
        return categoryMapper.toDomainModel(savedEntity);
    }
}
