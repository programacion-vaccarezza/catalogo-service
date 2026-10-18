package com.example.catalogo_service.catalog.infrastructure.web.controller;

import com.example.catalogo_service.catalog.application.exception.ProfessionalNotFoundException;
import com.example.catalogo_service.catalog.application.service.ProfessionalService;
import com.example.catalogo_service.catalog.domain.model.Professional;
import com.example.catalogo_service.catalog.domain.model.ProfessionalSearchCriteria;
import com.example.catalogo_service.catalog.infrastructure.web.dto.ProfessionalResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/professionals")
@RequiredArgsConstructor
public class ProfessionalController {

    private final ProfessionalService professionalService;

    @Operation(summary = "Buscar profesionales en la copia local del catálogo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profesionales encontrados (puede ser una lista vacía)")
    })
    @GetMapping
    public ResponseEntity<List<ProfessionalResponse>> search(
            @RequestParam(required = false) Long category,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Boolean enabled,
            @RequestParam(required = false) Boolean availability) {
        ProfessionalSearchCriteria criteria = ProfessionalSearchCriteria.builder()
                .categoryId(category)
                .name(name)
                .enabled(enabled)
                .availability(availability)
                .build();
        List<ProfessionalResponse> response = professionalService.searchProfessionals(criteria).stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener el detalle de un profesional por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profesional encontrado"),
            @ApiResponse(responseCode = "404", description = "No existe un profesional con ese ID")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProfessionalResponse> getById(@PathVariable Long id) {
        try {
            Professional professional = professionalService.getProfessionalById(id);
            return ResponseEntity.ok(toResponse(professional));
        } catch (ProfessionalNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    private ProfessionalResponse toResponse(Professional professional) {
        return ProfessionalResponse.builder()
                .id(professional.getId())
                .categoryId(professional.getCategoryId())
                .firstName(professional.getFirstName())
                .lastName(professional.getLastName())
                .enabled(professional.isEnabled())
                .build();
    }
}
