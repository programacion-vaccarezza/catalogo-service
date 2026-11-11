package com.example.catalogo_service.catalog.infrastructure.web.controller;

import com.example.catalogo_service.catalog.application.service.CategoryService;
import com.example.catalogo_service.catalog.domain.model.Category;
import com.example.catalogo_service.catalog.infrastructure.web.dto.CategoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// Endpoint público para listar las categorías del catálogo local
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    // Lista todas las categorías
    @Operation(summary = "Listar las categorías de la copia local del catálogo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categorías encontradas (puede ser una lista vacía)")
    })
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> list() {
        List<CategoryResponse> response = categoryService.listCategories().stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    // Convierte el dominio a la respuesta pública
    private CategoryResponse toResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .enabled(category.isEnabled())
                .build();
    }
}
