package com.hospital.repository;

import com.hospital.entity.MedicalRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    Page<MedicalRecord> findByPatientId(Long patientId, Pageable pageable);
    Page<MedicalRecord> findByDoctorId(Long doctorId, Pageable pageable);
    Optional<MedicalRecord> findByAppointmentId(Long appointmentId);

    @Query("SELECT mr FROM MedicalRecord mr WHERE mr.isDeleted = false AND mr.patient.id = :patientId ORDER BY mr.createdAt DESC")
    Page<MedicalRecord> findActiveByPatientId(@Param("patientId") Long patientId, Pageable pageable);
}
