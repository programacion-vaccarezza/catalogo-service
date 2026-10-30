package com.example.catalogo_service.sync.application.usecases;

import com.example.catalogo_service.sync.domain.model.CatalogSyncState;
import com.example.catalogo_service.sync.domain.ports.in.ApplyIncrementalChangesUseCase;
import com.example.catalogo_service.sync.domain.ports.in.ForceFullSyncUseCase;
import com.example.catalogo_service.sync.domain.ports.out.CatalogSyncStateRepository;
import com.example.catalogo_service.sync.domain.ports.out.CatedraRedisPort;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplyIncrementalChangesUseCaseImpl implements ApplyIncrementalChangesUseCase {

    private static final Logger LOG = LoggerFactory.getLogger(ApplyIncrementalChangesUseCaseImpl.class);

    private final CatalogSyncStateRepository catalogSyncStateRepository;
    private final CatalogVersionApplier catalogVersionApplier;
    private final CatedraRedisPort catedraRedisPort;
    private final ForceFullSyncUseCase forceFullSyncUseCase;

    @Override
    public void applyChanges(long targetVersion) {
        long localVersion = catalogSyncStateRepository.find()
                .map(CatalogSyncState::getVersion)
                .orElse(0L);

        if (localVersion >= targetVersion) {
            return;
        }

        try {
            long oldestAvailableVersion = catedraRedisPort.fetchOldestAvailableVersion();
            if (localVersion < oldestAvailableVersion) {
                LOG.warn("Discontinuidad detectada (version local {} < version mas antigua disponible {}), " +
                        "reconstruyendo con sincronizacion completa", localVersion, oldestAvailableVersion);
                forceFullSyncUseCase.sync();
                return;
            }

            for (long version = localVersion + 1; version <= targetVersion; version++) {
                catalogVersionApplier.applyVersion(version);
                LOG.info("Version incremental {} aplicada", version);
            }
        } catch (Exception e) {
            LOG.warn("Error al aplicar sincronizacion incremental, reconstruyendo con sincronizacion completa", e);
            forceFullSyncUseCase.sync();
        }
    }
}
