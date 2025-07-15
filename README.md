# Secure File Upload Service


A secure, modular backend service built using Java 20+ and Spring Boot for uploading, downloading, and managing files. Designed with production-grade engineering practices like JWT authentication, contextual logging (MDC), role-based access control, checksum validation, and pluggable storage backends (local or AWS S3).


## Tech Stack
- **Language**: Java 17+
- **Framework**: Spring Boot
- **Auth**: Keycloak (JWT, OAuth2) 
- **Storage**: Local filesystem (dev) & AWS S3 (prod-ready)
- **Database**: H2 (dev), PostgreSQL (prod)
- **Logging**: Logback, MDC (traceId, username)
- **Architecture**: Layered + Strategy/Factory patterns
- **Design Patterns**: Strategy, Factory, Decorator, Builder
- **Docs**: Swagger/OpenAPI (`/swagger-ui.html`)


## Features
- Secure file **upload/download**
- Role-based access via Keycloak (`user`, `admin`) (Optional for Dev)
- Pluggable storage backend using **strategy pattern**
- File **checksum validation** (SHA-256)
- **Metadata** storage in PostgreSQL(Prod) and H2(Dev)
- Global exception handling and audit logging
- Traceable request logs using **MDC**
- Separate config profiles for **dev** and **prod**

## Setup & Run

### 1. Prerequisites
- Java 20+
- Maven
- Docker (Keycloak - Only Prod profile) 

### 2. Clone Repo
- git clone https://github.com/praj-mkdir/SecureVault.git
- cd SecureVault

### 3. Start Keycloak (Optional for Dev and Testing purpose)
- docker-compose up -d

###  Run in Dev Profile (H2 + Local Upload)
- ./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
- Local file upload path: ./uploadedfiles
- H2 console: http://localhost:8081/h2-console

### Swagger UI
- http://localhost:8081/swagger-ui.html


Note: For testing purpose and development purpose all the endpoints are non-restricted.
