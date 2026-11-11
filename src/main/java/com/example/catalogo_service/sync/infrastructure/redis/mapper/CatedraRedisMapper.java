package com.example.catalogo_service.sync.infrastructure.redis.mapper;

import com.example.catalogo_service.catalog.domain.model.Category;
import com.example.catalogo_service.catalog.domain.model.Professional;
import com.example.catalogo_service.catalog.domain.model.WeeklySchedule;
import com.example.catalogo_service.sync.domain.model.CatalogChangeSet;
import com.example.catalogo_service.sync.infrastructure.client.dto.CategorySnapshotDto;
import com.example.catalogo_service.sync.infrastructure.client.dto.ProfessionalSnapshotDto;
import com.example.catalogo_service.sync.infrastructure.client.dto.WeeklyScheduleSnapshotDto;
import com.example.catalogo_service.sync.infrastructure.redis.dto.CatalogChangesDto;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

// Mapea los DTOs que llegan de Redis al modelo de dominio
@Component
public class CatedraRedisMapper {

    // Convierte el detalle de cambios (version + IDs) a dominio
    public CatalogChangeSet toDomainModel(CatalogChangesDto dto) {
        if (dto == null) {
            return null;
        }
        return CatalogChangeSet.builder()
                .version(dto.getVersion())
                .categoryIds(dto.getChanges().getProfessionalCategories())
                .professionalIds(dto.getChanges().getProfessionals())
                .weeklyScheduleIds(dto.getChanges().getWeeklySchedules())
                .build();
    }

    // Convierte una categoría leída de Redis a dominio
    public Category toDomainModel(CategorySnapshotDto dto) {
        if (dto == null) {
            return null;
        }
        return Category.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .enabled(dto.isEnabled())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }

    // Convierte un profesional leído de Redis a dominio
    public Professional toDomainModel(ProfessionalSnapshotDto dto) {
        if (dto == null) {
            return null;
        }
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

    // Convierte un horario leído de Redis a dominio
    public WeeklySchedule toDomainModel(WeeklyScheduleSnapshotDto dto) {
        if (dto == null) {
            return null;
        }
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
