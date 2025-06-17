# Overview 
A backend platform for secure file sharing with JWT/OAuth2 Based authentication via keycloak, file storage on AWS S3, Antivirus scanning, Structured logging and production grade principles

## 🧱  Architecture Overview
- Layered Architecture
- Exception Handling
- Configurable YAML-based setup(Profile based)

### Components
- Client: Postman or any application that take request jwt token from Keycloak
- Authorization server: Keycloak
- Resource Server: Springboot backend, with spring security 
- Object Storage : AWS Simple storage service (S3)
- Database: Postgresql - Storing the metadata
- Virus scanner (Planned) : ClamAV via EC2 or ecs or lambda -- future scope 


