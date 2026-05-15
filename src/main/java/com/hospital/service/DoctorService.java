package com.hospital.service;

import com.hospital.dto.request.DoctorRequest;
import com.hospital.dto.response.DoctorResponse;
import com.hospital.entity.Department;
import com.hospital.entity.Doctor;
import com.hospital.entity.User;
import com.hospital.exception.*;
import com.hospital.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;

    @Transactional
    public DoctorResponse create(DoctorRequest req) {
        if (doctorRepository.existsByLicenseNumber(req.getLicenseNumber()))
            throw new DuplicateResourceException("License number already exists: " + req.getLicenseNumber());

        User user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", req.getUserId()));
        Department dept = departmentRepository.findById(req.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department", req.getDepartmentId()));

        Doctor doctor = Doctor.builder()
                .user(user).department(dept)
                .specialization(req.getSpecialization())
                .licenseNumber(req.getLicenseNumber())
                .yearsOfExperience(req.getYearsOfExperience())
                .consultationFee(req.getConsultationFee())
                .bio(req.getBio())
                .availableDays(req.getAvailableDays())
                .availableFrom(req.getAvailableFrom())
                .availableTo(req.getAvailableTo())
                .build();

        return mapToResponse(doctorRepository.save(doctor));
    }

    public Page<DoctorResponse> getAll(String specialization, int page, int size) {
        return doctorRepository.findBySpecializationContaining(
                specialization, PageRequest.of(page, size)).map(this::mapToResponse);
    }

    public DoctorResponse getById(Long id) {
        return mapToResponse(doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", id)));
    }

    public List<DoctorResponse> getByDepartment(Long departmentId) {
        return doctorRepository.findByDepartmentId(departmentId).stream()
                .map(this::mapToResponse).toList();
    }

    @Transactional
    public DoctorResponse update(Long id, DoctorRequest req) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", id));

        if (req.getDepartmentId() != null) {
            Department dept = departmentRepository.findById(req.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department", req.getDepartmentId()));
            doctor.setDepartment(dept);
        }
        if (req.getSpecialization() != null) doctor.setSpecialization(req.getSpecialization());
        if (req.getConsultationFee() != null) doctor.setConsultationFee(req.getConsultationFee());
        if (req.getBio() != null) doctor.setBio(req.getBio());
        if (req.getAvailableDays() != null) doctor.setAvailableDays(req.getAvailableDays());
        if (req.getAvailableFrom() != null) doctor.setAvailableFrom(req.getAvailableFrom());
        if (req.getAvailableTo() != null) doctor.setAvailableTo(req.getAvailableTo());

        return mapToResponse(doctorRepository.save(doctor));
    }

    @Transactional
    public void delete(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", id));
        doctor.setIsDeleted(true);
        doctorRepository.save(doctor);
    }

    private DoctorResponse mapToResponse(Doctor d) {
        return DoctorResponse.builder()
                .id(d.getId())
                .userId(d.getUser().getId())
                .fullName(d.getUser().getFullName())
                .email(d.getUser().getEmail())
                .phone(d.getUser().getPhone())
                .profilePictureUrl(d.getUser().getProfilePictureUrl())
                .specialization(d.getSpecialization())
                .licenseNumber(d.getLicenseNumber())
                .yearsOfExperience(d.getYearsOfExperience())
                .consultationFee(d.getConsultationFee())
                .bio(d.getBio())
                .departmentId(d.getDepartment().getId())
                .departmentName(d.getDepartment().getName())
                .availableDays(d.getAvailableDays())
                .availableFrom(d.getAvailableFrom())
                .availableTo(d.getAvailableTo())
                .build();
    }
}
