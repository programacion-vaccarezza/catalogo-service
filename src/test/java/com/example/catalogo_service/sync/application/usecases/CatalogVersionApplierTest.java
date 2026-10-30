package com.example.catalogo_service.sync.application.usecases;

import com.example.catalogo_service.catalog.domain.model.Category;
import com.example.catalogo_service.catalog.domain.model.Professional;
import com.example.catalogo_service.catalog.domain.model.WeeklySchedule;
import com.example.catalogo_service.catalog.domain.ports.out.CategoryRepository;
import com.example.catalogo_service.catalog.domain.ports.out.ProfessionalRepository;
import com.example.catalogo_service.catalog.domain.ports.out.WeeklyScheduleRepository;
import com.example.catalogo_service.sync.domain.model.CatalogChangeSet;
import com.example.catalogo_service.sync.domain.ports.out.CatalogSyncStateRepository;
import com.example.catalogo_service.sync.domain.ports.out.CatedraRedisPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CatalogVersionApplierTest {

    @Mock
    CatedraRedisPort catedraRedisPort;

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    ProfessionalRepository professionalRepository;

    @Mock
    WeeklyScheduleRepository weeklyScheduleRepository;

    @Mock
    CatalogSyncStateRepository catalogSyncStateRepository;

    @InjectMocks
    CatalogVersionApplier catalogVersionApplier;

    @Test
    void debeGuardarLasEntidadesEncontradasYLaNuevaVersion_cuandoAplicaUnaVersion() {
        CatalogChangeSet changeSet = CatalogChangeSet.builder()
                .version(5L)
                .categoryIds(List.of(1L))
                .professionalIds(List.of(15L))
                .weeklyScheduleIds(List.of(21L))
                .build();

        Category category = Category.builder().id(1L).name("Clinica medica").enabled(true).build();
        Professional professional = Professional.builder().id(15L).categoryId(1L).firstName("Ana").lastName("Gomez").enabled(true).build();
        WeeklySchedule weeklySchedule = WeeklySchedule.builder().id(21L).professionalId(15L).enabled(true).build();

        when(catedraRedisPort.fetchChanges(5L)).thenReturn(changeSet);
        when(catedraRedisPort.fetchCategory(1L)).thenReturn(Optional.of(category));
        when(catedraRedisPort.fetchProfessional(15L)).thenReturn(Optional.of(professional));
        when(catedraRedisPort.fetchWeeklySchedule(21L)).thenReturn(Optional.of(weeklySchedule));

        catalogVersionApplier.applyVersion(5L);

        verify(categoryRepository).save(category);
        verify(professionalRepository).save(professional);
        verify(weeklyScheduleRepository).save(weeklySchedule);
        verify(catalogSyncStateRepository).save(argThat(state -> state.getVersion() == 5L));
    }

    @Test
    void noDebeGuardarNada_cuandoUnIdDeLaVersionNoSeEncuentraEnRedis() {
        CatalogChangeSet changeSet = CatalogChangeSet.builder()
                .version(6L)
                .categoryIds(List.of(99L))
                .professionalIds(List.of())
                .weeklyScheduleIds(List.of())
                .build();

        when(catedraRedisPort.fetchChanges(6L)).thenReturn(changeSet);
        when(catedraRedisPort.fetchCategory(99L)).thenReturn(Optional.empty());

        catalogVersionApplier.applyVersion(6L);

        verify(categoryRepository, never()).save(any());
        verify(catalogSyncStateRepository).save(argThat(state -> state.getVersion() == 6L));
    }
}
