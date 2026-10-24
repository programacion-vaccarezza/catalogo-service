package com.example.catalogo_service.catalog.application.usecases;

import com.example.catalogo_service.catalog.application.exception.ProfessionalNotFoundException;
import com.example.catalogo_service.catalog.domain.model.Professional;
import com.example.catalogo_service.catalog.domain.ports.out.ProfessionalRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetProfessionalByIdUseCaseImplTest {

    @Mock
    ProfessionalRepository professionalRepository;

    @InjectMocks
    GetProfessionalByIdUseCaseImpl getProfessionalByIdUseCase;

    @Test
    void debeLanzarExcepcion_cuandoElProfesionalNoExiste() {
        when(professionalRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> getProfessionalByIdUseCase.getProfessionalById(999L))
                .isInstanceOf(ProfessionalNotFoundException.class)
                .hasMessage("Profesional no encontrado: 999");
    }

    @Test
    void debeDevolverElProfesional_cuandoExiste() {
        Professional professional = Professional.builder()
                .id(201L)
                .categoryId(101L)
                .firstName("Ana")
                .lastName("Torres")
                .enabled(true)
                .build();
        when(professionalRepository.findById(201L)).thenReturn(Optional.of(professional));

        Professional resultado = getProfessionalByIdUseCase.getProfessionalById(201L);

        assertThat(resultado).isEqualTo(professional);
    }
}
