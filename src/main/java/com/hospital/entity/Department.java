package com.hospital.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "departments")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Department extends BaseEntity {

    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "location", length = 100)
    private String location;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "head_doctor_id")
    private Long headDoctorId;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private List<Doctor> doctors = new ArrayList<>();
}
