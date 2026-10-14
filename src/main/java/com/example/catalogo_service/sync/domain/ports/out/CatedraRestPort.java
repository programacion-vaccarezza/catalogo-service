package com.example.catalogo_service.sync.domain.ports.out;

import com.example.catalogo_service.sync.domain.model.CatalogSnapshot;

public interface CatedraRestPort {

    CatalogSnapshot fetchSnapshot();
}
