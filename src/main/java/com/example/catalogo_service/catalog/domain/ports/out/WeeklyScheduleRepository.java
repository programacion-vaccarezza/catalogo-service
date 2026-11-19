package com.example.catalogo_service.catalog.domain.ports.out;

import com.example.catalogo_service.catalog.domain.model.WeeklySchedule;

import java.util.List;

// Port de salida del repositorio de horarios semanales
public interface WeeklyScheduleRepository {

    // Crea o actualiza un horario
    WeeklySchedule save(WeeklySchedule weeklySchedule);
    // Devuelve los horarios de un profesional
    List<WeeklySchedule> findByProfessionalId(Long professionalId);
}
