# Employee Management System

A secure Employee Management System built with Spring Boot, Spring Security, JWT Authentication, MySQL, Docker, and JUnit.

---

## Features

- Employee CRUD Operations
- JWT Authentication
- Role-Based Authorization (Admin/User)
- Employee Search
- Pagination & Sorting
- Employee Photo Upload
- Global Exception Handling
- Request Validation
- Swagger API Documentation
- Unit Testing (JUnit 5 + Mockito)
- Controller Testing (MockMvc)
- Docker Support
- Docker Compose
- Logging using SLF4J & Logback

---

## Tech Stack

### Backend
- Java 17
- Spring Boot
- Spring MVC
- Spring Security
- Spring Data JPA
- Hibernate

### Database
- MySQL

### Authentication
- JWT

### Testing
- JUnit 5
- Mockito
- MockMvc

### Documentation
- Swagger / OpenAPI

### DevOps
- Docker
- Docker Compose

### Build Tool
- Maven

---

## Project Structure

```
src
├── controller
├── service
├── repository
├── entity
├── dto
├── auth
├── security
├── exception
└── config
```

---

## API Endpoints

### Authentication

| Method | Endpoint |
|----------|---------------------|
| POST | /api/auth/register |
| POST | /api/auth/login |

### Employees

| Method | Endpoint |
|----------|-------------------------------|
| POST | /api/employees |
| GET | /api/employees |
| GET | /api/employees/{id} |
| PUT | /api/employees/{id} |
| DELETE | /api/employees/{id} |
| GET | /api/employees/search |
| POST | /api/employees/{id}/upload-photo |

---

## Swagger

After running the application:

http://localhost:8080/swagger-ui/index.html

---

## Run Locally

Clone the repository

```bash
git clone https://github.com/YOUR_USERNAME/employee_management_system.git
```

Navigate to the project

```bash
cd employee_management_system
```

Run

```bash
mvn spring-boot:run
```

---

## Run with Docker

```bash
docker compose up --build
```

---

## Testing

Run all tests

```bash
mvn test
```

---

## Future Enhancements

- Refresh Token Authentication
- Email Notifications
- Redis Cache
- AWS S3 for Photo Storage
- Kubernetes Deployment

---

## Author

**Hari Sankar Panigrahi**

GitHub:
https://github.com/Harisankar-Panigrahi