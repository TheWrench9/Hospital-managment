package com.hospital.dto.request;

import com.hospital.entity.enums.BloodGroup;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PatientRequest {
    @NotNull
    private Long userId;
    private BloodGroup bloodGroup;
    private Double heightCm;
    private Double weightKg;
    private String allergies;
    private String chronicConditions;
    private String emergencyContactName;
    private String emergencyContactPhone;
    private String emergencyContactRelation;
    private String insuranceProvider;
    private String insurancePolicyNumber;
    private String address;
}
