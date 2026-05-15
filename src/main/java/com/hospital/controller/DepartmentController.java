package com.hospital.controller;

import com.hospital.dto.response.ApiResponse;
import com.hospital.entity.Department;
import com.hospital.exception.*;
import com.hospital.repository.DepartmentRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/departments")
@RequiredArgsConstructor
@Tag(name = "Departments", description = "Hospital department management")
public class DepartmentController {

    private final DepartmentRepository departmentRepository;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Create a department")
    public ResponseEntity<ApiResponse<Department>> create(@RequestBody Department request) {
        if (departmentRepository.existsByName(request.getName()))
            throw new DuplicateResourceException("Department already exists: " + request.getName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Department created", departmentRepository.save(request)));
    }

    @GetMapping
    @Operation(summary = "List all departments (public)")
    public ResponseEntity<ApiResponse<List<Department>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(departmentRepository.findByIsDeletedFalse()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get department by ID (public)")
    public ResponseEntity<ApiResponse<Department>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department", id))));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Update a department")
    public ResponseEntity<ApiResponse<Department>> update(@PathVariable Long id, @RequestBody Department body) {
        Department dept = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department", id));
        if (body.getName() != null) dept.setName(body.getName());
        if (body.getDescription() != null) dept.setDescription(body.getDescription());
        if (body.getLocation() != null) dept.setLocation(body.getLocation());
        if (body.getPhone() != null) dept.setPhone(body.getPhone());
        return ResponseEntity.ok(ApiResponse.success("Updated", departmentRepository.save(dept)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Delete a department")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        Department dept = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department", id));
        dept.setIsDeleted(true);
        departmentRepository.save(dept);
        return ResponseEntity.ok(ApiResponse.success("Deleted", null));
    }
}
