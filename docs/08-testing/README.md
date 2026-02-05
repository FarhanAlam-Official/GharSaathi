# Testing Documentation

> Testing strategy and test cases for GharSaathi

## 📋 Contents

### [Testing Strategy](./testing-strategy.md)

Overall approach to testing including types of tests, coverage goals, and testing phases.

### [Unit Testing](./unit-testing.md)

Testing individual components in isolation using JUnit and Mockito.

### [Integration Testing](./integration-testing.md)

Testing component interactions and database operations.

### [API Testing](./api-testing.md)

Complete API endpoint testing documentation and test cases.

### [Test Data](./test-data.md)

Test data setup, fixtures, and seed data for testing.

## 🧪 Testing Overview

GharSaathi implements **comprehensive testing** across multiple levels:

### Test Pyramid

```
        /\
       /  \      E2E Tests (Planned)
      /----\
     /      \    Integration Tests
    /--------\
   /          \  Unit Tests (In Progress)
  /____________\
```

### Testing Frameworks

- **JUnit 5**: Unit testing framework
- **Mockito**: Mocking framework
- **Spring Boot Test**: Integration testing
- **Postman**: API testing (manual)
- **REST Assured**: API testing (automated, planned)

## 📊 Test Coverage

### Current Status

- **Unit Tests**: 🟡 In Progress (Target: 70%)
- **Integration Tests**: 🟡 Planned
- **API Tests**: ✅ Complete (Manual test cases documented)
- **E2E Tests**: ❌ Out of Scope (future enhancement)

### Coverage Goals

| Test Type | Target Coverage | Current Status |
|-----------|----------------|----------------|
| Unit Tests | 70% | 40% (in progress) |
| Integration Tests | 60% | 0% (planned) |
| API Tests | 100% | 100% (manual) |

## 🎯 What We Test

### Unit Testing

- ✅ Service layer business logic
- ✅ Utility and helper methods
- ✅ DTO validation rules
- ✅ Custom exception scenarios
- 🟡 Repository custom queries (planned)

### Integration Testing

- 🟡 Controller + Service + Repository (planned)
- 🟡 Database operations (planned)
- 🟡 Security configurations (planned)
- 🟡 Transaction management (planned)

### API Testing

- ✅ All API endpoints (60+ endpoints)
- ✅ Authentication flows
- ✅ Authorization rules
- ✅ Request validation
- ✅ Response format
- ✅ Error scenarios

## 📁 Test Resources

### Test Documentation Location

```
backend/tests/
├── README.md
├── ADMIN_USER_MANAGEMENT_API_TESTS.txt
├── DASHBOARD_MODULE_API_TESTS.txt
├── FILE_UPLOAD_SERVICE_API_TESTS.txt
├── LEASE_MODULE_API_TESTS.txt
├── NOTIFICATIONS_MODULE_API_TESTS.txt
├── PAYMENT_SYSTEM_API_TESTS.txt
├── PROPERTY_MODULE_API_TESTS.txt
├── PROPERTY_REVIEWS_MODULE_API_TESTS.txt
├── RENTAL_APPLICATIONS_MODULE_API_TESTS.txt
├── USER_PROFILE_MODULE_API_TESTS.txt
└── VERIFICATION_TESTS.txt
```

### Test Code Location

```
backend/src/test/java/
└── com/gharsaathi/
    ├── property/
    ├── rental/application/
    ├── lease/
    └── ... (tests organized by module)
```

## 🔧 Running Tests

### Run All Unit Tests

```bash
cd backend
./mvnw test
```

### Run Specific Test Class

```bash
./mvnw test -Dtest=PropertyServiceTest
```

### Run with Coverage

```bash
./mvnw test jacoco:report
```

### API Testing with Postman

1. Import collection from `backend/tests/`
2. Set environment variables
3. Run collection

## ✅ Test Checklist

### Before Committing Code

- [ ] All unit tests pass
- [ ] New code has unit tests
- [ ] API endpoints manually tested
- [ ] No console errors
- [ ] Code coverage acceptable

### Before Releasing

- [ ] All tests passing
- [ ] Integration tests complete
- [ ] API tests verified
- [ ] Performance acceptable
- [ ] Security tests passed

## 📝 API Test Cases Summary

### Modules Tested (60+ endpoints)

1. ✅ **Authentication** (7 tests)
   - Register, Login, Refresh, Logout, etc.

2. ✅ **Property Management** (12 tests)
   - CRUD operations, search, filters

3. ✅ **Rental Applications** (8 tests)
   - Submit, approve, reject, withdraw

4. ✅ **Lease Management** (7 tests)
   - Create, view, terminate

5. ✅ **Payment System** (10 tests)
   - Generate, confirm, late fees

6. ✅ **User Profile** (5 tests)
   - View, update, password change

7. ✅ **Admin Management** (8 tests)
   - User management, suspension

8. ✅ **File Upload** (5 tests)
   - Upload, download, delete

9. ✅ **Dashboard Analytics** (6 tests)
   - Tenant, Landlord, Admin dashboards

## 🔗 Quick Reference

### Write a Unit Test

```java
@Test
void testCreateProperty() {
    // Arrange
    Property property = new Property();
    when(propertyRepository.save(any())).thenReturn(property);
    
    // Act
    Property result = propertyService.createProperty(dto);
    
    // Assert
    assertNotNull(result);
    verify(propertyRepository).save(any());
}
```

### Run API Test (cURL)

```bash
# Login
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"user@example.com","password":"password"}'

# Use Token
curl -X GET http://localhost:8080/api/v1/properties \
  -H "Authorization: Bearer <token>"
```

## 🔗 Related Documentation

- [API Testing Details](./api-testing.md)
- [API Documentation](../03-api/)
- [Architecture](../02-architecture/)

---

**Test Framework**: JUnit 5 + Mockito  
**API Tests**: 60+ endpoints documented  
**Coverage Goal**: 70% (unit tests)  
**Last Updated**: January 28, 2026
