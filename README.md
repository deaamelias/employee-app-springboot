
---

# Employee App (Spring Boot 3.3.4)

This project contains a Spring Boot application with:

* REST API for Employee CRUD under `/api/employees`
* Thymeleaf UI at `/employees` using DataTables
* PostgreSQL + Flyway migrations
* Sample PL/pgSQL functions in migrations

## Quick start

1. **Install and start PostgreSQL locally**
   Pastikan PostgreSQL sudah terinstall dan berjalan di mesin lokal kamu.
   Buat database baru sesuai konfigurasi di `application.yml` (default: `employee_db`).

2. **Update database config** (optional)
   Sesuaikan konfigurasi koneksi database di `src/main/resources/application.yml` jika perlu:

   ```yaml
   spring:
     datasource:
       url: jdbc:postgresql://localhost:5432/employee_db
       username: your_username
       password: your_password
   ```

3. **Build and run application**

   ```bash
   mvn clean spring-boot:run
   ```

4. **Access UI**

    * REST API base URL: `http://localhost:8080/api/employees`
    * UI at: `http://localhost:8080/employees`

---
