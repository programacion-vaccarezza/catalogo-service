package com.example.catalogo_service.sync.infrastructure.redis.mapper;

import com.example.catalogo_service.catalog.domain.model.Category;
import com.example.catalogo_service.catalog.domain.model.Professional;
import com.example.catalogo_service.catalog.domain.model.WeeklySchedule;
import com.example.catalogo_service.sync.domain.model.CatalogChangeSet;
import com.example.catalogo_service.sync.infrastructure.client.dto.CategorySnapshotDto;
import com.example.catalogo_service.sync.infrastructure.client.dto.ProfessionalSnapshotDto;
import com.example.catalogo_service.sync.infrastructure.client.dto.WeeklyScheduleSnapshotDto;
import com.example.catalogo_service.sync.infrastructure.redis.dto.CatalogChangesDetailDto;
import com.example.catalogo_service.sync.infrastructure.redis.dto.CatalogChangesDto;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CatedraRedisMapperTest {

    private final CatedraRedisMapper mapper = new CatedraRedisMapper();

    @Test
    void debeMapearElChangeSet_cuandoElDtoDeCambiosEsValido() {
        CatalogChangesDetailDto detail = new CatalogChangesDetailDto(List.of(1L), List.of(15L), List.of(21L));
        CatalogChangesDto dto = new CatalogChangesDto(9L, detail);

        CatalogChangeSet changeSet = mapper.toDomainModel(dto);

        assertThat(changeSet.getVersion()).isEqualTo(9L);
        assertThat(changeSet.getCategoryIds()).containsExactly(1L);
        assertThat(changeSet.getProfessionalIds()).containsExactly(15L);
        assertThat(changeSet.getWeeklyScheduleIds()).containsExactly(21L);
    }

    @Test
    void debeMapearLaCategoria_cuandoElDtoEsValido() {
        Instant now = Instant.parse("2026-10-20T10:00:00Z");
        CategorySnapshotDto dto = new CategorySnapshotDto(1L, "Clinica medica", "Descripcion", true, now, now);

        Category category = mapper.toDomainModel(dto);

        assertThat(category.getId()).isEqualTo(1L);
        assertThat(category.getName()).isEqualTo("Clinica medica");
        assertThat(category.getDescription()).isEqualTo("Descripcion");
        assertThat(category.isEnabled()).isTrue();
        assertThat(category.getCreatedAt()).isEqualTo(now);
        assertThat(category.getUpdatedAt()).isEqualTo(now);
    }

    @Test
    void debeMapearElProfesional_cuandoElDtoEsValido() {
        Instant now = Instant.parse("2026-10-20T10:00:00Z");
        ProfessionalSnapshotDto dto = new ProfessionalSnapshotDto(15L, 1L, "Ana", "Gomez", true, now, now);

        Professional professional = mapper.toDomainModel(dto);

        assertThat(professional.getId()).isEqualTo(15L);
        assertThat(professional.getCategoryId()).isEqualTo(1L);
        assertThat(professional.getFirstName()).isEqualTo("Ana");
        assertThat(professional.getLastName()).isEqualTo("Gomez");
        assertThat(professional.isEnabled()).isTrue();
        assertThat(professional.getCreatedAt()).isEqualTo(now);
        assertThat(professional.getUpdatedAt()).isEqualTo(now);
    }

    @Test
    void debeMapearElHorarioSemanal_cuandoElDtoEsValido() {
        Instant now = Instant.parse("2026-10-20T10:00:00Z");
        WeeklyScheduleSnapshotDto dto = new WeeklyScheduleSnapshotDto(
                21L, 15L, "MONDAY", LocalTime.of(9, 0), LocalTime.of(12, 0), 30, true, now, now);

        WeeklySchedule weeklySchedule = mapper.toDomainModel(dto);

        assertThat(weeklySchedule.getId()).isEqualTo(21L);
        assertThat(weeklySchedule.getProfessionalId()).isEqualTo(15L);
        assertThat(weeklySchedule.getDayOfWeek()).isEqualTo(DayOfWeek.MONDAY);
        assertThat(weeklySchedule.getStartTime()).isEqualTo(LocalTime.of(9, 0));
        assertThat(weeklySchedule.getEndTime()).isEqualTo(LocalTime.of(12, 0));
        assertThat(weeklySchedule.getSlotDurationMinutes()).isEqualTo(30);
        assertThat(weeklySchedule.isEnabled()).isTrue();
        assertThat(weeklySchedule.getCreatedAt()).isEqualTo(now);
        assertThat(weeklySchedule.getUpdatedAt()).isEqualTo(now);
    }

    @Test
    void debeDevolverNull_cuandoElDtoDeCambiosEsNull() {
        assertThat(mapper.toDomainModel((CatalogChangesDto) null)).isNull();
    }

    @Test
    void debeDevolverNull_cuandoElDtoDeCategoriaEsNull() {
        assertThat(mapper.toDomainModel((CategorySnapshotDto) null)).isNull();
    }

    @Test
    void debeDevolverNull_cuandoElDtoDeProfesionalEsNull() {
        assertThat(mapper.toDomainModel((ProfessionalSnapshotDto) null)).isNull();
    }

    @Test
    void debeDevolverNull_cuandoElDtoDeHorarioSemanalEsNull() {
        assertThat(mapper.toDomainModel((WeeklyScheduleSnapshotDto) null)).isNull();
    }
}
