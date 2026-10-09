package com.example.catalogo_service.catalog.domain.ports.out;

import com.example.catalogo_service.catalog.domain.model.Category;

public interface CategoryRepository {

    Category save(Category category);
}
