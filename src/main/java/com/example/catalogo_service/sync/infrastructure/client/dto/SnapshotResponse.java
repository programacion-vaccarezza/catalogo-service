package com.example.catalogo_service.sync.infrastructure.client.dto;

import lombok.*;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SnapshotResponse {

    private long snapshotVersion;
    private Instant generatedAt;
    private List<CategorySnapshotDto> professionalCategories;
    private List<ProfessionalSnapshotDto> professionals;
    private List<WeeklyScheduleSnapshotDto> weeklySchedules;
}
