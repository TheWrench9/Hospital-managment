package com.hospital.controller;

import com.hospital.dto.request.DoctorRequest;
import com.hospital.dto.response.*;
import com.hospital.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/doctors")
@RequiredArgsConstructor
@Tag(name = "Doctors", description = "Doctor management APIs")
@SecurityRequirement(name = "bearerAuth")
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Register a doctor profile")
    public ResponseEntity<ApiResponse<DoctorResponse>> create(@Valid @RequestBody DoctorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Doctor registered", doctorService.create(request)));
    }

    @GetMapping
    @Operation(summary = "Get all doctors", description = "Filter by specialization. Paginated.")
    public ResponseEntity<ApiResponse<Page<DoctorResponse>>> getAll(
            @RequestParam(required = false) String specialization,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.success(doctorService.getAll(specialization, page, size)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get doctor by ID")
    public ResponseEntity<ApiResponse<DoctorResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(doctorService.getById(id)));
    }

    @GetMapping("/department/{departmentId}")
    @Operation(summary = "Get doctors by department")
    public ResponseEntity<ApiResponse<List<DoctorResponse>>> getByDepartment(@PathVariable Long departmentId) {
        return ResponseEntity.ok(ApiResponse.success(doctorService.getByDepartment(departmentId)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    @Operation(summary = "Update doctor information")
    public ResponseEntity<ApiResponse<DoctorResponse>> update(@PathVariable Long id, @RequestBody DoctorRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Doctor updated", doctorService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Soft-delete a doctor")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        doctorService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Doctor removed", null));
    }
}
