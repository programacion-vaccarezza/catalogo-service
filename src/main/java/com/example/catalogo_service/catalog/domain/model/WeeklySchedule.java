package com.example.catalogo_service.catalog.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalTime;

// Horario semanal recurrente de un profesional, copia local de la que publica la cátedra
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeeklySchedule {

    private Long id;
    private Long professionalId;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private int slotDurationMinutes;
    private boolean enabled;
    private Instant createdAt;
    private Instant updatedAt;
}
