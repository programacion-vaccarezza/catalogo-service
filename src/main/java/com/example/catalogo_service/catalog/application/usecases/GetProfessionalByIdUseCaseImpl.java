package com.example.catalogo_service.catalog.application.usecases;

import com.example.catalogo_service.catalog.application.exception.ProfessionalNotFoundException;
import com.example.catalogo_service.catalog.domain.model.Professional;
import com.example.catalogo_service.catalog.domain.ports.in.GetProfessionalByIdUseCase;
import com.example.catalogo_service.catalog.domain.ports.out.ProfessionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

// Busca un profesional por id en la copia local del catálogo
@Component
@RequiredArgsConstructor
public class GetProfessionalByIdUseCaseImpl implements GetProfessionalByIdUseCase {

    private final ProfessionalRepository professionalRepository;

    // Busca el profesional o lanza excepción si no existe
    @Override
    public Professional getProfessionalById(Long id) {
        return professionalRepository.findById(id)
                .orElseThrow(() -> new ProfessionalNotFoundException(id));
    }
}
