package com.hospital.dto.response;

import com.hospital.entity.enums.AppointmentStatus;
import lombok.*;
import java.math.BigDecimal;
import java.time.*;

@Data @Builder
public class AppointmentResponse {
    private Long id;
    private Long patientId;
    private Long doctorId;
    private String patientName;
    private String doctorName;
    private String doctorSpecialization;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private AppointmentStatus status;
    private String reasonForVisit;
    private String symptoms;
    private String notes;
    private Integer durationMinutes;
    private BigDecimal amountBilled;
    private String paymentStatus;
    private LocalDateTime createdAt;
}
