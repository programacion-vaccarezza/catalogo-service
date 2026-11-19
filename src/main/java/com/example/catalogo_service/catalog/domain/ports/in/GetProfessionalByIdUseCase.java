package com.example.catalogo_service.catalog.domain.ports.in;

import com.example.catalogo_service.catalog.domain.model.Professional;

// Caso de uso: obtener el detalle de un profesional por id
public interface GetProfessionalByIdUseCase {

    // Devuelve el profesional o lanza excepción si no existe
    Professional getProfessionalById(Long id);
}
