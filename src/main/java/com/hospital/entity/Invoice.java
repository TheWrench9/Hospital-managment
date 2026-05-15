package com.hospital.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "invoices")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    private BigDecimal amount;

    private String description;

    private String status; // e.g. PAID, UNPAID, PENDING

    private LocalDateTime issuedAt;

    private LocalDateTime paidAt;
}