package com.example.catalogo_service.catalog.application.usecases;

import com.example.catalogo_service.catalog.domain.model.Category;
import com.example.catalogo_service.catalog.domain.ports.out.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListCategoriesUseCaseImplTest {

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    ListCategoriesUseCaseImpl listCategoriesUseCase;

    @Test
    void debeDevolverLasCategorias_cuandoHayCategoriasGuardadas() {
        Category category = Category.builder()
                .id(1L)
                .name("Clinica medica")
                .enabled(true)
                .build();
        when(categoryRepository.findAll()).thenReturn(List.of(category));

        List<Category> resultado = listCategoriesUseCase.listCategories();

        assertThat(resultado).containsExactly(category);
    }

    @Test
    void debeDevolverListaVacia_cuandoNoHayCategoriasGuardadas() {
        when(categoryRepository.findAll()).thenReturn(Collections.emptyList());

        List<Category> resultado = listCategoriesUseCase.listCategories();

        assertThat(resultado).isEmpty();
    }
}