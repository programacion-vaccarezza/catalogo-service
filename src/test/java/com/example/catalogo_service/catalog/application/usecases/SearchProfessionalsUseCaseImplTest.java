package com.example.catalogo_service.catalog.application.usecases;

import com.example.catalogo_service.catalog.domain.model.Professional;
import com.example.catalogo_service.catalog.domain.model.ProfessionalSearchCriteria;
import com.example.catalogo_service.catalog.domain.model.WeeklySchedule;
import com.example.catalogo_service.catalog.domain.ports.out.ProfessionalRepository;
import com.example.catalogo_service.catalog.domain.ports.out.WeeklyScheduleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchProfessionalsUseCaseImplTest {

    @Mock
    ProfessionalRepository professionalRepository;

    @Mock
    WeeklyScheduleRepository weeklyScheduleRepository;

    @InjectMocks
    SearchProfessionalsUseCaseImpl searchProfessionalsUseCase;

    private final Professional anaTorres = Professional.builder()
            .id(1L).categoryId(101L).firstName("Ana").lastName("Torres").enabled(true).build();

    private final Professional carlosDiaz = Professional.builder()
            .id(2L).categoryId(102L).firstName("Carlos").lastName("Diaz").enabled(false).build();

    @Test
    void debeDevolverTodos_cuandoNoHayFiltros() {
        when(professionalRepository.findAll()).thenReturn(List.of(anaTorres, carlosDiaz));

        List<Professional> resultado = searchProfessionalsUseCase.searchProfessionals(ProfessionalSearchCriteria.builder().build());

        assertThat(resultado).containsExactlyInAnyOrder(anaTorres, carlosDiaz);
    }

    @Test
    void debeFiltrarPorCategoria_cuandoSeEspecificaCategoryId() {
        when(professionalRepository.findAll()).thenReturn(List.of(anaTorres, carlosDiaz));

        List<Professional> resultado = searchProfessionalsUseCase.searchProfessionals(
                ProfessionalSearchCriteria.builder().categoryId(101L).build());

        assertThat(resultado).containsExactly(anaTorres);
    }

    @Test
    void debeFiltrarPorNombre_sinImportarMayusculasOMinusculas() {
        when(professionalRepository.findAll()).thenReturn(List.of(anaTorres, carlosDiaz));

        List<Professional> resultado = searchProfessionalsUseCase.searchProfessionals(
                ProfessionalSearchCriteria.builder().name("TORRES").build());

        assertThat(resultado).containsExactly(anaTorres);
    }

    @Test
    void debeFiltrarPorEstadoHabilitado() {
        when(professionalRepository.findAll()).thenReturn(List.of(anaTorres, carlosDiaz));

        List<Professional> resultado = searchProfessionalsUseCase.searchProfessionals(
                ProfessionalSearchCriteria.builder().enabled(false).build());

        assertThat(resultado).containsExactly(carlosDiaz);
    }

    @Test
    void debeIncluirSoloLosQueTienenHorarioHabilitado_cuandoAvailabilityEsTrue() {
        when(professionalRepository.findAll()).thenReturn(List.of(anaTorres, carlosDiaz));
        when(weeklyScheduleRepository.findByProfessionalId(1L))
                .thenReturn(List.of(WeeklySchedule.builder().id(301L).professionalId(1L).enabled(true).build()));
        when(weeklyScheduleRepository.findByProfessionalId(2L))
                .thenReturn(List.of());

        List<Professional> resultado = searchProfessionalsUseCase.searchProfessionals(
                ProfessionalSearchCriteria.builder().availability(true).build());

        assertThat(resultado).containsExactly(anaTorres);
    }

    @Test
    void debeIncluirSoloLosQueNoTienenHorarioHabilitado_cuandoAvailabilityEsFalse() {
        when(professionalRepository.findAll()).thenReturn(List.of(anaTorres, carlosDiaz));
        when(weeklyScheduleRepository.findByProfessionalId(1L))
                .thenReturn(List.of(WeeklySchedule.builder().id(301L).professionalId(1L).enabled(true).build()));
        when(weeklyScheduleRepository.findByProfessionalId(2L))
                .thenReturn(List.of());

        List<Professional> resultado = searchProfessionalsUseCase.searchProfessionals(
                ProfessionalSearchCriteria.builder().availability(false).build());

        assertThat(resultado).containsExactly(carlosDiaz);
    }
}
