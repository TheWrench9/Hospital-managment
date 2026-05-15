package com.hospital;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(
        title = "Hospital Management System API",
        version = "1.0.0",
        description = "Complete REST API for Hospital Management — Patients, Doctors, Appointments, Medical Records, Prescriptions & Billing",
        contact = @Contact(name = "HMS Team", email = "admin@hospital.com"),
        license = @License(name = "MIT License")
    )
)
public class HospitalManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(HospitalManagementApplication.class, args);
    }
}
