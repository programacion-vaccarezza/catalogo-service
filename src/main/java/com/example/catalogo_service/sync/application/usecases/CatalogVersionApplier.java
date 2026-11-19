package com.example.catalogo_service.sync.application.usecases;

import com.example.catalogo_service.catalog.domain.ports.out.CategoryRepository;
import com.example.catalogo_service.catalog.domain.ports.out.ProfessionalRepository;
import com.example.catalogo_service.catalog.domain.ports.out.WeeklyScheduleRepository;
import com.example.catalogo_service.sync.domain.model.CatalogChangeSet;
import com.example.catalogo_service.sync.domain.model.CatalogSyncState;
import com.example.catalogo_service.sync.domain.ports.out.CatalogSyncStateRepository;
import com.example.catalogo_service.sync.domain.ports.out.CatedraRedisPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

// Aplica una única versión de cambios: trae de Redis solo las entidades que cambiaron
@Component
@RequiredArgsConstructor
public class CatalogVersionApplier {

    private final CatedraRedisPort catedraRedisPort;
    private final CategoryRepository categoryRepository;
    private final ProfessionalRepository professionalRepository;
    private final WeeklyScheduleRepository weeklyScheduleRepository;
    private final CatalogSyncStateRepository catalogSyncStateRepository;

    // Busca los IDs que cambiaron en esta versión, guarda el estado actual de cada uno, y
    // recién al final avanza la version local en una sola transacción
    @Transactional
    public void applyVersion(long version) {
        CatalogChangeSet changeSet = catedraRedisPort.fetchChanges(version);

        // Si un ID ya no existe en Redis simplemente se ignora
        changeSet.getCategoryIds().forEach(id ->
                catedraRedisPort.fetchCategory(id).ifPresent(categoryRepository::save));
        changeSet.getProfessionalIds().forEach(id ->
                catedraRedisPort.fetchProfessional(id).ifPresent(professionalRepository::save));
        changeSet.getWeeklyScheduleIds().forEach(id ->
                catedraRedisPort.fetchWeeklySchedule(id).ifPresent(weeklyScheduleRepository::save));

        catalogSyncStateRepository.save(CatalogSyncState.builder()
                .version(version)
                .updatedAt(Instant.now())
                .build());
    }
}
