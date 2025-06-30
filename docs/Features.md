# Features - SecureVault

## 1. Secure Upload Endpoint

* JWT-based authentication via Keycloak
* Role-based access with `@PreAuthorize`
* Extract user details from JWT for audit and logging
* Strategy and Factory pattern used to support multiple storage backends (local/S3)
* MDC trace logging enabled per request

## 2. File Metadata Persistence

* Stores metadata such as filename, content type, size, uploadedBy, upload timestamp, and checksum
* JPA entity mapped to PostgreSQL
* Metadata used for ownership validation and audit trail

## 3. Secure File Download (Planned/In Progress)

* Authenticated access via JWT
* File ownership validation or admin role required
* Option to return either pre-signed S3 URL or streamed file
* Download events will be logged with traceId and user info

## 4. File Integrity Check (Checksum)

* SHA-256 checksum computed on file upload
* Stored in file metadata table
* Implemented using Decorator Pattern around the upload strategy
* Enabled conditionally via `upload.checksum.enabled=true`
* Logged to audit trail and optionally returned in download response

## 5. Pluggable File Storage (Strategy Pattern)

* Interface-based file upload strategy
* Concrete implementations: `LocalFileUploadStrategy`, `S3FileUploadStrategy`
* Factory pattern dynamically resolves the strategy at runtime
* Decorators like `CheckSumDecorator` can wrap any strategy

## 6. Logging & Observability

* Logback configured with file and console appenders
* Rolling policy based on time and size
* MDC used to inject contextual data like traceId and username into logs
* Custom filter adds a traceId per request (UUID or propagated header)
* Logging interceptor captures request metadata
* AOP-based logging planned for service layer

## 7. Exception Handling

* Centralized exception management using `@ControllerAdvice`
* Custom `ApiError` DTO used to format error responses (uses Builder pattern)
* All exceptions include traceId and appropriate HTTP status

## 8. Security Architecture

* Spring Boot configured as an OAuth2 Resource Server
* Keycloak used as the Identity Provider
* Custom `JwtAuthenticationConverter` and `RealmRoleConverter` extract roles from JWT
* Access to endpoints controlled using `@PreAuthorize` and role-based logic

## 9. Environment Profiles & Conditional Beans

* Separate security and logging filters for `dev` and `prod` profiles
* Conditional bean registration via `@Profile`
* Logging verbosity controlled via active profile
* Planned: conditional `SecurityFilterChain` per environment

## 10. Audit Logging (Planned)

* Upload/download actions will be logged with timestamp, username, filename, and traceId
* Log entries will be saved in a dedicated audit table or audit-specific log file
* Option to forward audit logs to S3 in production environments

## 11. Terraform Infrastructure as Code (Planned)

* Modular Terraform scripts for provisioning:

    * S3 buckets
    * IAM roles and policies
    * AWS Secrets Manager entries
* Enables reproducible and secure AWS infrastructure setup

## 12. Swagger/OpenAPI Documentation (Planned)

* Annotate controllers and DTOs with Swagger/OpenAPI annotations
* Auto-generate interactive API docs
* Document security schemes with JWT bearer authentication
