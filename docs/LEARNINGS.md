- @RestController annotation (which includes the @ResponseBody) and the presence of a json serializer (jackson)
- When sending back the response use dto or objects and Always return the json 
- Use the ResponseEntity<> and setup the standard API response
- Create a standard responseclass using the generic
- Circular dependency
  - Circular dependency occurs when Bean A depends on another Bean B, and the Bean B depends on Bean A. 
  - Springboot throws a BeanCurrentIncreationExecption, and also can be avoided by using the @lazy
- Logging mechanism learning resources -https://betterstack.com/community/guides/logging/java/logback/ 
  - https://last9.io/blog/a-guide-to-spring-boot-logging/ reference for implementing the mdc and custom response

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



## Aspect oriented programming
- AOP is paradigm that lets us segegrate the cross-cutting concerns, such logging and transaction (research about this) from business logic
- 