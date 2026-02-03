# API Overview - GharSaathi

> Complete reference for the GharSaathi REST API

## 🌐 API Introduction

The GharSaathi API is a RESTful web service that provides programmatic access to all platform features. It follows REST principles, uses JSON for data exchange, and implements JWT-based authentication.

## 🏗️ API Architecture

### Base URL
```
Production: https://api.gharsaathi.com/api/v1
Development: http://localhost:8080/api/v1
```

### API Version
Current version: **v1**  
All endpoints are prefixed with `/api/v1`

### Design Principles
- **RESTful**: Resource-oriented URLs
- **Stateless**: No server-side session storage
- **JSON**: All requests and responses use JSON
- **HATEOAS**: Hypermedia links in responses (planned)
- **Versioned**: API versioning through URL

---

## 🔐 Authentication

### Authentication Method
**JWT (JSON Web Token)** Bearer Authentication

### Getting Authenticated

#### 1. Register
```http
POST /api/v1/auth/register
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "SecurePass123",
  "firstName": "John",
  "lastName": "Doe",
  "role": "TENANT",
  "phone": "+9779841234567"
}
```

Response:
```json
{
  "id": 1,
  "email": "user@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "role": "TENANT",
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tokenType": "Bearer",
  "expiresIn": 3600
}
```

#### 2. Login
```http
POST /api/v1/auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "SecurePass123"
}
```

#### 3. Use Access Token
```http
GET /api/v1/properties
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

#### 4. Refresh Token
```http
POST /api/v1/auth/refresh
Authorization: Bearer <refresh-token>
```

### Token Lifecycle
- **Access Token**: Valid for 1 hour
- **Refresh Token**: Valid for 7 days
- **Token Blacklist**: Revoked tokens cannot be reused

---

## 📊 API Modules

### 1. Authentication Module
**Base Path**: `/api/v1/auth`

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/register` | Create new user account | ❌ |
| POST | `/login` | Authenticate and get tokens | ❌ |
| POST | `/refresh` | Get new access token | ✅ Refresh Token |
| POST | `/logout` | Invalidate tokens | ✅ |

### 2. Property Module
**Base Path**: `/api/v1/properties`

| Method | Endpoint | Description | Auth Required | Role |
|--------|----------|-------------|---------------|------|
| GET | `/` | List all available properties | ❌ | Public |
| GET | `/{id}` | Get property details | ❌ | Public |
| POST | `/` | Create new property | ✅ | LANDLORD |
| PUT | `/{id}` | Update property | ✅ | LANDLORD |
| DELETE | `/{id}` | Delete property | ✅ | LANDLORD |
| GET | `/my-properties` | Get landlord's properties | ✅ | LANDLORD |
| GET | `/search` | Search properties with filters | ❌ | Public |

### 3. Rental Application Module
**Base Path**: `/api/v1/rental-applications`

| Method | Endpoint | Description | Auth Required | Role |
|--------|----------|-------------|---------------|------|
| POST | `/` | Submit rental application | ✅ | TENANT |
| GET | `/my-applications` | Get tenant's applications | ✅ | TENANT |
| GET | `/for-my-properties` | Get applications for properties | ✅ | LANDLORD |
| POST | `/{id}/approve` | Approve application | ✅ | LANDLORD |
| POST | `/{id}/reject` | Reject application | ✅ | LANDLORD |
| DELETE | `/{id}/withdraw` | Withdraw application | ✅ | TENANT |

### 4. Lease Management Module
**Base Path**: `/api/v1/leases`

| Method | Endpoint | Description | Auth Required | Role |
|--------|----------|-------------|---------------|------|
| POST | `/` | Create lease agreement | ✅ | LANDLORD |
| GET | `/my-leases` | Get tenant's leases | ✅ | TENANT |
| GET | `/my-properties` | Get landlord's leases | ✅ | LANDLORD |
| POST | `/{id}/terminate` | Terminate lease | ✅ | LANDLORD |
| GET | `/{id}` | Get lease details | ✅ | TENANT/LANDLORD |

### 5. Payment Module
**Base Path**: `/api/v1/payments`

