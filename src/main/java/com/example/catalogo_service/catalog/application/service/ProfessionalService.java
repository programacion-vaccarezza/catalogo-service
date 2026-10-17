package com.example.catalogo_service.catalog.application.service;

import com.example.catalogo_service.catalog.domain.model.Professional;
import com.example.catalogo_service.catalog.domain.model.WeeklySchedule;
import com.example.catalogo_service.catalog.domain.ports.in.GetProfessionalByIdUseCase;
import com.example.catalogo_service.catalog.domain.ports.in.GetWeeklySchedulesByProfessionalIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfessionalService {

    private final GetProfessionalByIdUseCase getProfessionalByIdUseCase;
    private final GetWeeklySchedulesByProfessionalIdUseCase getWeeklySchedulesByProfessionalIdUseCase;

    public Professional getProfessionalById(Long id) {
        return getProfessionalByIdUseCase.getProfessionalById(id);
    }

    public List<WeeklySchedule> getWeeklySchedulesByProfessionalId(Long professionalId) {
        return getWeeklySchedulesByProfessionalIdUseCase.getWeeklySchedulesByProfessionalId(professionalId);
    }
}
