package com.hospital.repository;

import com.hospital.entity.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByUserId(Long userId);
    Optional<Doctor> findByLicenseNumber(String licenseNumber);
    boolean existsByLicenseNumber(String licenseNumber);
    List<Doctor> findByDepartmentId(Long departmentId);

    @Query("SELECT d FROM Doctor d WHERE d.isDeleted = false AND " +
           "(:specialization IS NULL OR LOWER(d.specialization) LIKE LOWER(CONCAT('%', :specialization, '%')))")
    Page<Doctor> findBySpecializationContaining(String specialization, Pageable pageable);
}
