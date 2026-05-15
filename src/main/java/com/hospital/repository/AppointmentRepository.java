package com.hospital.repository;

import com.hospital.entity.Appointment;
import com.hospital.entity.enums.AppointmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Page<Appointment> findByPatientId(Long patientId, Pageable pageable);
    Page<Appointment> findByDoctorId(Long doctorId, Pageable pageable);
    List<Appointment> findByDoctorIdAndAppointmentDate(Long doctorId, LocalDate date);
    List<Appointment> findByPatientIdAndStatus(Long patientId, AppointmentStatus status);
}
