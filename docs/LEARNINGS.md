- @RestController annotation (which includes the @ResponseBody) and the presence of a json serializer (jackson)
- When sending back the response use dto or objects and Always return the json 
- Use the ResponseEntity<> and setup the standard API response
- Create a standard responseclass using the generic
- Circular dependency
  - Circular dependency occurs when Bean A depends on another Bean B, and the Bean B depends on Bean A. 
  - Springboot throws a BeanCurrentIncreationExecption, and also can be avoided by using the @lazy
- Logging mechanism learning resources -https://betterstack.com/community/guides/logging/java/logback/ 
  - https://last9.io/blog/a-guide-to-spring-boot-logging/ reference for implementing the mdc and custom response

### Spring security 
  
- I had defined exception handler for accessdenied and Invalid jwt or unauthenticated request, in global exception handler
- but i was able to catch the accessdeined but not the authenticationexception was caught, 
- Root Cause: When jwt is invalid, expired or malformed, Spring security throws AuthenticationExectpion inside the filter chain
- and handles it internally via AuthenticationEntryPoint-> It allows you to control the response to authenticated requests in way that make sense for app.
- hen a request comes in that requires authentication but no user is logged in, Spring Security needs to decide what to do. This decision-making process is the job of the AuthenticationEntryPoint.
- [Spring security and Securityfilterchain](https://medium.com/@samrat.alam/multiple-security-filter-chains-in-spring-security-for-separate-paths-in-spring-boot-avoiding-d6107445f3a8)

### Learning about design Pattern


### Learnings about the filters, 
- Filters work like a stack of LIFO 
  - Filters are executed in the order they are assinged
  - the first filter to run is the last to finish 
  - think of it as nested try - catch block
  - OncePerRequestFilter -- Ensures filter runs once per request, even in async
  - filterchain.doFilter() -> passes request to next filter/controller, 
  - MDC -> Mapped Diagnostic Context - used to add contextual info 
  - Client (Postman) ──> Your Logging Filter ──> Spring Security Filter ──> Controller
    ↓
    Controller logic
    ↓
    Client <──── Response <──── finally block (MDC.clear()) <──── filter ends
  
- Note: In java/Spring, we cannot modify the original request headers, meaning they are immutable.
- That why we set the mdc and all. For debugging all we return the response with traceId 

## Swagger And Documentation
- OpenAPI Specification - It defines a standardized format for describing the API's comprehensively
- Swagger - tool used to implement the OpenAPI specification 
  - It is open source framework used for desigining, building, documenting and consuming RESTful API's,
  - It provides standardized way of describe the structure of an API, make it easier understand and integrate
  - (SpringFox) --It is java library integrate the swagger with springboot application, it automatically generates documentation from spring controllers and models
  - Springdoc OpenAPI is an alternative to springfox, it is desgined to generate API documentation from springboot applications using the OpenAPI 3 spec


## Aspect oriented programming
- AOP is paradigm that lets us segegrate the cross-cutting concerns, such logging and transaction (research about this) from business logic


# General learnings 
- spring-data-jpa only gives you the core interfaces for Spring Data repositories, and assumes you configure JPA and Hibernate yourself.
- But spring-boot-starter-data-jpa is a Spring Boot starter that includes everything — Spring Data JPA, Hibernate (JPA provider), @Entity, auto-config, and repository support.
So, in Spring Boot apps, always use the starter version to avoid missing pieces like @Entity.