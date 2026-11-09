package com.example.catalogo_service.catalog.application.usecases;

import com.example.catalogo_service.catalog.domain.model.Category;
import com.example.catalogo_service.catalog.domain.ports.in.ListCategoriesUseCase;
import com.example.catalogo_service.catalog.domain.ports.out.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

// Lista todas las categorías del catálogo local
@Component
@RequiredArgsConstructor
public class ListCategoriesUseCaseImpl implements ListCategoriesUseCase {

    private final CategoryRepository categoryRepository;

    // Devuelve todas las categorías guardadas
    @Override
    public List<Category> listCategories() {
        return categoryRepository.findAll();
    }
}
