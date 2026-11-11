package com.example.catalogo_service.sync.domain.ports.in;

// Caso de uso: forzar una sincronización completa, sin importar si la base ya tiene datos
public interface ForceFullSyncUseCase {

    // Ejecuta la sincronización completa incondicionalmente
    void sync();
}
