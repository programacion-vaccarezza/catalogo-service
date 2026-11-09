package com.example.catalogo_service.catalog.domain.ports.out;

import com.example.catalogo_service.catalog.domain.model.Category;

import java.util.List;

// Port de salida del repositorio de categorías
public interface CategoryRepository {

    // Crea o actualiza una categoría
    Category save(Category category);
    // Devuelve todas las categorías
    List<Category> findAll();
}
