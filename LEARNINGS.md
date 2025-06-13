- @RestController annotation (which includes the @ResponseBody) and the presence of a json serializer (jackson)
- When sending back the response use dto or objects and Always return the json 
- Use the ResponseEntity<> and setup the standard API response
- Create a standard responseclass using the generic
- Circular dependency
  - Circular dependency occurs when Bean A depends on another Bean B, and the Bean B depends on Bean A. 
  - Springboot throws a BeanCurrentIncreationExecption, and also can be avoided by using the @lazy
- Logging mechanism learning resources -https://betterstack.com/community/guides/logging/java/logback/ 
  - https://last9.io/blog/a-guide-to-spring-boot-logging/ reference for implementing the mdc and custom response

## Aspect oriented programming
- AOP is paradigm that lets us segegrate the cross-cutting concerns, such logging and transaction (research about this) from business logic
- 