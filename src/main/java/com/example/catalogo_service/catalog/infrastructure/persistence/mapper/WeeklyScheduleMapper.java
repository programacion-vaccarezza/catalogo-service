package com.example.catalogo_service.catalog.infrastructure.persistence.mapper;

import com.example.catalogo_service.catalog.domain.model.WeeklySchedule;
import com.example.catalogo_service.catalog.infrastructure.persistence.entity.WeeklyScheduleEntity;
import org.springframework.stereotype.Component;

@Component
public class WeeklyScheduleMapper {

    public WeeklyScheduleEntity toEntity(WeeklySchedule weeklySchedule) {
        if (weeklySchedule == null) {
            return null;
        }
        return WeeklyScheduleEntity.builder()
                .id(weeklySchedule.getId())
                .professionalId(weeklySchedule.getProfessionalId())
                .dayOfWeek(weeklySchedule.getDayOfWeek())
                .startTime(weeklySchedule.getStartTime())
                .endTime(weeklySchedule.getEndTime())
                .slotDurationMinutes(weeklySchedule.getSlotDurationMinutes())
                .enabled(weeklySchedule.isEnabled())
                .createdAt(weeklySchedule.getCreatedAt())
                .updatedAt(weeklySchedule.getUpdatedAt())
                .build();
    }

    public WeeklySchedule toDomainModel(WeeklyScheduleEntity entity) {
        if (entity == null) {
            return null;
        }
        return WeeklySchedule.builder()
                .id(entity.getId())
                .professionalId(entity.getProfessionalId())
                .dayOfWeek(entity.getDayOfWeek())
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .slotDurationMinutes(entity.getSlotDurationMinutes())
                .enabled(entity.isEnabled())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
