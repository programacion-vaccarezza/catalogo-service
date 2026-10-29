package com.example.catalogo_service.catalog.infrastructure.web.controller;

import com.example.catalogo_service.catalog.application.exception.ProfessionalNotFoundException;
import com.example.catalogo_service.catalog.application.service.ProfessionalService;
import com.example.catalogo_service.catalog.domain.model.Professional;
import com.example.catalogo_service.catalog.domain.model.WeeklySchedule;
import com.example.catalogo_service.catalog.infrastructure.web.dto.InternalProfessionalResponse;
import com.example.catalogo_service.catalog.infrastructure.web.dto.InternalWeeklyScheduleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/internal/professionals")
@RequiredArgsConstructor
public class InternalProfessionalController {

    private static final Logger LOG = LoggerFactory.getLogger(InternalProfessionalController.class);

    private final ProfessionalService professionalService;

    @Operation(summary = "Obtener un profesional por su ID (uso exclusivo de servicios internos)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profesional encontrado"),
            @ApiResponse(responseCode = "403", description = "El token no tiene permisos de servicio"),
            @ApiResponse(responseCode = "404", description = "No existe un profesional con ese ID")
    })
    @GetMapping("/{id}")
    public ResponseEntity<InternalProfessionalResponse> getProfessionalById(@PathVariable Long id, Authentication authentication) {
        LOG.info("Servicio {} consulto el profesional {}", authentication.getName(), id);
        try {
            Professional professional = professionalService.getProfessionalById(id);
            return ResponseEntity.ok(InternalProfessionalResponse.builder()
                    .id(professional.getId())
                    .categoryId(professional.getCategoryId())
                    .firstName(professional.getFirstName())
                    .lastName(professional.getLastName())
                    .enabled(professional.isEnabled())
                    .build());
        } catch (ProfessionalNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @Operation(summary = "Obtener los horarios semanales de un profesional (uso exclusivo de servicios internos)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Horarios encontrados"),
            @ApiResponse(responseCode = "403", description = "El token no tiene permisos de servicio"),
            @ApiResponse(responseCode = "404", description = "No existe un profesional con ese ID")
    })
    @GetMapping("/{id}/schedules")
    public ResponseEntity<List<InternalWeeklyScheduleResponse>> getWeeklySchedulesByProfessionalId(@PathVariable Long id, Authentication authentication) {
        LOG.info("Servicio {} consulto los horarios del profesional {}", authentication.getName(), id);
        try {
            List<WeeklySchedule> weeklySchedules = professionalService.getWeeklySchedulesByProfessionalId(id);
            List<InternalWeeklyScheduleResponse> response = weeklySchedules.stream()
                    .map(weeklySchedule -> InternalWeeklyScheduleResponse.builder()
                            .id(weeklySchedule.getId())
                            .dayOfWeek(weeklySchedule.getDayOfWeek())
                            .startTime(weeklySchedule.getStartTime())
                            .endTime(weeklySchedule.getEndTime())
                            .slotDurationMinutes(weeklySchedule.getSlotDurationMinutes())
                            .enabled(weeklySchedule.isEnabled())
                            .build())
                    .toList();
            return ResponseEntity.ok(response);
        } catch (ProfessionalNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
