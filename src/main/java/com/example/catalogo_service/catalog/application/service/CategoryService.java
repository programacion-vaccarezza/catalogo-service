package com.example.catalogo_service.catalog.application.service;

import com.example.catalogo_service.catalog.domain.model.Category;
import com.example.catalogo_service.catalog.domain.ports.in.ListCategoriesUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final ListCategoriesUseCase listCategoriesUseCase;

    public List<Category> listCategories() {
        return listCategoriesUseCase.listCategories();
    }
}
