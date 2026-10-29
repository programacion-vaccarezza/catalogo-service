package com.example.catalogo_service.catalog.application.usecases;

import com.example.catalogo_service.catalog.application.exception.ProfessionalNotFoundException;
import com.example.catalogo_service.catalog.domain.model.Professional;
import com.example.catalogo_service.catalog.domain.model.WeeklySchedule;
import com.example.catalogo_service.catalog.domain.ports.out.ProfessionalRepository;
import com.example.catalogo_service.catalog.domain.ports.out.WeeklyScheduleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetWeeklySchedulesByProfessionalIdUseCaseImplTest {

    @Mock
    ProfessionalRepository professionalRepository;

    @Mock
    WeeklyScheduleRepository weeklyScheduleRepository;

    @InjectMocks
    GetWeeklySchedulesByProfessionalIdUseCaseImpl getWeeklySchedulesByProfessionalIdUseCase;

    @Test
    void debeLanzarExcepcion_cuandoElProfesionalNoExiste() {
        when(professionalRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> getWeeklySchedulesByProfessionalIdUseCase.getWeeklySchedulesByProfessionalId(999L))
                .isInstanceOf(ProfessionalNotFoundException.class)
                .hasMessage("Profesional no encontrado: 999");

        verify(weeklyScheduleRepository, never()).findByProfessionalId(any());
    }

    @Test
    void debeDevolverLosHorarios_cuandoElProfesionalExiste() {
        Professional professional = Professional.builder().id(201L).build();
        when(professionalRepository.findById(201L)).thenReturn(Optional.of(professional));

        WeeklySchedule weeklySchedule = WeeklySchedule.builder()
                .id(301L)
                .professionalId(201L)
                .dayOfWeek(DayOfWeek.MONDAY)
                .enabled(true)
                .build();
        when(weeklyScheduleRepository.findByProfessionalId(201L)).thenReturn(List.of(weeklySchedule));

        List<WeeklySchedule> resultado = getWeeklySchedulesByProfessionalIdUseCase.getWeeklySchedulesByProfessionalId(201L);

        assertThat(resultado).containsExactly(weeklySchedule);
    }
}
