package com.hospital.dto.response;
import lombok.*;
import java.math.BigDecimal;
@Data @Builder
public class DoctorResponse {
    private Long id, userId, departmentId;
    private String fullName, email, phone, profilePictureUrl;
    private String specialization, licenseNumber, bio, departmentName;
    private String availableDays, availableFrom, availableTo;
    private Integer yearsOfExperience;
    private BigDecimal consultationFee;
}
