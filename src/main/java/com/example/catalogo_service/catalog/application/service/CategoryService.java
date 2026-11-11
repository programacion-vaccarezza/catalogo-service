package com.example.catalogo_service.catalog.application.service;

import com.example.catalogo_service.catalog.domain.model.Category;
import com.example.catalogo_service.catalog.domain.ports.in.ListCategoriesUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

// Fachada de los casos de uso de categorías
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final ListCategoriesUseCase listCategoriesUseCase;

    // Lista las categorías del catálogo local
    public List<Category> listCategories() {
        return listCategoriesUseCase.listCategories();
    }
}
