package com.example.catalogo_service.sync.application.usecases;

import com.example.catalogo_service.catalog.domain.ports.out.ProfessionalRepository;
import com.example.catalogo_service.sync.domain.ports.in.FullSyncUseCase;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

// Sincroniza el catálogo completo solo si la base local está vacía
@Component
@RequiredArgsConstructor
public class FullSyncUseCaseImpl implements FullSyncUseCase {

    private static final Logger LOG = LoggerFactory.getLogger(FullSyncUseCaseImpl.class);

    private final ProfessionalRepository professionalRepository;
    private final FullSyncExecutor fullSyncExecutor;

    // Si ya hay datos no hace nada; si está vacía, ejecuta la sincronización completa
    @Override
    public void syncIfEmpty() {
        if (professionalRepository.existsAny()) {
            LOG.debug("Catalogo local ya tiene datos, se omite la sincronizacion completa");
            return;
        }

        LOG.info("Catalogo local vacio, iniciando sincronizacion completa");
        fullSyncExecutor.execute();
    }
}
