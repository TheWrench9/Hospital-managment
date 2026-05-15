package com.hospital.controller;

import com.hospital.dto.request.MedicalRecordRequest;
import com.hospital.dto.response.*;
import com.hospital.service.MedicalRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/medical-records")
@RequiredArgsConstructor
@Tag(name = "Medical Records", description = "Patient medical records, diagnoses & prescriptions")
@SecurityRequirement(name = "bearerAuth")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    @PostMapping
    @Operation(summary = "Create a medical record for an appointment")
    public ResponseEntity<ApiResponse<MedicalRecordResponse>> create(@Valid @RequestBody MedicalRecordRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Medical record created", medicalRecordService.create(request)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get medical record by ID")
    public ResponseEntity<ApiResponse<MedicalRecordResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(medicalRecordService.getById(id)));
    }

    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get all medical records for a patient")
    public ResponseEntity<ApiResponse<Page<MedicalRecordResponse>>> getByPatient(
            @PathVariable Long patientId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.success(medicalRecordService.getByPatient(patientId, page, size)));
    }

    @GetMapping("/appointment/{appointmentId}")
    @Operation(summary = "Get medical record by appointment ID")
    public ResponseEntity<ApiResponse<MedicalRecordResponse>> getByAppointment(@PathVariable Long appointmentId) {
        return ResponseEntity.ok(ApiResponse.success(medicalRecordService.getByAppointment(appointmentId)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a medical record")
    public ResponseEntity<ApiResponse<MedicalRecordResponse>> update(
            @PathVariable Long id, @RequestBody MedicalRecordRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Medical record updated", medicalRecordService.update(id, request)));
    }
}
