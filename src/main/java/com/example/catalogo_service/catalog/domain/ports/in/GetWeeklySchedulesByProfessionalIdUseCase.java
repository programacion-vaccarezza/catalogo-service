package com.example.catalogo_service.catalog.domain.ports.in;

import com.example.catalogo_service.catalog.domain.model.WeeklySchedule;

import java.util.List;

// Caso de uso: listar los horarios semanales de un profesional
public interface GetWeeklySchedulesByProfessionalIdUseCase {

    // Devuelve los horarios del profesional o lanza excepción si no existe
    List<WeeklySchedule> getWeeklySchedulesByProfessionalId(Long professionalId);
}
