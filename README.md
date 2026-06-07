# Clinic Management System

A production-ready backend application for managing clinic operations, built with Spring Boot 3, Java 21, PostgreSQL, and Flyway.

## Features

* RESTful API architecture
* Doctor Management APIs
* Request validation using Bean Validation
* Global exception handling
* PostgreSQL database integration
* Flyway database migrations
* JPA/Hibernate ORM
* DTO-based API design
* MapStruct for object mapping
* OpenAPI/Swagger documentation
* Spring Boot Actuator monitoring
* Optimistic locking support
* Auditing support (Created Date, Updated Date)
* Clean layered architecture

---

## Tech Stack

| Technology        | Version |
| ----------------- | ------- |
| Java              | 21      |
| Spring Boot       | 3.5.14  |
| PostgreSQL        | 17      |
| MapStruct         | 1.6.3   |
| OpenAPI / Swagger | 2.8.16  |

---

## Getting Started

### Prerequisites

* Java 21
* Maven 3.9.16
* PostgreSQL 17

### Clone Repository

```bash
git clone https://github.com/Anupamlugun/clinic-management-system.git

cd clinic-management-system
```

### Create Database

```sql
CREATE DATABASE clinic;
```

### Configure Database

Update `application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/clinic
    username: postgres
    password: postgres
```

### Run Application

```bash
mvn clean install

mvn spring-boot:run
```

Application will start at:

```text
http://localhost:8080
```

---

## API Documentation

Swagger UI:

```text
http://localhost:8080/api/swagger-ui/index.html
```

OpenAPI JSON:

```text
http://localhost:8080/api/v3/api-docs/v1
```

---

## Database Migration

Flyway migration files are located in:

```text
src/main/resources/db/migration
```

Example:

```text
V1__create_doctors_table.sql
V2__add_indexes.sql
```

Migrations execute automatically during application startup.

---

## Sample Doctor API

### Create Doctor

```http
POST /api/v1/doctors
```

Request:

```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "phoneNumber": "9876543210",
  "specialization": "Cardiology",
  "experienceYears": 10
}
```

---

## Monitoring

Spring Boot Actuator endpoints:

```text
/actuator/health
/actuator/info
/actuator/metrics
```

---

## Future Enhancements

* Spring Security
* JWT Authentication
* Role Based Access Control (RBAC)
* Patient Management
* Appointment Management
* Prescription Management
* Docker Support
* CI/CD Pipeline
* Kubernetes Deployment
* Redis Caching
* Audit Logs

---

## Development Principles

* Clean Code
* SOLID Principles
* Layered Architecture
* DTO Pattern
* Centralized Exception Handling
* Database Versioning with Flyway
* Optimistic Locking
* API Documentation First

---

## Author

**Anupam Lugun**

Full Stack Java Developer

---

## License

This project is developed for learning, portfolio, and enterprise application development purposes.
