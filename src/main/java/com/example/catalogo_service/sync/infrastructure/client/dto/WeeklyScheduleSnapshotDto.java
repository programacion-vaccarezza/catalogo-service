package com.example.catalogo_service.sync.infrastructure.client.dto;

import lombok.*;

import java.time.Instant;
import java.time.LocalTime;

// Horario semanal tal como viene en el JSON del snapshot REST de la cátedra
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyScheduleSnapshotDto {

    private Long id;
    private Long professionalId;
    // Viene como texto (ej. "MONDAY") el mapper lo convierte a DayOfWeek
    private String dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private int slotDurationMinutes;
    private boolean enabled;
    private Instant createdAt;
    private Instant updatedAt;
}
