package com.example.catalogo_service.sync.application.usecases;

import com.example.catalogo_service.sync.domain.ports.in.ForceFullSyncUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

// Fuerza una sincronización completa incondicional (a diferencia de FullSyncUseCase, que solo
// sincroniza si la base está vacía), usado como fallback cuando la incremental no puede continuar
@Component
@RequiredArgsConstructor
public class ForceFullSyncUseCaseImpl implements ForceFullSyncUseCase {

    private final FullSyncExecutor fullSyncExecutor;

    // Ejecuta la sincronización completa sin preguntar si hace falta
    @Override
    public void sync() {
        fullSyncExecutor.execute();
    }
}
