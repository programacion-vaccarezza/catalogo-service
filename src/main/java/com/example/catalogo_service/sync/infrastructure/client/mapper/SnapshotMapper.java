package com.example.catalogo_service.sync.infrastructure.client.mapper;

import com.example.catalogo_service.catalog.domain.model.Category;
import com.example.catalogo_service.catalog.domain.model.Professional;
import com.example.catalogo_service.catalog.domain.model.WeeklySchedule;
import com.example.catalogo_service.sync.domain.model.CatalogSnapshot;
import com.example.catalogo_service.sync.infrastructure.client.dto.CategorySnapshotDto;
import com.example.catalogo_service.sync.infrastructure.client.dto.ProfessionalSnapshotDto;
import com.example.catalogo_service.sync.infrastructure.client.dto.SnapshotResponse;
import com.example.catalogo_service.sync.infrastructure.client.dto.WeeklyScheduleSnapshotDto;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.util.List;

@Component
public class SnapshotMapper {

    public CatalogSnapshot toDomainModel(SnapshotResponse response) {
        if (response == null) {
            return null;
        }
        return CatalogSnapshot.builder()
                .snapshotVersion(response.getSnapshotVersion())
                .generatedAt(response.getGeneratedAt())
                .categories(toCategories(response.getProfessionalCategories()))
                .professionals(toProfessionals(response.getProfessionals()))
                .weeklySchedules(toWeeklySchedules(response.getWeeklySchedules()))
                .build();
    }

    private List<Category> toCategories(List<CategorySnapshotDto> dtos) {
        return dtos.stream().map(this::toCategory).toList();
    }

    private Category toCategory(CategorySnapshotDto dto) {
        return Category.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .enabled(dto.isEnabled())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }

    private List<Professional> toProfessionals(List<ProfessionalSnapshotDto> dtos) {
        return dtos.stream().map(this::toProfessional).toList();
    }

    private Professional toProfessional(ProfessionalSnapshotDto dto) {
        return Professional.builder()
                .id(dto.getId())
                .categoryId(dto.getCategoryId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .enabled(dto.isEnabled())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }

    private List<WeeklySchedule> toWeeklySchedules(List<WeeklyScheduleSnapshotDto> dtos) {
        return dtos.stream().map(this::toWeeklySchedule).toList();
    }

    private WeeklySchedule toWeeklySchedule(WeeklyScheduleSnapshotDto dto) {
        return WeeklySchedule.builder()
                .id(dto.getId())
                .professionalId(dto.getProfessionalId())
                .dayOfWeek(DayOfWeek.valueOf(dto.getDayOfWeek()))
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .slotDurationMinutes(dto.getSlotDurationMinutes())
                .enabled(dto.isEnabled())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }
}
