# Industry-Grade JWT Authentication System - GharSaathi

## Overview

This is a comprehensive, production-ready JWT authentication system built with Spring Boot 4.0.1 and Spring Security. It implements industry best practices for secure user authentication and authorization.

## 🔐 Key Features

### 1. **Dual Token System**

- **Access Tokens**: Short-lived tokens (1 hour) for API authentication
- **Refresh Tokens**: Long-lived tokens (7 days) for obtaining new access tokens
- Both tokens include unique identifiers (JTI) for tracking and revocation

### 2. **Token Security**

- **Token Blacklisting**: Revoked tokens are stored in database to prevent reuse
- **Token Type Validation**: System validates token type (ACCESS vs REFRESH)
- **Automatic Cleanup**: Scheduled job removes expired tokens daily at 2 AM
- **IP & User Agent Tracking**: Records device information for security auditing

### 3. **Enhanced JWT Claims**

```json
{
  "jti": "unique-token-id",
  "sub": "user@email.com",
  "userId": 123,
  "role": "LANDLORD",
  "type": "ACCESS",
  "authorities": ["ROLE_LANDLORD"],
  "iat": 1704236400,
  "exp": 1704240000
}
```

### 4. **Security Features**

- BCrypt password hashing (strength: 12)
- CORS configuration for frontend integration
- XSS protection headers
- Content Security Policy
- Frame-Options protection
- Role-based access control (RBAC)

### 5. **Exception Handling**

- Custom JWT exceptions (InvalidTokenException, TokenExpiredException, TokenRevokedException)
- Comprehensive error responses
- Detailed logging for security events

## 📁 Project Structure

```
backend/src/main/java/com/gharsaathi/
├── auth/
│   ├── controller/
│   │   └── AuthController.java          # Auth endpoints
│   ├── model/
│   │   ├── User.java                    # User entity
│   │   ├── Role.java                    # Role enum
│   │   ├── RefreshToken.java           # Refresh token entity
│   │   └── TokenBlacklist.java         # Blacklisted tokens
│   ├── repository/
│   │   ├── UserRepository.java
│   │   ├── RefreshTokenRepository.java
│   │   └── TokenBlacklistRepository.java
│   └── service/
│       ├── AuthService.java             # Authentication logic
│       ├── RefreshTokenService.java     # Token refresh handling
│       └── TokenBlacklistService.java   # Token revocation
├── common/
│   ├── dto/
│   │   ├── AuthResponse.java           # Login/Register response
│   │   ├── LoginRequest.java
│   │   ├── RegisterRequest.java
│   │   ├── TokenRefreshRequest.java
│   │   └── TokenRefreshResponse.java
│   ├── exception/
│   │   ├── GlobalExceptionHandler.java
│   │   ├── InvalidTokenException.java
│   │   ├── TokenExpiredException.java
│   │   └── TokenRevokedException.java
│   ├── security/
│   │   ├── JwtUtil.java                # JWT generation & validation
│   │   ├── JwtAuthenticationFilter.java # Request filtering
│   │   └── SecurityConfig.java         # Security configuration
│   └── util/
│       └── JwtProperties.java          # JWT config properties
```

## 🔄 Authentication Flow

### 1. Registration

```http
POST /api/auth/register
Content-Type: application/json

{
  "fullName": "John Doe",
  "email": "john@example.com",
  "password": "SecurePass123",
  "phoneNumber": "1234567890",
  "role": "TENANT"
}
```

**Response:**

```json
{
  "accessToken": "eyJhbGci...",
  "refreshToken": "eyJhbGci...",
  "tokenType": "Bearer",
  "expiresIn": 1704240000,
  "userId": 1,
  "email": "john@example.com",
  "fullName": "John Doe",
  "role": "TENANT",
  "message": "User registered successfully"
}
```

### 2. Login

```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "john@example.com",
  "password": "SecurePass123"
}
```

**Response:** Same as registration

### 3. Token Refresh

```http
POST /api/auth/refresh
Content-Type: application/json

{
  "refreshToken": "eyJhbGci..."
}
```

**Response:**

```json
{
  "accessToken": "eyJhbGci...",
  "refreshToken": "eyJhbGci...",
  "tokenType": "Bearer",
  "expiresIn": 3600000
}
```

### 4. Logout (Single Device)

```http
POST /api/auth/logout
Authorization: Bearer eyJhbGci...
```

**Response:**

```json
"Logged out successfully"
```

### 5. Logout (All Devices)

```http
POST /api/auth/logout/all
Authorization: Bearer eyJhbGci...
```

**Response:**

```json
"Logged out from all devices successfully"
```

## 🛡️ Security Configuration

### Environment Variables (Production)

```properties
JWT_SECRET=your-secure-base64-secret-key-here
JWT_EXPIRATION=3600000
JWT_REFRESH_EXPIRATION=604800000
JWT_ISSUER=gharsaathi
```

### Generate Secure Secret

```bash
openssl rand -base64 64
```

### Token Expiration

- **Access Token**: 1 hour (3600000 ms)
- **Refresh Token**: 7 days (604800000 ms)

## 🎯 Role-Based Access Control

| Role | Endpoints | Description |
|------|-----------|-------------|
| ADMIN | `/api/admin/**` | Full system access |
| LANDLORD | `/api/landlord/**` | Property management |
| TENANT | `/api/tenant/**` | Property search & applications |

