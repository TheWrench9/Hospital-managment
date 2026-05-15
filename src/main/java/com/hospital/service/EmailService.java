package com.hospital.service;

import com.hospital.entity.Appointment;
import com.hospital.entity.Invoice;
import com.hospital.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {

    public void sendWelcomeEmail(User user) {
        log.info("Email disabled - skipping welcome email to {}", user.getEmail());
    }

    public void sendAppointmentConfirmation(Appointment appointment) {
        log.info("Email disabled - skipping appointment confirmation");
    }

    public void sendPasswordResetEmail(User user, String token) {
        log.info("Email disabled - skipping password reset email to {}", user.getEmail());
    }

    public void sendInvoiceEmail(Invoice invoice) {
        log.info("Email disabled - skipping invoice email");
    }
}
