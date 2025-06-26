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

## 3. Factory Pattern (Creational Pattern)
This pattern is used to delegate the responsibility of object creation to a factory class that decides which concrete implementation to return based on input.
The Factory pattern helps us decide which strategy to use, based on the input we get (like from a request parameter).


**Context**:
We needed a way to choose the correct FileUploadStrategy at runtime, depending on what type the user or system asks for (local, s3, etc.).
**Solution**:
- We created a FileUploadStrategyFactory class
- It uses Spring to inject a map of all strategy beans (@Component("local"), @Component("s3"), etc.)
- Based on the StorageType, the factory returns the matching strategy implementation

Spring does the wiring behind the scenes, we don't need to manually write the if-else or switch code.
We have used the Spring boot's dependency injection to resolve the interface to each of concrete classes
Since each strategy is marked with @Component annotation, we can get all the concrete implementations.
[Reference - Good article](https://thegeekyasian.com/strategy-pattern-spring-boot/)


