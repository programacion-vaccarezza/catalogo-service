package com.example.catalogo_service.sync.domain.ports.in;

public interface ApplyIncrementalChangesUseCase {

    void applyChanges(long targetVersion);
}
