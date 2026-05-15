package com.hospital.service;

import com.hospital.dto.request.AppointmentRequest;
import com.hospital.dto.response.AppointmentResponse;
import com.hospital.entity.Appointment;
import com.hospital.entity.Doctor;
import com.hospital.entity.Patient;
import com.hospital.entity.enums.AppointmentStatus;
import com.hospital.exception.ResourceNotFoundException;
import com.hospital.repository.AppointmentRepository;
import com.hospital.repository.DoctorRepository;
import com.hospital.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    @Transactional
    public AppointmentResponse create(AppointmentRequest req) {
        Patient patient = patientRepository.findById(req.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient", req.getPatientId()));
        Doctor doctor = doctorRepository.findById(req.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", req.getDoctorId()));

        Appointment appointment = Appointment.builder()
                .patient(patient)
                .doctor(doctor)
                .appointmentDate(req.getAppointmentDate())
                .appointmentTime(req.getAppointmentTime())
                .reasonForVisit(req.getReasonForVisit())
                .symptoms(req.getSymptoms())
                .durationMinutes(req.getDurationMinutes() != null ? req.getDurationMinutes() : 30)
                .amountBilled(doctor.getConsultationFee())
                .status(AppointmentStatus.SCHEDULED)
                .paymentStatus("PENDING")
                .build();

        return mapToResponse(appointmentRepository.save(appointment));
    }

    public Page<AppointmentResponse> getAll(int page, int size) {
        return appointmentRepository.findAll(PageRequest.of(page, size)).map(this::mapToResponse);
    }

    public AppointmentResponse getById(Long id) {
        return mapToResponse(appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", id)));
    }

    public Page<AppointmentResponse> getByPatient(Long patientId, int page, int size) {
        return appointmentRepository.findByPatientId(patientId, PageRequest.of(page, size)).map(this::mapToResponse);
    }

    public Page<AppointmentResponse> getByDoctor(Long doctorId, int page, int size) {
        return appointmentRepository.findByDoctorId(doctorId, PageRequest.of(page, size)).map(this::mapToResponse);
    }

    @Transactional
    public AppointmentResponse update(Long id, AppointmentRequest req) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", id));
        if (req.getAppointmentDate() != null) appointment.setAppointmentDate(req.getAppointmentDate());
        if (req.getAppointmentTime() != null) appointment.setAppointmentTime(req.getAppointmentTime());
        if (req.getReasonForVisit() != null) appointment.setReasonForVisit(req.getReasonForVisit());
        if (req.getSymptoms() != null) appointment.setSymptoms(req.getSymptoms());
        if (req.getDurationMinutes() != null) appointment.setDurationMinutes(req.getDurationMinutes());
        return mapToResponse(appointmentRepository.save(appointment));
    }

    @Transactional
    public AppointmentResponse updateStatus(Long id, AppointmentStatus status) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", id));
        appointment.setStatus(status);
        return mapToResponse(appointmentRepository.save(appointment));
    }

    @Transactional
    public void cancel(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", id));
        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointmentRepository.save(appointment);
    }

    private AppointmentResponse mapToResponse(Appointment a) {
        return AppointmentResponse.builder()
                .id(a.getId())
                .patientId(a.getPatient().getId())
                .doctorId(a.getDoctor().getId())
                .patientName(a.getPatient().getUser().getFullName())
                .doctorName(a.getDoctor().getUser().getFullName())
                .doctorSpecialization(a.getDoctor().getSpecialization())
                .appointmentDate(a.getAppointmentDate())
                .appointmentTime(a.getAppointmentTime())
                .status(a.getStatus())
                .reasonForVisit(a.getReasonForVisit())
                .symptoms(a.getSymptoms())
                .notes(a.getNotes())
                .durationMinutes(a.getDurationMinutes())
                .amountBilled(a.getAmountBilled())
                .paymentStatus(a.getPaymentStatus())
                .createdAt(a.getCreatedAt())
                .build();
    }
}
