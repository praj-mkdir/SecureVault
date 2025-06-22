# Design Patterns used in secureVault

## 1. Strategy Pattern (Behaviroal Pattern)
    This pattern enables an object to choose from multiple algorithms and behaviors at runtime, rather than statically choosing a single one.
**Context**:
-  what if you want to support different upload environments ? like Local for development, s3 or Azure blob storage without changing the business logic

**Solution**:
- Created a `FileUploadStrategy` interface
- Implemented two strategies:
    - `S3FileUploadStrategy` (default for production)
      - `LocalFileUploadStrategy` (used in local/dev profile)
        - Used a central `UploadStrategyFactory` to dynamically select strategy based on config or request

                            +----------------------+
                            |  FileUploadStrategy  | <--- Interface (Strategy)
                            +----------------------+
                                ▲             ▲
                +-------------------+             +---------------------+
                | S3FileUploadStrategy            | LocalFileUploadStrategy
                +--------------------+            +---------------------+
                                            ▲
                            +----------------------+
                            |  FileUploadService   |  <-- uses strategy
                            +----------------------+
        **Benefits**:
- Easily extendable to add Google Cloud, Azure, or encrypted S3
- Clean separation of business logic from storage mechanism
- Supports test mocks or in-memory storage strategies

## 2. Builder Pattern (Creational Pattern)
 This pattern is used to construct complex objects step-by-step. 

**Context**:
- What if an API error response object has optional fields like traceId or path, but also needs certain required fields like status and message?
- Direct constructors become messy or verbose when objects have multiple optional parameters.

**Solution**:

- Used a Builder inner static class in ApiErrorResponse
- The builder provides a fluent API to set required and optional fields
- The actual constructor is private, enforcing use of the builder

`ApiErrorResponse errorResponse = new ApiErrorResponse.Builder()
.status(404)
.message("Resource not found")
.timestamp(Instant.now())
.traceId("abc123")
.path("/api/resource")
 .build();`