| Method | Endpoint | Description | Auth Required | Role |
|--------|----------|-------------|---------------|------|
| POST | `/generate` | Generate payment | ✅ | LANDLORD |
| GET | `/my-payments` | Get tenant's payments | ✅ | TENANT |
| GET | `/for-my-properties` | Get property payments | ✅ | LANDLORD |
| POST | `/{id}/tenant-confirm` | Confirm payment (tenant) | ✅ | TENANT |
| POST | `/{id}/landlord-confirm` | Confirm payment (landlord) | ✅ | LANDLORD |
| GET | `/{id}/receipt` | Get payment receipt | ✅ | TENANT/LANDLORD |

### 6. User Profile Module
**Base Path**: `/api/v1/profile`

| Method | Endpoint | Description | Auth Required | Role |
|--------|----------|-------------|---------------|------|
| GET | `/` | Get current user profile | ✅ | All |
| PUT | `/` | Update profile | ✅ | All |
| PUT | `/change-password` | Change password | ✅ | All |

### 7. Admin User Management
**Base Path**: `/api/v1/admin/users`

| Method | Endpoint | Description | Auth Required | Role |
|--------|----------|-------------|---------------|------|
| GET | `/` | List all users | ✅ | ADMIN |
| GET | `/{id}` | Get user details | ✅ | ADMIN |
| POST | `/{id}/suspend` | Suspend user | ✅ | ADMIN |
| POST | `/{id}/unsuspend` | Unsuspend user | ✅ | ADMIN |
| PUT | `/{id}/role` | Change user role | ✅ | ADMIN |

### 8. File Upload Module
**Base Path**: `/api/v1/files`

| Method | Endpoint | Description | Auth Required | Role |
|--------|----------|-------------|---------------|------|
| POST | `/upload` | Upload file | ✅ | All |
| GET | `/{id}/download` | Download file | ✅ | All |
| DELETE | `/{id}` | Delete file | ✅ | Owner |
| GET | `/my-files` | Get user's files | ✅ | All |

### 9. Dashboard Module
**Base Path**: `/api/v1/dashboard`

| Method | Endpoint | Description | Auth Required | Role |
|--------|----------|-------------|---------------|------|
| GET | `/tenant` | Get tenant dashboard | ✅ | TENANT |
| GET | `/landlord` | Get landlord dashboard | ✅ | LANDLORD |
| GET | `/admin` | Get admin dashboard | ✅ | ADMIN |

---

## 📝 Request/Response Format

### Request Headers
```http
Content-Type: application/json
Authorization: Bearer <access-token>
Accept: application/json
```

### Standard Request Body
```json
{
  "field1": "value1",
  "field2": "value2"
}
```

### Success Response Format
```json
{
  "id": 1,
  "field": "value",
  "createdAt": "2026-01-28T10:30:00",
  "updatedAt": "2026-01-28T10:30:00"
}
```

### Error Response Format
```json
{
  "timestamp": "2026-01-28T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/api/v1/properties",
  "errors": [
    {
      "field": "monthlyRent",
      "message": "must be greater than 0"
    }
  ]
}
```

---

## 📊 HTTP Status Codes

### Success Codes
| Code | Meaning | Usage |
|------|---------|-------|
| **200** | OK | Successful GET, PUT, PATCH requests |
| **201** | Created | Successful POST requests |
| **204** | No Content | Successful DELETE requests |

### Client Error Codes
| Code | Meaning | Usage |
|------|---------|-------|
| **400** | Bad Request | Invalid request data or validation failure |
| **401** | Unauthorized | Missing or invalid authentication |
| **403** | Forbidden | Authenticated but not authorized |
| **404** | Not Found | Resource doesn't exist |
| **409** | Conflict | Duplicate resource or state conflict |

### Server Error Codes
| Code | Meaning | Usage |
|------|---------|-------|
| **500** | Internal Server Error | Unexpected server error |
| **503** | Service Unavailable | Server temporarily unavailable |

---

## 🔍 Query Parameters

### Pagination
```http
GET /api/v1/properties?page=0&size=20
```

Parameters:
- `page`: Page number (0-indexed, default: 0)
- `size`: Items per page (default: 20, max: 100)

