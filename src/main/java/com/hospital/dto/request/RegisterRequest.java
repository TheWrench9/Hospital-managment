package com.hospital.dto.request;

import com.hospital.entity.enums.Gender;
import com.hospital.entity.enums.Role;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class RegisterRequest {
    @NotBlank @Size(min = 2, max = 100)
    private String firstName;
    @NotBlank @Size(min = 2, max = 100)
    private String lastName;
    @NotBlank @Email
    private String email;
    @NotBlank @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
    private String phone;
    private LocalDate dateOfBirth;
    private Gender gender;
    @NotNull
    private Role role;
}
