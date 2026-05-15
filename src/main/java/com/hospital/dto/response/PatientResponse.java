package com.hospital.dto.response;
import com.hospital.entity.enums.*;
import lombok.*;
import java.time.LocalDate;
@Data @Builder
public class PatientResponse {
    private Long id, userId;
    private String fullName, email, phone, medicalRecordNumber;
    private LocalDate dateOfBirth;
    private Gender gender;
    private BloodGroup bloodGroup;
    private Double heightCm, weightKg;
    private String allergies, chronicConditions, emergencyContactName;
    private String emergencyContactPhone, insuranceProvider, address;
}
