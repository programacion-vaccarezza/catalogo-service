package com.example.catalogo_service.sync.infrastructure.runner;

import com.example.catalogo_service.sync.domain.ports.in.FullSyncUseCase;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FullSyncStartupRunner implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(FullSyncStartupRunner.class);

    private final FullSyncUseCase fullSyncUseCase;

    @Override
    public void run(String... args) {
        try {
            fullSyncUseCase.syncIfEmpty();
        } catch (Exception e) {
            LOG.error("Error al sincronizar el catalogo en el arranque, el servicio continua sin catalogo local", e);
        }
    }
}