## 📊 Database Schema

### users

```sql
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone_number VARCHAR(15),
    role VARCHAR(20) NOT NULL,
    enabled BOOLEAN DEFAULT TRUE,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL
);
```

### refresh_tokens

```sql
CREATE TABLE refresh_tokens (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    token VARCHAR(512) UNIQUE NOT NULL,
    token_id VARCHAR(255) UNIQUE NOT NULL,
    user_id BIGINT NOT NULL,
    expires_at DATETIME NOT NULL,
    created_at DATETIME NOT NULL,
    revoked BOOLEAN DEFAULT FALSE,
    ip_address VARCHAR(45),
    user_agent VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(id)
);
```

### token_blacklist

```sql
CREATE TABLE token_blacklist (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    token_id VARCHAR(255) UNIQUE NOT NULL,
    blacklisted_at DATETIME NOT NULL,
    expires_at DATETIME NOT NULL,
    reason VARCHAR(255)
);
```

## 🔧 Configuration

### CORS Configuration

```java
@Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of("http://localhost:3000"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
    configuration.setAllowedHeaders(List.of("*"));
    configuration.setAllowCredentials(true);
    configuration.setMaxAge(3600L);
    return source;
}
```

## 📝 Frontend Integration Example

### React/Next.js Example

```typescript
// Login
const login = async (email: string, password: string) => {
  const response = await fetch('http://localhost:8080/api/auth/login', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ email, password })
  });
  
  const data = await response.json();
  localStorage.setItem('accessToken', data.accessToken);
  localStorage.setItem('refreshToken', data.refreshToken);
  return data;
};

// Protected API Call
const fetchProtectedData = async () => {
  const token = localStorage.getItem('accessToken');
  const response = await fetch('http://localhost:8080/api/tenant/properties', {
    headers: { 'Authorization': `Bearer ${token}` }
  });
  
  if (response.status === 401) {
    // Token expired, refresh it
    await refreshToken();
    return fetchProtectedData(); // Retry
  }
  
  return response.json();
};

// Refresh Token
const refreshToken = async () => {
  const refreshToken = localStorage.getItem('refreshToken');
  const response = await fetch('http://localhost:8080/api/auth/refresh', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ refreshToken })
  });
  
  const data = await response.json();
  localStorage.setItem('accessToken', data.accessToken);
};

// Logout
const logout = async () => {
  const token = localStorage.getItem('accessToken');
  await fetch('http://localhost:8080/api/auth/logout', {
    method: 'POST',
    headers: { 'Authorization': `Bearer ${token}` }
  });
  
  localStorage.removeItem('accessToken');
  localStorage.removeItem('refreshToken');
};
```

## 🧪 Testing

### Using cURL

```bash
# Register
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "Test User",
    "email": "test@example.com",
    "password": "Password123",
    "phoneNumber": "1234567890",
    "role": "TENANT"
  }'

# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "Password123"
  }'

# Access Protected Endpoint
curl -X GET http://localhost:8080/api/tenant/dashboard \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN"

# Refresh Token
curl -X POST http://localhost:8080/api/auth/refresh \
  -H "Content-Type: application/json" \
  -d '{
    "refreshToken": "YOUR_REFRESH_TOKEN"
  }'

# Logout
curl -X POST http://localhost:8080/api/auth/logout \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

## 🚀 Best Practices Implemented

1. **Separation of Concerns**: Clear separation between authentication, authorization, and business logic
2. **Stateless Authentication**: JWT-based stateless authentication for scalability
3. **Token Rotation**: Refresh token mechanism for security
4. **Audit Trail**: IP and user agent tracking for security monitoring
5. **Automatic Cleanup**: Scheduled tasks for database maintenance
6. **Error Handling**: Comprehensive exception handling with meaningful messages
7. **Logging**: Detailed security event logging
8. **Validation**: Input validation on all endpoints
9. **HTTPS Ready**: Security headers configured for production

## 🔮 Future Enhancements

- [ ] Multi-factor authentication (MFA)
- [ ] OAuth2 integration (Google, Facebook)
- [ ] Rate limiting per user/IP
- [ ] Password reset functionality
- [ ] Email verification
- [ ] Account lockout after failed attempts
- [ ] Session management dashboard
- [ ] Token encryption at rest
- [ ] Biometric authentication support

## 📚 Dependencies

```xml
<dependencies>
    <!-- Spring Boot -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    
    <!-- JWT -->
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-api</artifactId>
        <version>0.12.6</version>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-impl</artifactId>
        <version>0.12.6</version>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-jackson</artifactId>
        <version>0.12.6</version>
    </dependency>
</dependencies>
```

## 🐛 Troubleshooting

### Common Issues

1. **401 Unauthorized**: Check if token is expired, use refresh token
2. **Token Validation Failed**: Verify JWT secret matches in configuration
3. **CORS Errors**: Ensure frontend URL is in allowed origins
4. **Database Connection**: Check MySQL is running on port 3307

## 📞 Support

For issues or questions, contact the development team or create an issue in the repository.

---

**Last Updated**: January 2, 2026  
**Version**: 1.0.0  
**Author**: GharSaathi Development Team
