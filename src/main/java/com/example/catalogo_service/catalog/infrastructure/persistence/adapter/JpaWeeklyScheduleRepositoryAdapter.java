package com.example.catalogo_service.catalog.infrastructure.persistence.adapter;

import com.example.catalogo_service.catalog.domain.model.WeeklySchedule;
import com.example.catalogo_service.catalog.domain.ports.out.WeeklyScheduleRepository;
import com.example.catalogo_service.catalog.infrastructure.persistence.entity.WeeklyScheduleEntity;
import com.example.catalogo_service.catalog.infrastructure.persistence.mapper.WeeklyScheduleMapper;
import com.example.catalogo_service.catalog.infrastructure.persistence.repository.JpaWeeklyScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaWeeklyScheduleRepositoryAdapter implements WeeklyScheduleRepository {

    private final JpaWeeklyScheduleRepository jpaWeeklyScheduleRepository;
    private final WeeklyScheduleMapper weeklyScheduleMapper;

    @Override
    public WeeklySchedule save(WeeklySchedule weeklySchedule) {
        WeeklyScheduleEntity weeklyScheduleEntity = weeklyScheduleMapper.toEntity(weeklySchedule);
        WeeklyScheduleEntity savedEntity = jpaWeeklyScheduleRepository.save(weeklyScheduleEntity);
        return weeklyScheduleMapper.toDomainModel(savedEntity);
    }
}
