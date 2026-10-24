package com.example.catalogo_service.catalog.application.usecases;

import com.example.catalogo_service.catalog.application.exception.ProfessionalNotFoundException;
import com.example.catalogo_service.catalog.domain.model.WeeklySchedule;
import com.example.catalogo_service.catalog.domain.ports.in.GetWeeklySchedulesByProfessionalIdUseCase;
import com.example.catalogo_service.catalog.domain.ports.out.ProfessionalRepository;
import com.example.catalogo_service.catalog.domain.ports.out.WeeklyScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetWeeklySchedulesByProfessionalIdUseCaseImpl implements GetWeeklySchedulesByProfessionalIdUseCase {

    private final ProfessionalRepository professionalRepository;
    private final WeeklyScheduleRepository weeklyScheduleRepository;

    @Override
    public List<WeeklySchedule> getWeeklySchedulesByProfessionalId(Long professionalId) {
        if (professionalRepository.findById(professionalId).isEmpty()) {
            throw new ProfessionalNotFoundException(professionalId);
        }
        return weeklyScheduleRepository.findByProfessionalId(professionalId);
    }
}
