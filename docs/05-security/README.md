# Security Documentation

> Spring Security implementation and best practices for GharSaathi

## 📋 Contents

### [Spring Security Overview](./spring-security-overview.md)

Introduction to Spring Security architecture and how it's implemented in GharSaathi.

### [Authentication Flow](./authentication-flow.md)

Detailed flow diagrams and explanations of the JWT-based authentication process.

### [Authorization Rules](./authorization-rules.md)

Role-based access control (RBAC) implementation and endpoint security configuration.

### [JWT & OAuth](./jwt-oauth.md)

JWT token structure, generation, validation, and refresh token mechanism.

### [CORS & CSRF](./cors-csrf.md)

Cross-Origin Resource Sharing (CORS) configuration and CSRF protection strategy.

### [Detailed Security Documentation](./detailed/)

In-depth security documentation including complete JWT authentication system implementation, token management, and security best practices.

## 🔐 Security Overview

GharSaathi implements **industry-grade security** with:

### Authentication

- **JWT Tokens**: Stateless authentication
- **BCrypt Hashing**: Secure password storage (strength: 12)
- **Token Blacklist**: Revoked token prevention
- **Dual Token System**: Access + Refresh tokens

### Authorization

- **RBAC**: Role-Based Access Control
- **Method Security**: `@PreAuthorize` annotations
- **URL Security**: Endpoint-level protection
- **Owner Verification**: Resource access validation

### Protection Mechanisms

- **XSS Protection**: Output encoding
- **SQL Injection**: Parameterized queries
- **CORS**: Controlled cross-origin access
- **Rate Limiting**: API abuse prevention
- **Token Expiry**: Automatic session timeout

## 🎯 Security Features

### Token Security

```
Access Token:  1 hour lifetime
Refresh Token: 7 days lifetime
Token Blacklist: Prevents reuse
JTI (Token ID): Unique identifier
```

### Password Security

```
Algorithm: BCrypt
Strength: 12 rounds
Salt: Automatic per-password
Min Length: 8 characters
Requirements: Complexity rules
```

### API Security

```
Authentication: Required on 95% of endpoints
Authorization: Role-based on all protected endpoints
Rate Limiting: 100 req/min (general), 10 req/min (auth)
```

## 🔑 JWT Token Structure

```json
{
  "header": {
    "alg": "HS256",
    "typ": "JWT"
  },
  "payload": {
    "jti": "unique-token-id",
    "sub": "user@email.com",
    "userId": 123,
    "role": "LANDLORD",
    "type": "ACCESS",
    "authorities": ["ROLE_LANDLORD"],
    "iat": 1704236400,
    "exp": 1704240000
  },
  "signature": "..."
}
```

## 🛡️ Security Best Practices

### For Developers

1. Never hardcode secrets in code
2. Always validate user input
3. Use parameterized queries
4. Implement proper error handling
5. Log security events
6. Keep dependencies updated

### For Users

1. Use strong passwords (8+ chars, mixed case, numbers, symbols)
2. Never share passwords or tokens
3. Log out when finished
4. Report suspicious activity

## 🔗 Quick Reference

### Secure an Endpoint

```java
@PreAuthorize("hasRole('LANDLORD')")
@PostMapping("/properties")
public ResponseEntity<?> createProperty() {
    // Only landlords can access
}
```

### Get Current User

```java
Authentication auth = SecurityContextHolder
    .getContext()
    .getAuthentication();
String email = auth.getName();
```

### Validate Token

```java
if (jwtTokenProvider.validateToken(token)) {
    // Token is valid
}
```

## 🔗 Related Documentation

- [Authentication API](../03-api/authentication-api.md)
- [User Roles](../01-requirements/user-roles.md)
- [Architecture](../02-architecture/)

---

**Security Model**: JWT + RBAC  
**Encryption**: BCrypt (strength 12)  
**Token Lifetime**: 1h access, 7d refresh  
**Last Updated**: January 28, 2026
