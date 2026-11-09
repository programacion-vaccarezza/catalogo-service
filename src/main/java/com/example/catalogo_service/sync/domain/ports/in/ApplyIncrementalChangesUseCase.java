package com.example.catalogo_service.sync.domain.ports.in;

// Caso de uso: aplicar los cambios de catálogo hasta alcanzar una version objetivo
public interface ApplyIncrementalChangesUseCase {

    // Avanza la version local hasta targetVersion, o cae a sincronización completa si no puede
    void applyChanges(long targetVersion);
}
