package com.hospital.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "doctors", indexes = {
    @Index(name = "idx_doctor_license", columnList = "license_number"),
    @Index(name = "idx_doctor_dept",    columnList = "department_id")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Doctor extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(name = "specialization", nullable = false, length = 150)
    private String specialization;

    @Column(name = "license_number", nullable = false, unique = true, length = 100)
    private String licenseNumber;

    @Column(name = "years_of_experience")
    private Integer yearsOfExperience;

    @Column(name = "consultation_fee", precision = 10, scale = 2)
    private BigDecimal consultationFee;

    @Column(name = "bio", columnDefinition = "TEXT")
    private String bio;

    @Column(name = "available_days", length = 100)
    private String availableDays;

    @Column(name = "available_from", length = 10)
    private String availableFrom;

    @Column(name = "available_to", length = 10)
    private String availableTo;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Appointment> appointments = new ArrayList<>();
}
