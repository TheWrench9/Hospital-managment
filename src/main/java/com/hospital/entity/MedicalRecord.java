package com.hospital.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "medical_records")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MedicalRecord extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @Column(name = "diagnosis", columnDefinition = "TEXT", nullable = false)
    private String diagnosis;

    @Column(name = "treatment_plan", columnDefinition = "TEXT")
    private String treatmentPlan;

    @Column(name = "prescription", columnDefinition = "TEXT")
    private String prescription;

    @Column(name = "vital_signs", columnDefinition = "TEXT")
    private String vitalSigns;

    @Column(name = "lab_results", columnDefinition = "TEXT")
    private String labResults;

    @Column(name = "follow_up_instructions", columnDefinition = "TEXT")
    private String followUpInstructions;

    @Column(name = "attachments", columnDefinition = "TEXT")
    private String attachments;
}
