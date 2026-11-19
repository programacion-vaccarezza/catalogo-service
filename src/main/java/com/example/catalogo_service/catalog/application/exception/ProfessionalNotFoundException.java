package com.example.catalogo_service.catalog.application.exception;

// Se lanza cuando se busca un profesional por id y no existe
public class ProfessionalNotFoundException extends RuntimeException {
    public ProfessionalNotFoundException(Long id) {
        super("Profesional no encontrado: " + id);
    }
}
