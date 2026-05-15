package com.hospital.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MedicalRecordRequest {
    @NotNull
    private Long appointmentId;
    @NotNull
    private Long patientId;
    @NotNull
    private Long doctorId;
    @NotBlank
    private String diagnosis;
    private String treatmentPlan;
    private String prescription;
    private String vitalSigns;
    private String labResults;
    private String followUpInstructions;
    private String attachments;
}
