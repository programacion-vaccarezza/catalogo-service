package com.example.catalogo_service.catalog.domain.ports.out;

import com.example.catalogo_service.catalog.domain.model.WeeklySchedule;

public interface WeeklyScheduleRepository {

    WeeklySchedule save(WeeklySchedule weeklySchedule);
}