Response:
```json
{
  "content": [...],
  "totalElements": 150,
  "totalPages": 8,
  "size": 20,
  "number": 0
}
```

### Sorting
```http
GET /api/v1/properties?sort=monthlyRent,asc
```

Parameters:
- `sort`: `field,direction` (direction: asc/desc)

### Filtering
```http
GET /api/v1/properties?city=Kathmandu&minRent=10000&maxRent=50000
```

Common filters:
- Property: `city`, `propertyType`, `minRent`, `maxRent`, `amenities`
- Application: `status`
- Payment: `status`, `fromDate`, `toDate`

---

## 🔐 Security Features

### Rate Limiting
- **General APIs**: 100 requests per minute
- **Auth APIs**: 10 requests per minute
- **Admin APIs**: 200 requests per minute

### CORS Policy
```yaml
Allowed Origins: http://localhost:3000, https://gharsaathi.com
Allowed Methods: GET, POST, PUT, DELETE, OPTIONS
Allowed Headers: Authorization, Content-Type
Allow Credentials: true
```

### Input Validation
- All inputs validated using Bean Validation
- SQL injection prevention through parameterized queries
- XSS prevention through output encoding
- File upload size limits: 10MB max

---

## 📖 API Conventions

### Naming Conventions
- **Endpoints**: Lowercase, hyphen-separated (kebab-case)
- **Fields**: camelCase
- **Enums**: UPPER_SNAKE_CASE

### Date/Time Format
- **ISO 8601**: `2026-01-28T10:30:00`
- **Timezone**: UTC by default
- **Date Only**: `2026-01-28`

### Currency
- **Format**: Decimal number
- **Currency**: NPR (Nepali Rupees)
- **Example**: `25000.00`

### IDs
- **Type**: Long integer
- **Format**: Numeric
- **Example**: `12345`

---

## 🧪 Testing the API

### Using cURL
```bash
# Login
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"user@example.com","password":"password"}'

# Get Properties
curl -X GET http://localhost:8080/api/v1/properties \
  -H "Authorization: Bearer <token>"
```

### Using Postman
1. Import collection from `backend/tests/`
2. Set environment variables
3. Run requests

### Using HTTPie
```bash
# Login
http POST :8080/api/v1/auth/login \
  email=user@example.com password=password

# Get Properties
http GET :8080/api/v1/properties \
  Authorization:"Bearer <token>"
```

---

## 📚 Complete Endpoint List

### Total Endpoints: 60+

#### Public Endpoints (No Auth) - 3
- `POST /api/v1/auth/register`
- `POST /api/v1/auth/login`
- `GET /api/v1/properties` (with search)

#### Tenant Endpoints - 10+
- Rental applications (submit, view, withdraw)
- Leases (view)
- Payments (view, confirm)
- Profile management
- Dashboard

#### Landlord Endpoints - 20+
- Property management (CRUD)
- Application reviews (approve/reject)
- Lease management (create, terminate)
- Payment generation and confirmation
- Dashboard analytics

#### Admin Endpoints - 15+
- User management
- Platform oversight
- Analytics and reporting

#### Common Endpoints - 10+
- Profile management
- File upload/download
- Authentication (logout, refresh)

---

## 🔗 Related Documents

- [Authentication API](./authentication-api.md) - Detailed auth endpoints
- [User API](./user-api.md) - User management endpoints
- [Domain APIs](./domain-apis.md) - Property, application, lease APIs
- [Error Handling](./error-handling.md) - Error responses and codes
- [Request/Response Samples](./request-response-samples.md) - Example payloads

---

## 📞 API Support

### Getting Help
- **Documentation**: This docs folder
- **Test Cases**: `backend/tests/` directory
- **Issues**: Project repository issues section

### Best Practices
1. Always include Authorization header for protected endpoints
2. Handle token expiration and refresh
3. Validate input on client side before sending
4. Handle all error responses appropriately
5. Use pagination for list endpoints
6. Cache responses where appropriate

---

**API Version**: 1.0.0  
**Last Updated**: January 28, 2026  
**Base URL**: `/api/v1`  
**Authentication**: JWT Bearer Token
