package com.hospital.entity;

import com.hospital.entity.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "appointments", indexes = {
    @Index(name = "idx_appt_patient", columnList = "patient_id"),
    @Index(name = "idx_appt_doctor",  columnList = "doctor_id"),
    @Index(name = "idx_appt_date",    columnList = "appointment_date")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Appointment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @Column(name = "appointment_date", nullable = false)
    private LocalDate appointmentDate;

    @Column(name = "appointment_time", nullable = false)
    private LocalTime appointmentTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AppointmentStatus status = AppointmentStatus.SCHEDULED;

    @Column(name = "reason_for_visit", columnDefinition = "TEXT")
    private String reasonForVisit;

    @Column(name = "symptoms", columnDefinition = "TEXT")
    private String symptoms;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "duration_minutes")
    private Integer durationMinutes = 30;

    @Column(name = "amount_billed", precision = 10, scale = 2)
    private BigDecimal amountBilled;

    @Column(name = "payment_status", length = 30)
    private String paymentStatus = "PENDING";

    @OneToOne(mappedBy = "appointment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private MedicalRecord medicalRecord;
}
