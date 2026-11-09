package com.example.catalogo_service.catalog.infrastructure.persistence.adapter;

import com.example.catalogo_service.catalog.domain.model.WeeklySchedule;
import com.example.catalogo_service.catalog.domain.ports.out.WeeklyScheduleRepository;
import com.example.catalogo_service.catalog.infrastructure.persistence.entity.WeeklyScheduleEntity;
import com.example.catalogo_service.catalog.infrastructure.persistence.mapper.WeeklyScheduleMapper;
import com.example.catalogo_service.catalog.infrastructure.persistence.repository.JpaWeeklyScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

// Adapter que implementa WeeklyScheduleRepository usando JPA
@Component
@RequiredArgsConstructor
public class JpaWeeklyScheduleRepositoryAdapter implements WeeklyScheduleRepository {

    private final JpaWeeklyScheduleRepository jpaWeeklyScheduleRepository;
    private final WeeklyScheduleMapper weeklyScheduleMapper;

    // Guarda el horario y devuelve el modelo de dominio ya persistido
    @Override
    public WeeklySchedule save(WeeklySchedule weeklySchedule) {
        WeeklyScheduleEntity weeklyScheduleEntity = weeklyScheduleMapper.toEntity(weeklySchedule);
        WeeklyScheduleEntity savedEntity = jpaWeeklyScheduleRepository.save(weeklyScheduleEntity);
        return weeklyScheduleMapper.toDomainModel(savedEntity);
    }

    // Devuelve los horarios de un profesional mapeados a dominio
    @Override
    public List<WeeklySchedule> findByProfessionalId(Long professionalId) {
        return jpaWeeklyScheduleRepository.findByProfessionalId(professionalId).stream()
                .map(weeklyScheduleMapper::toDomainModel)
                .toList();
    }
}
