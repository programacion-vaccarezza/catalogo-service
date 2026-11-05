package com.example.catalogo_service.catalog.domain.ports.in;

import com.example.catalogo_service.catalog.domain.model.Category;

import java.util.List;

public interface ListCategoriesUseCase {

    List<Category> listCategories();
}
