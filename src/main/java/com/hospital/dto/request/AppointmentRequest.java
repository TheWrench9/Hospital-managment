package com.hospital.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AppointmentRequest {
    @NotNull
    private Long patientId;
    @NotNull
    private Long doctorId;
    @NotNull @Future
    private LocalDate appointmentDate;
    @NotNull
    private LocalTime appointmentTime;
    private String reasonForVisit;
    private String symptoms;
    private Integer durationMinutes = 30;
}
