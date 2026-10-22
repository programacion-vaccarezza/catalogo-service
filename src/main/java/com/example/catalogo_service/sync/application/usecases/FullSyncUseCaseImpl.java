package com.example.catalogo_service.sync.application.usecases;

import com.example.catalogo_service.catalog.domain.ports.out.CategoryRepository;
import com.example.catalogo_service.catalog.domain.ports.out.ProfessionalRepository;
import com.example.catalogo_service.catalog.domain.ports.out.WeeklyScheduleRepository;
import com.example.catalogo_service.sync.domain.model.CatalogSnapshot;
import com.example.catalogo_service.sync.domain.model.CatalogSyncState;
import com.example.catalogo_service.sync.domain.ports.in.FullSyncUseCase;
import com.example.catalogo_service.sync.domain.ports.out.CatalogSyncStateRepository;
import com.example.catalogo_service.sync.domain.ports.out.CatedraRestPort;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class FullSyncUseCaseImpl implements FullSyncUseCase {

    private static final Logger LOG = LoggerFactory.getLogger(FullSyncUseCaseImpl.class);

    private final CatedraRestPort catedraRestPort;
    private final CategoryRepository categoryRepository;
    private final ProfessionalRepository professionalRepository;
    private final WeeklyScheduleRepository weeklyScheduleRepository;
    private final CatalogSyncStateRepository catalogSyncStateRepository;

    @Override
    @Transactional
    public void syncIfEmpty() {
        if (professionalRepository.existsAny()) {
            LOG.debug("Catalogo local ya tiene datos, se omite la sincronizacion completa");
            return;
        }

        LOG.info("Catalogo local vacio, iniciando sincronizacion completa");
        CatalogSnapshot snapshot = catedraRestPort.fetchSnapshot();

        snapshot.getCategories().forEach(categoryRepository::save);
        snapshot.getProfessionals().forEach(professionalRepository::save);
        snapshot.getWeeklySchedules().forEach(weeklyScheduleRepository::save);

        catalogSyncStateRepository.save(CatalogSyncState.builder()
                .version(snapshot.getSnapshotVersion())
                .updatedAt(Instant.now())
                .build());

        LOG.info("Sincronizacion completa aplicada, version {}", snapshot.getSnapshotVersion());
    }
}
