package com.hospital.service;

import com.hospital.dto.request.PatientRequest;
import com.hospital.dto.response.PatientResponse;
import com.hospital.entity.Patient;
import com.hospital.entity.User;
import com.hospital.exception.*;
import com.hospital.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final UserRepository userRepository;

    @Transactional
    public PatientResponse create(PatientRequest req) {
        User user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", req.getUserId()));

        String mrn = "MRN-" + System.currentTimeMillis();

        Patient patient = Patient.builder()
                .user(user)
                .medicalRecordNumber(mrn)
                .bloodGroup(req.getBloodGroup())
                .heightCm(req.getHeightCm())
                .weightKg(req.getWeightKg())
                .allergies(req.getAllergies())
                .chronicConditions(req.getChronicConditions())
                .emergencyContactName(req.getEmergencyContactName())
                .emergencyContactPhone(req.getEmergencyContactPhone())
                .emergencyContactRelation(req.getEmergencyContactRelation())
                .insuranceProvider(req.getInsuranceProvider())
                .insurancePolicyNumber(req.getInsurancePolicyNumber())
                .address(req.getAddress())
                .build();

        return mapToResponse(patientRepository.save(patient));
    }

    public Page<PatientResponse> getAll(String search, int page, int size) {
        return patientRepository.searchPatients(search, PageRequest.of(page, size))
                .map(this::mapToResponse);
    }

    public PatientResponse getById(Long id) {
        return mapToResponse(patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", id)));
    }

    public PatientResponse getByMrn(String mrn) {
        return mapToResponse(patientRepository.findByMedicalRecordNumber(mrn)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with MRN: " + mrn)));
    }

    @Transactional
    public PatientResponse update(Long id, PatientRequest req) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", id));

        if (req.getBloodGroup() != null) patient.setBloodGroup(req.getBloodGroup());
        if (req.getHeightCm() != null) patient.setHeightCm(req.getHeightCm());
        if (req.getWeightKg() != null) patient.setWeightKg(req.getWeightKg());
        if (req.getAllergies() != null) patient.setAllergies(req.getAllergies());
        if (req.getChronicConditions() != null) patient.setChronicConditions(req.getChronicConditions());
        if (req.getEmergencyContactName() != null) patient.setEmergencyContactName(req.getEmergencyContactName());
        if (req.getEmergencyContactPhone() != null) patient.setEmergencyContactPhone(req.getEmergencyContactPhone());
        if (req.getInsuranceProvider() != null) patient.setInsuranceProvider(req.getInsuranceProvider());
        if (req.getAddress() != null) patient.setAddress(req.getAddress());

        return mapToResponse(patientRepository.save(patient));
    }

    private PatientResponse mapToResponse(Patient p) {
        return PatientResponse.builder()
                .id(p.getId())
                .userId(p.getUser().getId())
                .fullName(p.getUser().getFullName())
                .email(p.getUser().getEmail())
                .phone(p.getUser().getPhone())
                .dateOfBirth(p.getUser().getDateOfBirth())
                .gender(p.getUser().getGender())
                .medicalRecordNumber(p.getMedicalRecordNumber())
                .bloodGroup(p.getBloodGroup())
                .heightCm(p.getHeightCm())
                .weightKg(p.getWeightKg())
                .allergies(p.getAllergies())
                .chronicConditions(p.getChronicConditions())
                .emergencyContactName(p.getEmergencyContactName())
                .emergencyContactPhone(p.getEmergencyContactPhone())
                .insuranceProvider(p.getInsuranceProvider())
                .address(p.getAddress())
                .build();
    }
}
