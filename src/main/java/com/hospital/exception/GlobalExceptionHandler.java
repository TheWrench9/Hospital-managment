package com.hospital.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private Map<String, Object> buildError(HttpStatus status, String message, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        body.put("path", request.getDescription(false).replace("uri=", ""));
        return body;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException ex, WebRequest req) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(buildError(HttpStatus.NOT_FOUND, ex.getMessage(), req));
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicate(DuplicateResourceException ex, WebRequest req) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(buildError(HttpStatus.CONFLICT, ex.getMessage(), req));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest(BadRequestException ex, WebRequest req) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildError(HttpStatus.BAD_REQUEST, ex.getMessage(), req));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex, WebRequest req) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            errors.put(fieldName, error.getDefaultMessage());
        });
        Map<String, Object> body = buildError(HttpStatus.BAD_REQUEST, "Validation failed", req);
        body.put("errors", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDenied(AccessDeniedException ex, WebRequest req) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(buildError(HttpStatus.FORBIDDEN, "Access denied", req));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, Object>> handleAuth(AuthenticationException ex, WebRequest req) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(buildError(HttpStatus.UNAUTHORIZED, ex.getMessage(), req));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleAll(Exception ex, WebRequest req) {
        log.error("Unhandled exception: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildError(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", req));
    }
}
