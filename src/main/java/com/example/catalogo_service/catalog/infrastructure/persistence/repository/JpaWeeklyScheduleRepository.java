package com.example.catalogo_service.catalog.infrastructure.persistence.repository;

import com.example.catalogo_service.catalog.infrastructure.persistence.entity.WeeklyScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaWeeklyScheduleRepository extends JpaRepository<WeeklyScheduleEntity, Long> {

    List<WeeklyScheduleEntity> findByProfessionalId(Long professionalId);
}
