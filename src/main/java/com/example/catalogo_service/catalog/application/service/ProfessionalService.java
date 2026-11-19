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

// Fachada de los casos de uso de profesionales
@Service
@RequiredArgsConstructor
public class ProfessionalService {

    private final GetProfessionalByIdUseCase getProfessionalByIdUseCase;
    private final GetWeeklySchedulesByProfessionalIdUseCase getWeeklySchedulesByProfessionalIdUseCase;
    private final SearchProfessionalsUseCase searchProfessionalsUseCase;

    // Busca un profesional por id
    public Professional getProfessionalById(Long id) {
        return getProfessionalByIdUseCase.getProfessionalById(id);
    }

    // Lista los horarios semanales de un profesional
    public List<WeeklySchedule> getWeeklySchedulesByProfessionalId(Long professionalId) {
        return getWeeklySchedulesByProfessionalIdUseCase.getWeeklySchedulesByProfessionalId(professionalId);
    }

    // Busca profesionales según los filtros recibidos
    public List<Professional> searchProfessionals(ProfessionalSearchCriteria criteria) {
        return searchProfessionalsUseCase.searchProfessionals(criteria);
    }
}
