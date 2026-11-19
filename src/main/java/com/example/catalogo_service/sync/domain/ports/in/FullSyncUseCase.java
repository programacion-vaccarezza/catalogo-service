package com.example.catalogo_service.sync.domain.ports.in;

// Caso de uso: sincronizar el catálogo completo solo si la base local está vacía
public interface FullSyncUseCase {

    // Sincroniza si no hay datos; si ya hay, no hace nada
    void syncIfEmpty();
}
