package com.example.catalogo_service.catalog.application.service;

import com.example.catalogo_service.catalog.domain.model.Professional;
import com.example.catalogo_service.catalog.domain.model.ProfessionalSearchCriteria;
import com.example.catalogo_service.catalog.domain.model.WeeklySchedule;
import com.example.catalogo_service.catalog.domain.ports.in.GetProfessionalByIdUseCase;
import com.example.catalogo_service.catalog.domain.ports.in.GetWeeklySchedulesByProfessionalIdUseCase;
import com.example.catalogo_service.catalog.domain.ports.in.SearchProfessionalsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfessionalService {

    private final GetProfessionalByIdUseCase getProfessionalByIdUseCase;
    private final GetWeeklySchedulesByProfessionalIdUseCase getWeeklySchedulesByProfessionalIdUseCase;
    private final SearchProfessionalsUseCase searchProfessionalsUseCase;

    public Professional getProfessionalById(Long id) {
        return getProfessionalByIdUseCase.getProfessionalById(id);
    }

    public List<WeeklySchedule> getWeeklySchedulesByProfessionalId(Long professionalId) {
        return getWeeklySchedulesByProfessionalIdUseCase.getWeeklySchedulesByProfessionalId(professionalId);
    }

    public List<Professional> searchProfessionals(ProfessionalSearchCriteria criteria) {
        return searchProfessionalsUseCase.searchProfessionals(criteria);
    }
}
