package com.hospital.config;

import com.hospital.entity.User;
import com.hospital.entity.enums.Gender;
import com.hospital.entity.enums.Role;
import com.hospital.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            User admin = User.builder()
                    .firstName("Admin")
                    .lastName("User")
                    .email("admin@hospital.com")
                    .password(passwordEncoder.encode("Admin@1234"))
                    .role(Role.ADMIN)
                    .gender(Gender.MALE)
                    .isActive(true)
                    .emailVerified(true)
                    .build();
            userRepository.save(admin);
            log.info("========================================");
            log.info("Default admin user created:");
            log.info("  Email:    admin@hospital.com");
            log.info("  Password: Admin@1234");
            log.info("========================================");
        }
    }
}
