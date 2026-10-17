package com.example.catalogo_service.catalog.domain.ports.out;

import com.example.catalogo_service.catalog.domain.model.WeeklySchedule;

import java.util.List;

public interface WeeklyScheduleRepository {

    WeeklySchedule save(WeeklySchedule weeklySchedule);
    List<WeeklySchedule> findByProfessionalId(Long professionalId);
}
