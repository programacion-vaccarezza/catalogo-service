package com.example.catalogo_service.sync.domain.model;

import com.example.catalogo_service.catalog.domain.model.Category;
import com.example.catalogo_service.catalog.domain.model.Professional;
import com.example.catalogo_service.catalog.domain.model.WeeklySchedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatalogSnapshot {

    private long snapshotVersion;
    private Instant generatedAt;
    private List<Category> categories;
    private List<Professional> professionals;
    private List<WeeklySchedule> weeklySchedules;
}
