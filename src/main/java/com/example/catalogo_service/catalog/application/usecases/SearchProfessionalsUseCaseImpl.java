package com.example.catalogo_service.catalog.application.usecases;

import com.example.catalogo_service.catalog.domain.model.Professional;
import com.example.catalogo_service.catalog.domain.model.ProfessionalSearchCriteria;
import com.example.catalogo_service.catalog.domain.model.WeeklySchedule;
import com.example.catalogo_service.catalog.domain.ports.in.SearchProfessionalsUseCase;
import com.example.catalogo_service.catalog.domain.ports.out.ProfessionalRepository;
import com.example.catalogo_service.catalog.domain.ports.out.WeeklyScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

// Busca profesionales en la copia local, combinando los filtros recibidos
@Component
@RequiredArgsConstructor
public class SearchProfessionalsUseCaseImpl implements SearchProfessionalsUseCase {

    private final ProfessionalRepository professionalRepository;
    private final WeeklyScheduleRepository weeklyScheduleRepository;

    // Aplica los 4 filtros en cadena sobre todos los profesionales
    @Override
    public List<Professional> searchProfessionals(ProfessionalSearchCriteria criteria) {
        return professionalRepository.findAll().stream()
                .filter(professional -> matchesCategory(professional, criteria))
                .filter(professional -> matchesName(professional, criteria))
                .filter(professional -> matchesEnabled(professional, criteria))
                .filter(professional -> matchesAvailability(professional, criteria))
                .toList();
    }

    // Coincide si no se filtró por categoría o si es la misma
    private boolean matchesCategory(Professional professional, ProfessionalSearchCriteria criteria) {
        return criteria.getCategoryId() == null || criteria.getCategoryId().equals(professional.getCategoryId());
    }

    // Coincide si no se filtró por nombre o si aparece en el nombre o el apellido
    private boolean matchesName(Professional professional, ProfessionalSearchCriteria criteria) {
        if (criteria.getName() == null) {
            return true;
        }
        String query = criteria.getName().toLowerCase();
        return professional.getFirstName().toLowerCase().contains(query)
                || professional.getLastName().toLowerCase().contains(query);
    }

    // Coincide si no se filtró por habilitado o si coincide el estado
    private boolean matchesEnabled(Professional professional, ProfessionalSearchCriteria criteria) {
        return criteria.getEnabled() == null || criteria.getEnabled().equals(professional.isEnabled());
    }

    // "Disponibilidad" acá significa que tiene algún horario semanal habilitado
    private boolean matchesAvailability(Professional professional, ProfessionalSearchCriteria criteria) {
        if (criteria.getAvailability() == null) {
            return true;
        }
        boolean hasEnabledSchedule = weeklyScheduleRepository.findByProfessionalId(professional.getId()).stream()
                .anyMatch(WeeklySchedule::isEnabled);
        return criteria.getAvailability().equals(hasEnabledSchedule);
    }
}
