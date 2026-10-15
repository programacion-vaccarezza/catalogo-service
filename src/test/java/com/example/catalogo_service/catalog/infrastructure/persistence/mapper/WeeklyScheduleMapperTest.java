package com.example.catalogo_service.catalog.infrastructure.persistence.mapper;

import com.example.catalogo_service.catalog.domain.model.WeeklySchedule;
import com.example.catalogo_service.catalog.infrastructure.persistence.entity.WeeklyScheduleEntity;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class WeeklyScheduleMapperTest {

    private final WeeklyScheduleMapper mapper = new WeeklyScheduleMapper();

    @Test
    void debeConvertirAEntity_cuandoElHorarioEsValido() {
        Instant createdAt = Instant.parse("2026-07-01T12:00:00Z");
        Instant updatedAt = Instant.parse("2026-09-10T13:40:00Z");
        WeeklySchedule weeklySchedule = WeeklySchedule.builder()
                .id(21L)
                .professionalId(15L)
                .dayOfWeek(DayOfWeek.MONDAY)
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(12, 0))
                .slotDurationMinutes(30)
                .enabled(true)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();

        WeeklyScheduleEntity entity = mapper.toEntity(weeklySchedule);

        assertThat(entity).isNotNull();
        assertThat(entity)
                .extracting("id", "professionalId", "dayOfWeek", "startTime", "endTime", "slotDurationMinutes", "enabled", "createdAt", "updatedAt")
                .containsExactly(21L, 15L, DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(12, 0), 30, true, createdAt, updatedAt);
    }

    @Test
    void debeDevolverNull_cuandoElHorarioATransformarEsNull() {
        WeeklyScheduleEntity entity = mapper.toEntity(null);

        assertThat(entity).isNull();
    }

    @Test
    void debeConvertirADominio_cuandoLaEntidadEsValida() {
        Instant createdAt = Instant.parse("2026-07-01T12:00:00Z");
        Instant updatedAt = Instant.parse("2026-09-10T13:40:00Z");
        WeeklyScheduleEntity entity = WeeklyScheduleEntity.builder()
                .id(22L)
                .professionalId(16L)
                .dayOfWeek(DayOfWeek.FRIDAY)
                .startTime(LocalTime.of(14, 0))
                .endTime(LocalTime.of(18, 0))
                .slotDurationMinutes(45)
                .enabled(false)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();

        WeeklySchedule weeklySchedule = mapper.toDomainModel(entity);

        assertThat(weeklySchedule).isNotNull();
        assertThat(weeklySchedule)
                .extracting("id", "professionalId", "dayOfWeek", "startTime", "endTime", "slotDurationMinutes", "enabled", "createdAt", "updatedAt")
                .containsExactly(22L, 16L, DayOfWeek.FRIDAY, LocalTime.of(14, 0), LocalTime.of(18, 0), 45, false, createdAt, updatedAt);
    }

    @Test
    void debeDevolverNull_cuandoLaEntidadATransformarEsNull() {
        WeeklySchedule weeklySchedule = mapper.toDomainModel(null);

        assertThat(weeklySchedule).isNull();
    }
}
