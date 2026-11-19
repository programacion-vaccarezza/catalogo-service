package com.example.catalogo_service.catalog.domain.ports.in;

import com.example.catalogo_service.catalog.domain.model.Category;

import java.util.List;

// Caso de uso: listar las categorías del catálogo local
public interface ListCategoriesUseCase {

    // Devuelve todas las categorías guardadas
    List<Category> listCategories();
}
