package com.example.catalogo_service.sync.domain.ports.out;

import com.example.catalogo_service.catalog.domain.model.Category;
import com.example.catalogo_service.catalog.domain.model.Professional;
import com.example.catalogo_service.catalog.domain.model.WeeklySchedule;
import com.example.catalogo_service.sync.domain.model.CatalogChangeSet;

import java.util.Optional;

// Port de salida: el Redis de la cátedra con el catálogo publicado y su historial incremental
public interface CatedraRedisPort {

    // Devuelve los IDs que cambiaron en esa version
    CatalogChangeSet fetchChanges(long version);
    // Devuelve el estado actual de una categoría, si todavía existe en Redis
    Optional<Category> fetchCategory(Long id);
    // Devuelve el estado actual de un profesional, si todavía existe en Redis
    Optional<Professional> fetchProfessional(Long id);
    // Devuelve el estado actual de un horario, si todavía existe en Redis
    Optional<WeeklySchedule> fetchWeeklySchedule(Long id);
    // Devuelve la version más antigua que todavía se puede reconstruir de forma incremental
    long fetchOldestAvailableVersion();
}
