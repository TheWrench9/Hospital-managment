package com.hospital.dto.response;

import lombok.*;
import java.time.LocalDateTime;

@Data @Builder
public class MedicalRecordResponse {
    private Long id;
    private Long appointmentId;
    private Long patientId;
    private Long doctorId;
    private String patientName;
    private String doctorName;
    private String diagnosis;
    private String treatmentPlan;
    private String prescription;
    private String vitalSigns;
    private String labResults;
    private String followUpInstructions;
    private String attachments;
    private LocalDateTime createdAt;
}
