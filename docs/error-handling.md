# Error Handling in SecureVault

## All  in the backend are handled through a global `@RestControllerAdvice`, and return a structured response object (`ApiErrorResponse`) with traceability via `traceId`.


### ApiErrorResponse Structure

| Field     | Type      | Description                       |
|-----------|-----------|-----------------------------------|
| status    | `int`     | HTTP status code                  |
| message   | `String`  | Error message                     |
| timestamp | `Instant` | Time of the error                 |
| traceId   | `String`  | Unique ID for tracing the request |
| path      | `String`  | The requested URI                 |

All responses are built using the **Builder Pattern** for maintainability.