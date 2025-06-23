# 🚨 Exception Error Notes

This file contains known issues and fixes encountered during development of the SecureVault application.

---

## 🧾 Error 1: JwtDecoderInitializationException

### cause:
JwtDecoderInitializationException: Failed to lazily resolve the supplied JwtDecoder instance
Caused by: java.net.ConnectException: Connection refused: connect

### fix: 
Spring Boot attempted to retrieve JWT configuration from Keycloak:
http://<Keycloak-server>/realms/secureApp/.well-known/openid-configuration //
But Keycloak server was not running, so connection was refused.




