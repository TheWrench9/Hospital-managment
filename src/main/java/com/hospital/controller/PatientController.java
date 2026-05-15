package com.hospital.controller;

import com.hospital.dto.request.PatientRequest;
import com.hospital.dto.response.*;
import com.hospital.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
@Tag(name = "Patients", description = "Patient management APIs")
@SecurityRequirement(name = "bearerAuth")
public class PatientController {

    private final PatientService patientService;

    @PostMapping
    @Operation(summary = "Register a patient profile")
    public ResponseEntity<ApiResponse<PatientResponse>> create(@Valid @RequestBody PatientRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Patient registered", patientService.create(request)));
    }

    @GetMapping
    @Operation(summary = "Get all patients", description = "Search by name or MRN. Paginated.")
    public ResponseEntity<ApiResponse<Page<PatientResponse>>> getAll(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.success(patientService.getAll(search, page, size)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get patient by ID")
    public ResponseEntity<ApiResponse<PatientResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(patientService.getById(id)));
    }

    @GetMapping("/mrn/{mrn}")
    @Operation(summary = "Get patient by medical record number")
    public ResponseEntity<ApiResponse<PatientResponse>> getByMrn(@PathVariable String mrn) {
        return ResponseEntity.ok(ApiResponse.success(patientService.getByMrn(mrn)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update patient information")
    public ResponseEntity<ApiResponse<PatientResponse>> update(@PathVariable Long id, @RequestBody PatientRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Patient updated", patientService.update(id, request)));
    }
}
