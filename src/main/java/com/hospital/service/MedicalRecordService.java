package com.hospital.service;

import com.hospital.dto.request.MedicalRecordRequest;
import com.hospital.dto.response.MedicalRecordResponse;
import com.hospital.entity.Appointment;
import com.hospital.entity.Doctor;
import com.hospital.entity.MedicalRecord;
import com.hospital.entity.Patient;
import com.hospital.exception.ResourceNotFoundException;
import com.hospital.repository.AppointmentRepository;
import com.hospital.repository.DoctorRepository;
import com.hospital.repository.MedicalRecordRepository;
import com.hospital.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    @Transactional
    public MedicalRecordResponse create(MedicalRecordRequest req) {
        Appointment appointment = appointmentRepository.findById(req.getAppointmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", req.getAppointmentId()));
        Patient patient = patientRepository.findById(req.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient", req.getPatientId()));
        Doctor doctor = doctorRepository.findById(req.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", req.getDoctorId()));

        MedicalRecord record = MedicalRecord.builder()
                .appointment(appointment)
                .patient(patient)
                .doctor(doctor)
                .diagnosis(req.getDiagnosis())
                .treatmentPlan(req.getTreatmentPlan())
                .prescription(req.getPrescription())
                .vitalSigns(req.getVitalSigns())
                .labResults(req.getLabResults())
                .followUpInstructions(req.getFollowUpInstructions())
                .attachments(req.getAttachments())
                .build();

        return mapToResponse(medicalRecordRepository.save(record));
    }

    public Page<MedicalRecordResponse> getByPatient(Long patientId, int page, int size) {
        return medicalRecordRepository.findByPatientId(patientId, PageRequest.of(page, size))
                .map(this::mapToResponse);
    }

    public MedicalRecordResponse getById(Long id) {
        return mapToResponse(medicalRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MedicalRecord", id)));
    }

    public MedicalRecordResponse getByAppointment(Long appointmentId) {
        return mapToResponse(medicalRecordRepository.findByAppointmentId(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("MedicalRecord for appointment", appointmentId)));
    }

    @Transactional
    public MedicalRecordResponse update(Long id, MedicalRecordRequest req) {
        MedicalRecord record = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MedicalRecord", id));
        if (req.getDiagnosis() != null) record.setDiagnosis(req.getDiagnosis());
        if (req.getTreatmentPlan() != null) record.setTreatmentPlan(req.getTreatmentPlan());
        if (req.getPrescription() != null) record.setPrescription(req.getPrescription());
        if (req.getVitalSigns() != null) record.setVitalSigns(req.getVitalSigns());
        if (req.getLabResults() != null) record.setLabResults(req.getLabResults());
        if (req.getFollowUpInstructions() != null) record.setFollowUpInstructions(req.getFollowUpInstructions());
        if (req.getAttachments() != null) record.setAttachments(req.getAttachments());
        return mapToResponse(medicalRecordRepository.save(record));
    }

    private MedicalRecordResponse mapToResponse(MedicalRecord r) {
        return MedicalRecordResponse.builder()
                .id(r.getId())
                .appointmentId(r.getAppointment() != null ? r.getAppointment().getId() : null)
                .patientId(r.getPatient().getId())
                .doctorId(r.getDoctor().getId())
                .patientName(r.getPatient().getUser().getFullName())
                .doctorName(r.getDoctor().getUser().getFullName())
                .diagnosis(r.getDiagnosis())
                .treatmentPlan(r.getTreatmentPlan())
                .prescription(r.getPrescription())
                .vitalSigns(r.getVitalSigns())
                .labResults(r.getLabResults())
                .followUpInstructions(r.getFollowUpInstructions())
                .attachments(r.getAttachments())
                .createdAt(r.getCreatedAt())
                .build();
    }
}
