# Laptop Management System - Backend

**Student:** Iyarwema Nsanza Jimmy
**Student ID:** 27118
**Project:** Laptop Management System
**Course:** INSY 8322 Web Technology and Internet, Adventist University of Central Africa (AUCA)

A Spring Boot REST API that tracks the laptops a university lends to its students. A student requests a loan that a custodian issues; a loan ends RETURNED or REFUSED, and condition reports are written for returned laptops.

## Technologies

Java 21, Spring Boot 4, Spring Web, Spring Data JPA (Hibernate), PostgreSQL, Maven.

## Entities

Brand, StoreRoom, Custodian, Student, Loan, ConditionReport, and the enum LoanStatus (REQUESTED, ISSUED, RETURNED, REFUSED).

## Steps to run the API

1. Install Java 21 and PostgreSQL, and make sure the `JAVA_HOME` environment variable points to the JDK 21 folder.
2. In pgAdmin, create an empty database named `laptop_system_db`.
3. Open `src/main/resources/application.properties` and set `spring.datasource.password` to your PostgreSQL password (the default user is `postgres`).
4. From the project folder, run:
   - Windows: `.\mvnw spring-boot:run`
   - Linux / macOS: `./mvnw spring-boot:run`
5. The API starts on `http://localhost:8080`. Hibernate creates the tables automatically (`spring.jpa.hibernate.ddl-auto=update`).
6. Enter the seed data through the save endpoints, in this order: brands, store rooms, students, custodians, loans.
7. Import `laptop-management-system-midterm.postman_collection.json` into Postman to test the requirements. Each request is named by its requirement ID.

## Endpoints

| Area | Endpoints |
|---|---|
| CRUD (A2) | `/api/brands`, `/api/store-rooms`, `/api/custodians`, `/api/students`, `/api/loans`: `POST /save`, `GET /all`, `GET /{id}`, `PUT /update/{id}`, `DELETE /delete/{id}` |
| Derived queries (B) | `GET /api/students/by-faculty`, `GET /api/loans/by-status`, `GET /api/loans/between` |
| JPQL (C) | `GET /api/custodians/by-brand`, `GET /api/store-rooms/free`, `GET /api/brands/unused`, `GET /api/students/of-custodian/{custodianId}` |
| Business rules (D) | `POST /api/loans/save`, `PATCH /api/loans/{id}/status`, `PUT /api/custodians/{custodianId}/brands/{brandId}`, `PATCH /api/custodians/{custodianId}/store-room` |
| Condition reports (E) | `POST /api/condition-reports/save?loanId=`, `GET /api/condition-reports/student/{studentNumber}`, `DELETE /api/condition-reports/delete/{id}` |
| Reports and bulk update (F) | `GET /api/loans/stats/by-status`, `PATCH /api/loans/cancel-day` |
