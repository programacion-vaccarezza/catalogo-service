package com.example.catalogo_service.catalog.domain.ports.out;

import com.example.catalogo_service.catalog.domain.model.Category;

import java.util.List;

public interface CategoryRepository {

    Category save(Category category);
    List<Category> findAll();
}
