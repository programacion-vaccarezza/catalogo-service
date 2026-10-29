package com.example.catalogo_service.catalog.domain.ports.in;

import com.example.catalogo_service.catalog.domain.model.WeeklySchedule;

import java.util.List;

public interface GetWeeklySchedulesByProfessionalIdUseCase {

    List<WeeklySchedule> getWeeklySchedulesByProfessionalId(Long professionalId);
}
