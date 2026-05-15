package com.hospital.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class DoctorRequest {
    @NotNull
    private Long userId;
    @NotNull
    private Long departmentId;
    @NotBlank
    private String specialization;
    @NotBlank
    private String licenseNumber;
    private Integer yearsOfExperience;
    @DecimalMin("0.0")
    private BigDecimal consultationFee;
    private String bio;
    private String availableDays;
    private String availableFrom;
    private String availableTo;
}
