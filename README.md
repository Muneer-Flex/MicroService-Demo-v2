# MicroService-Demo-v2
This Project comprises of more than one microservice and provides demo on the below
1) Service to Service Communication using REST API (Rest Template)
2) Eureka Server: Separate Eureka Server for Service Registry
3) Eureka Client: All Microservices connects to Eureka Server for service to service communication
4) Spring Data JPA: For connecting & database related activities (Create, Fetch, Update Operations) from Microservices
5) Distributed Redis Cache: For Caching the database/service calls using Redis Cache and implemented Advanced Redis Configurations
6) Lombok: Implemented Lombok and Builder Pattern for removing boiler plate coding
7) Mapstructs: Implemeted Mapstructs with advanced use cases for mapping b/w Entity & Pojo classes
8) Log4J with MDC & Sleuth: Implemented Log4j using Logback and added MDC & Spring Sleuth for carrying the required informations (requestId, traceId, spanId) across multiple microservices for traceability
9) Actuators: For Health Monitoring of Microservices
10) Custom Exceptions: Added Custom Exceptions and handling using @RestControllerAdvice and @ExceptionHandlers to aggregate Exception handling globally
11) Custom Annotations: Created Custom Annotations for field level validations using Custom Validators (@Valid and BindingResults)
12) AspectJ: Created custom Annotations and implemented AspectJ Advise (@Around) for Centralized Logging of Time taken for each method, request & response logging to Cassandra
13) Open API & Swagger UI: Implemented OpenAPI for API Documentation with Swagger-UI and Added Authorization for each API using SecurityScheme (@SecurityScheme) and SecurityRequirements (@SecurityRequirements)
14) Rest Template Interceptors: Implemented ClientHttpRequestInterceptors for logging the request sent and response received for each API Invocation using Rest Template
15) Spring Security with JWT: Implemeted API Security using Centralized Auth Service with Privilege Mechanism for user sign-up, creating JWT Token after user verification and privilege verification and authorizing the JWT
16) Apache Kafka: Producer & Consumer Pattern using Apache Kafka (Key: String, Value: JSON). Implemented using Kafka Template and ReplyKafkaTemplate
