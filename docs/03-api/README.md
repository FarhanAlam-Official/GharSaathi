# API Documentation

> Complete REST API reference for GharSaathi

## 📋 Contents

This section contains complete API documentation:

### [API Overview](./api-overview.md)
Introduction to the GharSaathi API including authentication, base URLs, conventions, and module summary.

### [Authentication API](./authentication-api.md)
Detailed documentation for authentication endpoints including registration, login, token refresh, and logout.

### [User API](./user-api.md)
User profile management endpoints including profile viewing, updating, and password changes.

### [Domain APIs](./domain-apis.md)
Core business logic APIs for properties, rental applications, leases, payments, file uploads, and dashboards.

### [Request/Response Samples](./request-response-samples.md)
Example API requests and responses for all major endpoints with explanations.

### [Error Handling](./error-handling.md)
Complete guide to error responses, status codes, and error handling best practices.

### [Implementation Summaries](./implementations/)
Detailed implementation documentation for completed modules including User Profile, Payment System, Lease Management, Dashboard, and more.

### [Payment System Documentation](./payment/)
Comprehensive payment system documentation including Khalti integration, quick start guides, and verification procedures.

## 🌐 API Quick Reference

### Base URL
```
Development: http://localhost:8080/api/v1
Production: https://api.gharsaathi.com/api/v1
```

### Authentication
All protected endpoints require JWT Bearer token:
```http
Authorization: Bearer <access-token>
```

### Content Type
```http
Content-Type: application/json
Accept: application/json
```

## 📊 API Statistics

- **Total Endpoints**: 60+
- **Public Endpoints**: 3 (register, login, browse properties)
- **Protected Endpoints**: 57+
- **API Modules**: 9
- **Authentication**: JWT Bearer Token

## 🎯 Quick Start

### 1. Register a User
```bash
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "SecurePass123",
    "firstName": "John",
    "lastName": "Doe",
    "role": "TENANT",
    "phone": "+9779841234567"
  }'
```

### 2. Login
```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "SecurePass123"
  }'
```

### 3. Use Token
```bash
curl -X GET http://localhost:8080/api/v1/properties \
  -H "Authorization: Bearer <your-access-token>"
```

## 🔗 Module Links

### Core APIs
- [Authentication](./authentication-api.md) - User auth and tokens
- [Properties](./domain-apis.md#property-management-api) - Property CRUD and search
- [Applications](./domain-apis.md#rental-application-api) - Rental applications
- [Leases](./domain-apis.md#lease-management-api) - Lease agreements
- [Payments](./domain-apis.md#payment-api) - Payment tracking

### Support APIs
- [User Profile](./user-api.md) - Profile management
- [File Upload](./domain-apis.md#file-upload-api) - File handling
- [Dashboard](./domain-apis.md#dashboard-api) - Analytics
- [Admin](./user-api.md#admin-api) - User management

## 📚 Testing Resources

- **Postman Collection**: `backend/tests/` directory
- **Test Documentation**: `backend/tests/*.txt` files
- **Example Data**: Available in test files

## 🔗 Related Sections

- [Architecture](../02-architecture/) - System design
- [Security](../05-security/) - Security implementation
- [Database](../06-database/) - Data models

---

**API Version**: v1  
**Last Updated**: January 28, 2026  
**Endpoints**: 60+ RESTful APIs
