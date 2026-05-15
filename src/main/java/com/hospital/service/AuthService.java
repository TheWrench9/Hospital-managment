package com.hospital.service;

import com.hospital.dto.request.LoginRequest;
import com.hospital.dto.request.RegisterRequest;
import com.hospital.dto.response.AuthResponse;
import com.hospital.dto.response.UserResponse;
import com.hospital.entity.User;
import com.hospital.entity.enums.Role;
import com.hospital.exception.BadRequestException;
import com.hospital.exception.DuplicateResourceException;
import com.hospital.repository.UserRepository;
import com.hospital.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail()))
            throw new DuplicateResourceException("Email already registered: " + request.getEmail());

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .dateOfBirth(request.getDateOfBirth())
                .gender(request.getGender())
                .role(request.getRole() != null ? request.getRole() : Role.PATIENT)
                .verificationToken(UUID.randomUUID().toString())
                .isActive(true)
                .emailVerified(false)
                .build();

        user = userRepository.save(user);
        emailService.sendWelcomeEmail(user);

        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail()).password(user.getPassword())
                .authorities("ROLE_" + user.getRole()).build();

        return AuthResponse.builder()
                .accessToken(jwtTokenProvider.generateToken(userDetails))
                .refreshToken(jwtTokenProvider.generateRefreshToken(userDetails))
                .expiresIn(86400000L)
                .user(mapUserToResponse(user))
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("Invalid credentials"));

        if (!user.getIsActive())
            throw new BadRequestException("Account is deactivated. Please contact support.");

        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail()).password(user.getPassword())
                .authorities("ROLE_" + user.getRole()).build();

        return AuthResponse.builder()
                .accessToken(jwtTokenProvider.generateToken(userDetails))
                .refreshToken(jwtTokenProvider.generateRefreshToken(userDetails))
                .expiresIn(86400000L)
                .user(mapUserToResponse(user))
                .build();
    }

    private UserResponse mapUserToResponse(User u) {
        return UserResponse.builder()
                .id(u.getId()).firstName(u.getFirstName()).lastName(u.getLastName())
                .email(u.getEmail()).phone(u.getPhone()).dateOfBirth(u.getDateOfBirth())
                .gender(u.getGender()).role(u.getRole())
                .profilePictureUrl(u.getProfilePictureUrl())
                .isActive(u.getIsActive()).emailVerified(u.getEmailVerified())
                .createdAt(u.getCreatedAt()).build();
    }
}
