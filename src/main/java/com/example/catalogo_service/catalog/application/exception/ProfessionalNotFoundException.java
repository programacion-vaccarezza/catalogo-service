package com.example.catalogo_service.catalog.application.exception;

public class ProfessionalNotFoundException extends RuntimeException {
    public ProfessionalNotFoundException(Long id) {
        super("Profesional no encontrado: " + id);
    }
}
