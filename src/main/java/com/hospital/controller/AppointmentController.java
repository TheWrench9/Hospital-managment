package com.hospital.controller;

import com.hospital.dto.request.AppointmentRequest;
import com.hospital.dto.response.*;
import com.hospital.entity.enums.AppointmentStatus;
import com.hospital.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
@Tag(name = "Appointments", description = "Appointment booking and management")
@SecurityRequirement(name = "bearerAuth")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    @Operation(summary = "Book a new appointment", description = "Automatically sends confirmation email to patient")
    public ResponseEntity<ApiResponse<AppointmentResponse>> book(@Valid @RequestBody AppointmentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Appointment booked", appointmentService.create(request)));
    }

    @GetMapping
    @Operation(summary = "Get all appointments (paginated)")
    public ResponseEntity<ApiResponse<Page<AppointmentResponse>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.success(appointmentService.getAll(page, size)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get appointment by ID")
    public ResponseEntity<ApiResponse<AppointmentResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(appointmentService.getById(id)));
    }

    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get appointments by patient")
    public ResponseEntity<ApiResponse<Page<AppointmentResponse>>> getByPatient(
            @PathVariable Long patientId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.success(appointmentService.getByPatient(patientId, page, size)));
    }

    @GetMapping("/doctor/{doctorId}")
    @Operation(summary = "Get appointments by doctor")
    public ResponseEntity<ApiResponse<Page<AppointmentResponse>>> getByDoctor(
            @PathVariable Long doctorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.success(appointmentService.getByDoctor(doctorId, page, size)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update appointment details")
    public ResponseEntity<ApiResponse<AppointmentResponse>> update(@PathVariable Long id, @RequestBody AppointmentRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Appointment updated", appointmentService.update(id, request)));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Update appointment status", description = "SCHEDULED | CONFIRMED | COMPLETED | CANCELLED | NO_SHOW")
    public ResponseEntity<ApiResponse<AppointmentResponse>> updateStatus(
            @PathVariable Long id, @RequestParam AppointmentStatus status) {
        return ResponseEntity.ok(ApiResponse.success("Status updated", appointmentService.updateStatus(id, status)));
    }

    @DeleteMapping("/{id}/cancel")
    @Operation(summary = "Cancel an appointment")
    public ResponseEntity<ApiResponse<Void>> cancel(@PathVariable Long id) {
        appointmentService.cancel(id);
        return ResponseEntity.ok(ApiResponse.success("Appointment cancelled", null));
    }
}
