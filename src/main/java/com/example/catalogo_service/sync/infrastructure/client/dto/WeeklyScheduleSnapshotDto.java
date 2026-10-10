package com.example.catalogo_service.sync.infrastructure.client.dto;

import lombok.*;

import java.time.Instant;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyScheduleSnapshotDto {

    private Long id;
    private Long professionalId;
    private String dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private int slotDurationMinutes;
    private boolean enabled;
    private Instant createdAt;
    private Instant updatedAt;
}
