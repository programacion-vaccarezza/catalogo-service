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

// Aplica los cambios de catálogo versión por versión desde Redis, cayendo a sincronización
// completa si detecta una discontinuidad o cualquier error inesperado
@Component
@RequiredArgsConstructor
public class ApplyIncrementalChangesUseCaseImpl implements ApplyIncrementalChangesUseCase {

    private static final Logger LOG = LoggerFactory.getLogger(ApplyIncrementalChangesUseCaseImpl.class);

    private final CatalogSyncStateRepository catalogSyncStateRepository;
    private final CatalogVersionApplier catalogVersionApplier;
    private final CatedraRedisPort catedraRedisPort;
    private final ForceFullSyncUseCase forceFullSyncUseCase;

    // Avanza la version local hasta targetVersion aplicando cada version intermedia en orden
    @Override
    public void applyChanges(long targetVersion) {
        long localVersion = catalogSyncStateRepository.find()
                .map(CatalogSyncState::getVersion)
                .orElse(0L);

        // Ya está al día (o el evento llegó duplicado/desordenado)
        if (localVersion >= targetVersion) {
            return;
        }

        try {
            long oldestAvailableVersion = catedraRedisPort.fetchOldestAvailableVersion();
            // Si la version local quedó por debajo de lo que Redis todavía conserva lo incremental
            // no alcanza y se trae el snapshot completo
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
            // Cualquier falla a mitad de camino se resuelve
            // con una sincronización completa en vez de dejar el catálogo en un estado parcial
            LOG.warn("Error al aplicar sincronizacion incremental, reconstruyendo con sincronizacion completa", e);
            forceFullSyncUseCase.sync();
        }
    }
}
