package com.hospital.entity;

import com.hospital.entity.enums.Gender;
import com.hospital.entity.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "users", indexes = {
    @Index(name = "idx_user_email", columnList = "email"),
    @Index(name = "idx_user_role",  columnList = "role")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User extends BaseEntity {

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "profile_picture_url")
    private String profilePictureUrl;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "email_verified", nullable = false)
    private Boolean emailVerified = false;

    @Column(name = "verification_token")
    private String verificationToken;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Doctor doctor;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Patient patient;

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
