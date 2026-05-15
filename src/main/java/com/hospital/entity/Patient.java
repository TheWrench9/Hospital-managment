package com.hospital.entity;

import com.hospital.entity.enums.BloodGroup;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patients", indexes = {
    @Index(name = "idx_patient_mrn", columnList = "medical_record_number")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Patient extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "medical_record_number", nullable = false, unique = true, length = 50)
    private String medicalRecordNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "blood_group")
    private BloodGroup bloodGroup;

    @Column(name = "height_cm")
    private Double heightCm;

    @Column(name = "weight_kg")
    private Double weightKg;

    @Column(name = "allergies", columnDefinition = "TEXT")
    private String allergies;

    @Column(name = "chronic_conditions", columnDefinition = "TEXT")
    private String chronicConditions;

    @Column(name = "emergency_contact_name", length = 150)
    private String emergencyContactName;

    @Column(name = "emergency_contact_phone", length = 20)
    private String emergencyContactPhone;

    @Column(name = "emergency_contact_relation", length = 50)
    private String emergencyContactRelation;

    @Column(name = "insurance_provider", length = 150)
    private String insuranceProvider;

    @Column(name = "insurance_policy_number", length = 100)
    private String insurancePolicyNumber;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Appointment> appointments = new ArrayList<>();

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MedicalRecord> medicalRecords = new ArrayList<>();
}
