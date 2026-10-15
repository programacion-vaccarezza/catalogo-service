package com.example.catalogo_service.sync.infrastructure.client.mapper;

import com.example.catalogo_service.catalog.domain.model.Category;
import com.example.catalogo_service.catalog.domain.model.Professional;
import com.example.catalogo_service.catalog.domain.model.WeeklySchedule;
import com.example.catalogo_service.sync.domain.model.CatalogSnapshot;
import com.example.catalogo_service.sync.infrastructure.client.dto.CategorySnapshotDto;
import com.example.catalogo_service.sync.infrastructure.client.dto.ProfessionalSnapshotDto;
import com.example.catalogo_service.sync.infrastructure.client.dto.SnapshotResponse;
import com.example.catalogo_service.sync.infrastructure.client.dto.WeeklyScheduleSnapshotDto;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SnapshotMapperTest {

    private final SnapshotMapper mapper = new SnapshotMapper();

    @Test
    void debeDevolverNull_cuandoLaRespuestaEsNull() {
        CatalogSnapshot snapshot = mapper.toDomainModel(null);

        assertThat(snapshot).isNull();
    }

    @Test
    void debeConvertirADominio_cuandoLaRespuestaTieneUnElementoDeCadaColeccion() {
        Instant createdAt = Instant.parse("2026-07-01T12:00:00Z");
        Instant updatedAt = Instant.parse("2026-09-10T13:40:00Z");
        Instant generatedAt = Instant.parse("2026-09-10T13:44:59Z");

        CategorySnapshotDto categoryDto = new CategorySnapshotDto(1L, "Clinica medica", "Atencion general", true, createdAt, updatedAt);
        ProfessionalSnapshotDto professionalDto = new ProfessionalSnapshotDto(15L, 1L, "Ana", "Gomez", true, createdAt, updatedAt);
        WeeklyScheduleSnapshotDto weeklyScheduleDto = new WeeklyScheduleSnapshotDto(
                21L, 15L, "MONDAY", LocalTime.of(9, 0), LocalTime.of(12, 0), 30, true, createdAt, updatedAt);

        SnapshotResponse response = new SnapshotResponse(
                7L, generatedAt, List.of(categoryDto), List.of(professionalDto), List.of(weeklyScheduleDto));

        CatalogSnapshot snapshot = mapper.toDomainModel(response);

        assertThat(snapshot).isNotNull();
        assertThat(snapshot.getSnapshotVersion()).isEqualTo(7L);
        assertThat(snapshot.getGeneratedAt()).isEqualTo(generatedAt);

        assertThat(snapshot.getCategories()).hasSize(1);
        Category category = snapshot.getCategories().get(0);
        assertThat(category)
                .extracting("id", "name", "description", "enabled", "createdAt", "updatedAt")
                .containsExactly(1L, "Clinica medica", "Atencion general", true, createdAt, updatedAt);

        assertThat(snapshot.getProfessionals()).hasSize(1);
        Professional professional = snapshot.getProfessionals().get(0);
        assertThat(professional)
                .extracting("id", "categoryId", "firstName", "lastName", "enabled", "createdAt", "updatedAt")
                .containsExactly(15L, 1L, "Ana", "Gomez", true, createdAt, updatedAt);

        assertThat(snapshot.getWeeklySchedules()).hasSize(1);
        WeeklySchedule weeklySchedule = snapshot.getWeeklySchedules().get(0);
        assertThat(weeklySchedule)
                .extracting("id", "professionalId", "dayOfWeek", "startTime", "endTime", "slotDurationMinutes", "enabled", "createdAt", "updatedAt")
                .containsExactly(21L, 15L, DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(12, 0), 30, true, createdAt, updatedAt);
    }

    @Test
    void debeConvertirListasVacias_cuandoLaRespuestaNoTieneElementos() {
        SnapshotResponse response = new SnapshotResponse(
                1L, Instant.now(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList());

        CatalogSnapshot snapshot = mapper.toDomainModel(response);

        assertThat(snapshot.getCategories()).isEmpty();
        assertThat(snapshot.getProfessionals()).isEmpty();
        assertThat(snapshot.getWeeklySchedules()).isEmpty();
    }
}
