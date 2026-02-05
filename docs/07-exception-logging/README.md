# Exception & Logging Documentation

> Error handling and logging strategies for GharSaathi

## 📋 Contents

### [Global Exception Handling](./global-exception-handling.md)

Centralized exception handling using `@ControllerAdvice` and custom error responses.

### [Custom Exceptions](./custom-exceptions.md)

Custom exception classes for different error scenarios across modules.

### [Logging Strategy](./logging-strategy.md)

Logging levels, patterns, and best practices for application logging.

### [Audit Logging](./audit-logging.md)

Tracking important user actions and system events for security and compliance.

## 🎯 Exception Handling Overview

GharSaathi implements **comprehensive exception handling** with:

### Global Exception Handler

- **@ControllerAdvice**: Centralized exception handling
- **Consistent Format**: Standardized error responses
- **HTTP Status Mapping**: Appropriate status codes
- **Validation Errors**: Field-level error details

### Custom Exceptions

Organized by module:

- **Auth**: InvalidTokenException, TokenExpiredException
- **Property**: PropertyNotFoundException
- **Application**: DuplicateApplicationException
- **Lease**: LeaseNotFoundException
- **Payment**: PaymentNotFoundException
- **User**: UserNotFoundException

### Error Response Format

```json
{
  "timestamp": "2026-01-28T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Property with ID 123 not found",
  "path": "/api/v1/properties/123"
}
```

## 📝 Logging Overview

### Logging Framework

**SLF4J + Logback** (Spring Boot default)

### Log Levels

```
TRACE → DEBUG → INFO → WARN → ERROR
```

### Usage by Level

- **ERROR**: Exceptions, critical failures
- **WARN**: Deprecations, non-critical issues
- **INFO**: Application startup, important events
- **DEBUG**: Detailed flow information (dev only)
- **TRACE**: Very detailed debugging (rarely used)

### Log Output

```
2026-01-28 10:30:45 INFO  [PropertyService] - Creating new property for landlord ID: 5
2026-01-28 10:30:46 DEBUG [PropertyRepository] - Saving property to database
2026-01-28 10:30:47 ERROR [GlobalExceptionHandler] - Property creation failed: Validation error
```

## 🛡️ Exception Categories

### 1. Client Errors (4xx)

- **400 Bad Request**: Validation failures
- **401 Unauthorized**: Authentication required
- **403 Forbidden**: Insufficient permissions
- **404 Not Found**: Resource doesn't exist
- **409 Conflict**: Duplicate or state conflict

### 2. Server Errors (5xx)

- **500 Internal Server Error**: Unexpected errors
- **503 Service Unavailable**: Temporary unavailability

### 3. Custom Business Errors

- Application-specific errors with detailed messages
- Mapped to appropriate HTTP status codes

## 🔍 Audit Logging

### What We Log

✅ User authentication (login, logout)  
✅ Role changes  
✅ User suspension/unsuspension  
✅ Property creation/deletion  
✅ Application approval/rejection  
✅ Lease creation/termination  
✅ Payment confirmations  
✅ Security events (invalid tokens, unauthorized access)

### What We Don't Log

❌ Passwords (never logged)  
❌ Sensitive personal data  
❌ Payment card details (not processed)  
❌ Excessive debug information in production

## 📊 Logging Best Practices

### Do's ✅

- Log at appropriate levels
- Include context (user ID, resource ID)
- Use structured logging
- Log exceptions with stack traces
- Log security events
- Use correlation IDs for request tracking

### Don'ts ❌

- Don't log sensitive data
- Don't log in loops (performance)
- Don't use System.out.println
- Don't log excessive details in production
- Don't ignore exceptions

## 🔧 Configuration

### Log File Location

```
Development: Console output
Production: /var/log/gharsaathi/app.log
```

### Log Rotation

```
Max File Size: 10MB
Max History: 30 days
Total Size Cap: 1GB
```

## 🔗 Quick Reference

### Throw Custom Exception

```java
if (property == null) {
    throw new PropertyNotFoundException(
        "Property with ID " + id + " not found"
    );
}
```

### Log an Event

```java
@Slf4j
public class PropertyService {
    public Property createProperty(PropertyDTO dto) {
        log.info("Creating property: {}", dto.getTitle());
        // ... logic
        log.debug("Property saved with ID: {}", property.getId());
        return property;
    }
}
```

### Handle in Controller Advice

```java
@ExceptionHandler(PropertyNotFoundException.class)
public ResponseEntity<ErrorResponse> handlePropertyNotFound(
    PropertyNotFoundException ex
) {
    ErrorResponse error = new ErrorResponse(
        LocalDateTime.now(),
        HttpStatus.NOT_FOUND.value(),
        "Not Found",
        ex.getMessage(),
        request.getRequestURI()
    );
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
}
```

## 🔗 Related Documentation

- [API Error Handling](../03-api/error-handling.md)
- [Security Logging](../05-security/spring-security-overview.md)
- [Architecture](../02-architecture/)

---

**Logging Framework**: SLF4J + Logback  
**Exception Handling**: @ControllerAdvice  
**Audit**: Security events logged  
**Last Updated**: January 28, 2026
