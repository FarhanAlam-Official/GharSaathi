# Detailed Security Documentation

This directory contains in-depth security documentation for the GharSaathi platform, including authentication mechanisms, authorization policies, and security best practices.

## Available Documentation

### Authentication & Authorization
- **[JWT Authentication System](./jwt-authentication-system.md)** - Complete JWT implementation, token management, and security features

## Security Architecture Overview

The GharSaathi security system implements:

### Authentication Layer
- **JWT-based Authentication** - Stateless token-based authentication
- **Token Refresh Mechanism** - Secure token renewal without re-login
- **Token Blacklisting** - Revocation of compromised or logged-out tokens
- **BCrypt Password Hashing** - Industry-standard password encryption
- **Multi-device Support** - Multiple active sessions per user

### Authorization Layer
- **Role-Based Access Control (RBAC)** - Four distinct user roles (Admin, Landlord, Tenant, Guest)
- **Method-level Security** - `@PreAuthorize` annotations on controller methods
- **Resource-level Authorization** - Users can only access their own resources
- **Admin Privileges** - Special permissions for system administration

### Security Features
- **Password Strength Requirements** - Enforced password complexity
- **Account Lockout** - Protection against brute force attacks
- **CORS Configuration** - Controlled cross-origin requests
- **HTTPS Enforcement** - Encrypted communication in production
- **SQL Injection Prevention** - Parameterized queries via JPA
- **XSS Protection** - Input validation and output encoding

## JWT Implementation Details

### Token Structure
```
Header.Payload.Signature
{
  "alg": "HS512",
  "typ": "JWT"
}
{
  "sub": "user@example.com",
  "roles": ["ROLE_TENANT"],
  "iat": 1234567890,
  "exp": 1234654290
}
```

### Token Lifecycle
1. **Issue** - On successful login
2. **Store** - Client stores in localStorage/sessionStorage
3. **Transmit** - Included in Authorization header
4. **Validate** - Server verifies signature and expiration
5. **Refresh** - Renewed before expiration
6. **Blacklist** - Revoked on logout or security event

### Security Endpoints
```
POST /api/auth/login         - Authenticate and receive tokens
POST /api/auth/register      - Create new user account
POST /api/auth/refresh       - Renew access token
POST /api/auth/logout        - Revoke tokens
```

## Role-Based Access Control

### Role Hierarchy
```
ADMIN > LANDLORD > TENANT > GUEST
```

### Permission Matrix
| Feature | Admin | Landlord | Tenant | Guest |
|---------|-------|----------|--------|-------|
| View Properties | ✓ | ✓ | ✓ | ✓ (Limited) |
| Create Property | ✓ | ✓ | ✗ | ✗ |
| Apply for Rental | ✓ | ✗ | ✓ | ✗ |
| Manage Users | ✓ | ✗ | ✗ | ✗ |
| Process Payments | ✓ | ✓ | ✓ | ✗ |
| View Analytics | ✓ | ✓ (Own) | ✓ (Own) | ✗ |

## Security Best Practices

### For Developers
1. **Never commit secrets** - Use environment variables
2. **Validate all inputs** - Use `@Valid` and custom validators
3. **Sanitize outputs** - Prevent XSS attacks
4. **Use HTTPS** - Always in production
5. **Keep dependencies updated** - Regular security patches
6. **Log security events** - Monitor for suspicious activity

### For Deployment
1. **Strong JWT secret** - Minimum 256-bit random key
2. **Short token lifetime** - 15-30 minutes for access tokens
3. **Database encryption** - Encrypt sensitive data at rest
4. **Regular backups** - Encrypted backup strategy
5. **Security headers** - CSP, HSTS, X-Frame-Options
6. **Rate limiting** - Prevent abuse and DoS attacks

## Configuration

### application.properties
```properties
# JWT Configuration
jwt.secret=${JWT_SECRET}
jwt.access-token-expiration=1800000      # 30 minutes
jwt.refresh-token-expiration=2592000000   # 30 days

# Security
spring.security.filter.order=5
server.ssl.enabled=true
```

### SecurityConfig.java
```java
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    // JWT filter, CORS, authorization rules
}
```

## Testing Security

### Unit Tests
- Token generation and validation
- Password encoding/verification
- Role-based method security
- CORS configuration

### Integration Tests
- Login/logout flows
- Token refresh mechanism
- Unauthorized access attempts
- Cross-role access prevention

### Security Audit Checklist
- [ ] All endpoints properly secured
- [ ] No hardcoded credentials
- [ ] Input validation on all DTOs
- [ ] SQL injection prevention
- [ ] XSS prevention measures
- [ ] CORS properly configured
- [ ] HTTPS enforced in production
- [ ] Secrets stored securely
- [ ] Logs don't contain sensitive data

## Related Documentation
- [Security Overview](../README.md) - High-level security architecture
- [User Roles](../../01-requirements/user-roles.md) - Detailed role definitions
- [API Documentation](../../03-api/) - Secured endpoints
- [Deployment](../../09-devops/) - Production security setup

## External Resources
- [OWASP Top 10](https://owasp.org/www-project-top-ten/)
- [JWT.io](https://jwt.io/) - JWT debugger and resources
- [Spring Security Docs](https://docs.spring.io/spring-security/reference/)

---
[← Back to Security](../README.md) | [Home](../../README.md)
