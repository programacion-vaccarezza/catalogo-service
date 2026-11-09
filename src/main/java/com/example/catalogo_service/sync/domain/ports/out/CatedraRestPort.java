package com.example.catalogo_service.sync.domain.ports.out;

import com.example.catalogo_service.sync.domain.model.CatalogSnapshot;

// Port de salida: el REST de la cátedra que devuelve el snapshot completo del catálogo
public interface CatedraRestPort {

    // Pide el snapshot completo actual
    CatalogSnapshot fetchSnapshot();
}
