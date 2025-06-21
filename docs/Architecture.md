# 🏗️ Overview

A backend platform for **secure file sharing**, built with production-grade principles. It uses **JWT/OAuth2-based authentication** via Keycloak, file storage on **AWS S3**, structured logging with traceability, and is designed with modular architecture and clean engineering practices. Features like **antivirus scanning** and **auditing** are planned as part of future scope.

---

## 🧱 Architecture Overview

The system follows a **modular layered architecture** with clear separation of concerns, robust security, and observability.

### Key Characteristics

- **Layered Design**: Follows Controller → Service → Repository structure
- **Modular Domains**: Organized via domain-driven package structure
- **Security**: Spring Security + OAuth2 JWT (Keycloak integration)
- **Exception Handling**: Centralized with `@ControllerAdvice`
- **Logging**: Contextual logging with MDC + Logback (traceId per request)
- **Environment Config**: YAML-based config with profile-specific overrides (`dev`, `prod`, etc.)

---

## 🔧 Core Components

| Component         | Role |
|-------------------|------|
| **Client**        | Postman or frontend app that authenticates via Keycloak and sends JWT with requests |
| **Authorization Server** | [Keycloak](https://www.keycloak.org/) (Dockerized) — issues JWT tokens and manages RBAC |
| **Resource Server** | Spring Boot backend using Spring Security to validate and authorize incoming JWT tokens |
| **Object Storage** | [AWS S3](https://aws.amazon.com/s3/) — used to store uploaded files securely |
| **Relational Database** | PostgreSQL — stores file metadata (filename, size, user, timestamp, etc.) |
| **Virus Scanner (Planned)** | ClamAV — planned for integration via EC2, ECS, or AWS Lambda |
| **Audit Logging (Planned)** | To record all uploads/downloads with user + timestamp details |

---

## 📦 Architecture Diagram (Optional)

> Consider adding a simple block diagram showing:
- Client → Keycloak (for token)
- Client → Backend → S3/DB
- TraceId and Logs flowing to a centralized logging system (future: ELK or Grafana)

---

## 🛡️ Future Enhancements

- **Expiring Pre-signed URLs** for secure file access
- **Audit Log Table** to track file actions
- **Virus scanning** before upload using ClamAV
- **Prometheus + Grafana** for metrics
- **Structured JSON logging** (ELK-ready) 
- **Terraform-based Infra provisioning**
- **API Rate Limiting** via filters or gateway

---

## 📚 Related Docs



