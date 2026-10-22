package com.example.catalogo_service.sync.application.usecases;

import com.example.catalogo_service.catalog.domain.model.Category;
import com.example.catalogo_service.catalog.domain.model.Professional;
import com.example.catalogo_service.catalog.domain.model.WeeklySchedule;
import com.example.catalogo_service.catalog.domain.ports.out.CategoryRepository;
import com.example.catalogo_service.catalog.domain.ports.out.ProfessionalRepository;
import com.example.catalogo_service.catalog.domain.ports.out.WeeklyScheduleRepository;
import com.example.catalogo_service.sync.domain.model.CatalogSnapshot;
import com.example.catalogo_service.sync.domain.ports.out.CatalogSyncStateRepository;
import com.example.catalogo_service.sync.domain.ports.out.CatedraRestPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FullSyncUseCaseImplTest {

    @Mock
    CatedraRestPort catedraRestPort;

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    ProfessionalRepository professionalRepository;

    @Mock
    WeeklyScheduleRepository weeklyScheduleRepository;

    @Mock
    CatalogSyncStateRepository catalogSyncStateRepository;

    @InjectMocks
    FullSyncUseCaseImpl fullSyncUseCase;

    @Test
    void debeOmitirLaSincronizacion_cuandoYaHayProfesionalesLocales() {
        when(professionalRepository.existsAny()).thenReturn(true);

        fullSyncUseCase.syncIfEmpty();

        verify(catedraRestPort, never()).fetchSnapshot();
        verify(categoryRepository, never()).save(any());
        verify(professionalRepository, never()).save(any());
        verify(weeklyScheduleRepository, never()).save(any());
        verify(catalogSyncStateRepository, never()).save(any());
    }

    @Test
    void debeSincronizarYGuardarLaVersion_cuandoElCatalogoLocalEstaVacio() {
        when(professionalRepository.existsAny()).thenReturn(false);

        Category category = Category.builder().id(1L).name("Clinica medica").enabled(true).build();
        Professional professional = Professional.builder().id(15L).categoryId(1L).firstName("Ana").lastName("Gomez").enabled(true).build();
        WeeklySchedule weeklySchedule = WeeklySchedule.builder().id(21L).professionalId(15L).enabled(true).build();

        CatalogSnapshot snapshot = CatalogSnapshot.builder()
                .snapshotVersion(7L)
                .generatedAt(Instant.now())
                .categories(List.of(category))
                .professionals(List.of(professional))
                .weeklySchedules(List.of(weeklySchedule))
                .build();

        when(catedraRestPort.fetchSnapshot()).thenReturn(snapshot);

        fullSyncUseCase.syncIfEmpty();

        verify(categoryRepository).save(category);
        verify(professionalRepository).save(professional);
        verify(weeklyScheduleRepository).save(weeklySchedule);
        verify(catalogSyncStateRepository).save(argThat(state -> state.getVersion() == 7L));
    }
}
