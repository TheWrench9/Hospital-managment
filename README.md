# 🏥 Hospital Management System — Spring Boot REST API

A **production-grade** REST API backend for managing hospital operations. Built with Spring Boot 3, MySQL, JWT Authentication, Swagger UI, File Upload, and Email notifications.

---

## 🚀 Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java 17 |
| Framework | Spring Boot 3.2.3 |
| Database | MySQL 8+ |
| Security | Spring Security + JWT (JJWT 0.12.5) |
| API Docs | Swagger / SpringDoc OpenAPI 2.3 |
| Email | Spring Mail (Gmail SMTP) |
| File Upload | Multipart — local storage |
| ORM | Spring Data JPA + Hibernate |
| Build | Maven |

---

## 📊 Database Schema (11 Tables)

```
users ──────────────┬── doctors ──── departments
                    │       │
                    └── patients ────┐
                                     ├── appointments ──── medical_records ──── prescriptions ──── prescription_items
                                     │                          │
                                     ├── invoices ──── invoice_items           documents
                                     │
                                     └── documents
```

### Tables & Relationships:
- **users** → base for all system users (PK: id)
- **departments** → hospital departments (PK: id)
- **doctors** → FK: user_id, department_id
- **patients** → FK: user_id; unique medical_record_number
- **appointments** → FK: patient_id, doctor_id
- **medical_records** → FK: patient_id, doctor_id, appointment_id
- **prescriptions** → FK: medical_record_id, doctor_id, patient_id
- **prescription_items** → FK: prescription_id
- **invoices** → FK: patient_id, appointment_id
- **invoice_items** → FK: invoice_id
- **documents** → FK: medical_record_id, uploaded_by (user_id)

---

## ⚙️ Setup & Run

### 1. Prerequisites
- Java 17+
- MySQL 8+
- Maven 3.8+

### 2. Configure Database
Edit `src/main/resources/application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/hospital_db?createDatabaseIfNotExist=true
    username: root
    password: YOUR_PASSWORD
```

### 3. Configure Email (Gmail)
```yaml
spring:
  mail:
    username: your-email@gmail.com
    password: your-app-password   # Use Gmail App Password
```

### 4. Run the SQL Seed (optional)
```bash
mysql -u root -p < src/main/resources/schema.sql
```

### 5. Start the application
```bash
mvn spring-boot:run
```

---

## 🔐 Default Login

```
Email:    admin@hospital.com
Password: Admin@123456
```

---

## 📖 Swagger UI

After starting the app, visit:

**http://localhost:8080/swagger-ui.html**

Steps to test in Swagger:
1. POST `/api/v1/auth/login` with admin credentials
2. Copy the `accessToken` from response
3. Click **Authorize** button (top-right)
4. Paste `<your_token>` and click Authorize
5. All endpoints are now accessible!

---

## 🔗 API Endpoints Summary

### 🔑 Authentication (`/api/v1/auth`)
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/register` | Register new user |
| POST | `/login` | Login → get JWT |

### 🏨 Departments (`/api/v1/departments`)
| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| GET | `/` | Public | List all departments |
| POST | `/` | ADMIN | Create department |
| GET | `/{id}` | Public | Get by ID |
| PUT | `/{id}` | ADMIN | Update |
| DELETE | `/{id}` | ADMIN | Soft-delete |

### 👨‍⚕️ Doctors (`/api/v1/doctors`)
| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| POST | `/` | ADMIN | Register doctor |
| GET | `/` | Any | List + filter by specialization |
| GET | `/{id}` | Any | Get by ID |
| GET | `/department/{id}` | Any | Doctors in dept |
| PUT | `/{id}` | ADMIN/DOCTOR | Update |
| DELETE | `/{id}` | ADMIN | Remove |

### 🧑‍🤝‍🧑 Patients (`/api/v1/patients`)
| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| POST | `/` | Any | Register patient |
| GET | `/` | Staff | Search patients |
| GET | `/{id}` | Staff | Get by ID |
| GET | `/mrn/{mrn}` | Staff | Get by MRN |
| PUT | `/{id}` | Staff | Update |

### 📅 Appointments (`/api/v1/appointments`)
| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| POST | `/` | Auth | Book appointment + email |
| GET | `/` | Auth | All appointments |
| GET | `/{id}` | Auth | Get by ID |
| GET | `/patient/{id}` | Auth | By patient |
| GET | `/doctor/{id}` | Auth | By doctor |
| PUT | `/{id}` | Auth | Update details |
| PATCH | `/{id}/status` | Auth | Change status |
| DELETE | `/{id}/cancel` | Auth | Cancel |

### 💰 Invoices (`/api/v1/invoices`)
| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| POST | `/` | ADMIN/RECEPTIONIST | Create invoice + email |
| GET | `/` | Auth | All invoices |
| GET | `/{id}` | Auth | Get by ID |
| GET | `/patient/{id}` | Auth | Patient invoices |
| POST | `/{id}/pay` | Auth | Process payment |
| GET | `/revenue/total` | ADMIN | Total revenue |

### 📄 Documents (`/api/v1/documents`)
| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| POST | `/upload` | Auth | Upload file (multipart) |
| GET | `/medical-record/{id}` | Auth | Files for a record |
| GET | `/download/{id}` | Auth | Download file |
| DELETE | `/{id}` | Auth | Delete file |

---

## 🎯 User Roles & Permissions

| Role | Access Level |
|------|-------------|
| `ADMIN` | Full access to all endpoints |
| `DOCTOR` | Own profile, appointments, medical records, prescriptions |
| `NURSE` | Patient care endpoints |
| `RECEPTIONIST` | Appointments, invoices, patient registration |
| `PATIENT` | Own profile, own appointments, own records |

---

## 📁 Project Structure

```
src/main/java/com/hospital/
├── HospitalManagementApplication.java
├── config/
│   ├── SecurityConfig.java         # JWT + role-based security
│   ├── SwaggerConfig.java          # OpenAPI bearer token
│   ├── AsyncConfig.java            # Async email executor
│   └── JpaConfig.java              # Auditing
├── controller/
│   ├── AuthController.java
│   ├── DepartmentController.java
│   ├── DoctorController.java
│   ├── PatientController.java
│   ├── AppointmentController.java
│   ├── InvoiceController.java
│   └── DocumentController.java
├── entity/
│   ├── BaseEntity.java             # id, createdAt, updatedAt, isDeleted
│   ├── User.java
│   ├── Department.java
│   ├── Doctor.java
│   ├── Patient.java
│   ├── Appointment.java
│   ├── MedicalRecord.java
│   ├── Prescription.java
│   ├── PrescriptionItem.java
│   ├── Invoice.java
│   ├── InvoiceItem.java
│   ├── Document.java
│   └── enums/
├── repository/                     # 8 JPA repositories
├── service/
│   ├── AuthService.java
│   ├── DoctorService.java
│   ├── PatientService.java
│   ├── AppointmentService.java
│   ├── InvoiceService.java
│   ├── EmailService.java           # Async HTML emails
│   └── FileStorageService.java     # Multi-type file upload
├── security/
│   ├── JwtTokenProvider.java
│   ├── JwtAuthenticationFilter.java
│   └── CustomUserDetailsService.java
├── dto/request/ & dto/response/
└── exception/
    ├── GlobalExceptionHandler.java
    ├── ResourceNotFoundException.java
    ├── DuplicateResourceException.java
    └── BadRequestException.java
