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

@Component
@RequiredArgsConstructor
public class SearchProfessionalsUseCaseImpl implements SearchProfessionalsUseCase {

    private final ProfessionalRepository professionalRepository;
    private final WeeklyScheduleRepository weeklyScheduleRepository;

    @Override
    public List<Professional> searchProfessionals(ProfessionalSearchCriteria criteria) {
        return professionalRepository.findAll().stream()
                .filter(professional -> matchesCategory(professional, criteria))
                .filter(professional -> matchesName(professional, criteria))
                .filter(professional -> matchesEnabled(professional, criteria))
                .filter(professional -> matchesAvailability(professional, criteria))
                .toList();
    }

    private boolean matchesCategory(Professional professional, ProfessionalSearchCriteria criteria) {
        return criteria.getCategoryId() == null || criteria.getCategoryId().equals(professional.getCategoryId());
    }

    private boolean matchesName(Professional professional, ProfessionalSearchCriteria criteria) {
        if (criteria.getName() == null) {
            return true;
        }
        String query = criteria.getName().toLowerCase();
        return professional.getFirstName().toLowerCase().contains(query)
                || professional.getLastName().toLowerCase().contains(query);
    }

    private boolean matchesEnabled(Professional professional, ProfessionalSearchCriteria criteria) {
        return criteria.getEnabled() == null || criteria.getEnabled().equals(professional.isEnabled());
    }

    private boolean matchesAvailability(Professional professional, ProfessionalSearchCriteria criteria) {
        if (criteria.getAvailability() == null) {
            return true;
        }
        boolean hasEnabledSchedule = weeklyScheduleRepository.findByProfessionalId(professional.getId()).stream()
                .anyMatch(WeeklySchedule::isEnabled);
        return criteria.getAvailability().equals(hasEnabledSchedule);
    }
}
