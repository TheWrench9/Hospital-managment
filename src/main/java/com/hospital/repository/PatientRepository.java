package com.hospital.repository;

import com.hospital.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByUserId(Long userId);
    Optional<Patient> findByMedicalRecordNumber(String mrn);
    boolean existsByMedicalRecordNumber(String mrn);

    @Query("SELECT p FROM Patient p JOIN p.user u WHERE p.isDeleted = false AND " +
           "(:search IS NULL OR LOWER(u.firstName) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "OR LOWER(p.medicalRecordNumber) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Patient> searchPatients(String search, Pageable pageable);
}
