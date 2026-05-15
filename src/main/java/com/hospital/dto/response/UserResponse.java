package com.hospital.dto.response;
import com.hospital.entity.enums.*;
import lombok.*;
import java.time.*;
@Data @Builder
public class UserResponse {
    private Long id;
    private String firstName, lastName, email, phone, profilePictureUrl;
    private LocalDate dateOfBirth;
    private Gender gender;
    private Role role;
    private Boolean isActive, emailVerified;
    private LocalDateTime createdAt;
}
