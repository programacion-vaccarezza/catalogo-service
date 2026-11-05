package com.example.catalogo_service.sync.domain.ports.out;

import com.example.catalogo_service.catalog.domain.model.Category;
import com.example.catalogo_service.catalog.domain.model.Professional;
import com.example.catalogo_service.catalog.domain.model.WeeklySchedule;
import com.example.catalogo_service.sync.domain.model.CatalogChangeSet;

import java.util.Optional;

public interface CatedraRedisPort {

    CatalogChangeSet fetchChanges(long version);
    Optional<Category> fetchCategory(Long id);
    Optional<Professional> fetchProfessional(Long id);
    Optional<WeeklySchedule> fetchWeeklySchedule(Long id);
    long fetchOldestAvailableVersion();
}
