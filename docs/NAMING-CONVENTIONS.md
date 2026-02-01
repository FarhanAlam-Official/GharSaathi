# Naming Conventions - GharSaathi Project

> Comprehensive naming conventions for code, documentation, and project resources

## 📋 Table of Contents

1. [Documentation Files](#documentation-files)
2. [Java/Backend Code](#javabackend-code)
3. [Frontend/TypeScript Code](#frontendtypescript-code)
4. [Database Conventions](#database-conventions)
5. [API Conventions](#api-conventions)
6. [Git Conventions](#git-conventions)

---

## Documentation Files

### File Names

- **Format**: `kebab-case.md`
- **Examples**:
  - ✅ `system-architecture.md`
  - ✅ `api-overview.md`
  - ✅ `user-roles.md`
  - ❌ `SystemArchitecture.md`
  - ❌ `api_overview.md`

### Folder Names

- **Format**: `00-prefix-name/` (with numeric prefix for ordering)
- **Examples**:
  - ✅ `00-overview/`
  - ✅ `03-api/`
  - ✅ `11-maintenance/`
  - ❌ `Overview/`
  - ❌ `API_Docs/`

### Section Headings

- **Level 1**: `# Title Case With Major Words Capitalized`
- **Level 2**: `## Title Case for Subsections`
- **Level 3**: `### Sentence case for details`

---

## Java/Backend Code

### Package Names

- **Format**: All lowercase, reverse domain notation
- **Pattern**: `com.gharsaathi.<module>.<layer>`
- **Examples**:

  ```java
  com.gharsaathi.property.controller
  com.gharsaathi.rental.application.service
  com.gharsaathi.auth.security
  com.gharsaathi.common.exception
  ```

### Class Names

- **Format**: PascalCase (UpperCamelCase)
- **Patterns**:
  - **Controllers**: `*Controller` (e.g., `PropertyController`)
  - **Services**: `*Service` (e.g., `PropertyService`)
  - **Repositories**: `*Repository` (e.g., `PropertyRepository`)
  - **Models/Entities**: Singular noun (e.g., `Property`, `User`)
  - **DTOs**: `*Request`, `*Response`, `*DTO` (e.g., `CreatePropertyRequest`)
  - **Exceptions**: `*Exception` (e.g., `PropertyNotFoundException`)
  - **Enums**: Singular noun (e.g., `PropertyStatus`, `UserRole`)

### Method Names

- **Format**: camelCase
- **Patterns**:
  - **CRUD Operations**: `create*`, `get*`, `update*`, `delete*`
  - **Query Methods**: `find*`, `list*`, `count*`, `exists*`
  - **Boolean Methods**: `is*`, `has*`, `can*`, `should*`
  - **Examples**:

    ```java
    createProperty()
    getPropertyById()
    updateProperty()
    deleteProperty()
    findPropertiesByCity()
    isPropertyAvailable()
    hasActiveApplications()
    ```

### Variable Names

- **Format**: camelCase
- **Constants**: `UPPER_SNAKE_CASE`
- **Examples**:

  ```java
  private String propertyTitle;
  private int numberOfRooms;
  public static final int MAX_UPLOAD_SIZE = 10485760;
  public static final String DEFAULT_CURRENCY = "NPR";
  ```

### Interface Names

- **Format**: PascalCase (no "I" prefix)
- **Examples**:
  - ✅ `PaymentService`
  - ✅ `PropertyRepository`
  - ❌ `IPaymentService`
  - ❌ `IPropertyRepository`

---

## Frontend/TypeScript Code

### File Names

- **Components**: `kebab-case.tsx` (e.g., `property-card.tsx`)
- **Pages**: `kebab-case.tsx` or special names (`page.tsx`, `layout.tsx`)
- **Utilities**: `kebab-case.ts` (e.g., `format-currency.ts`)
- **Types**: `kebab-case.ts` (e.g., `property-types.ts`)

### Component Names

- **Format**: PascalCase
- **Examples**:

  ```typescript
  export function PropertyCard() {}
  export default function DashboardSidebar() {}
  export const UserAvatar = () => {}
  ```

### Function Names

- **Format**: camelCase
- **Examples**:

  ```typescript
  function formatCurrency() {}
  const handleSubmit = () => {}
  async function fetchProperties() {}
  ```

### Variable and Constant Names

- **Variables**: camelCase
- **Constants**: UPPER_SNAKE_CASE
- **Boolean Variables**: `is*`, `has*`, `should*`
- **Examples**:

  ```typescript
  const propertyList = [];
  const isLoading = false;
  const hasError = true;
  const MAX_FILE_SIZE = 5242880;
  const API_BASE_URL = 'http://localhost:8080';
  ```

### Type and Interface Names

- **Format**: PascalCase
- **Patterns**:
  - **Types**: Descriptive name (e.g., `Property`, `User`)
  - **Interfaces**: Descriptive name (e.g., `PropertyCardProps`)
  - **Props**: `*Props` suffix (e.g., `DashboardProps`)
- **Examples**:

  ```typescript
  interface PropertyCardProps {
    property: Property;
    onSelect: (id: string) => void;
  }
  
  type UserRole = 'TENANT' | 'LANDLORD' | 'ADMIN';
  ```

### CSS Class Names

- **Format**: kebab-case (following Tailwind conventions)
- **Examples**:

  ```css
  .property-card { }
  .user-avatar { }
  .dashboard-header { }
  ```

---

## Database Conventions

### Table Names

- **Format**: `snake_case`, plural
- **Examples**:
  - ✅ `properties`
  - ✅ `rental_applications`
  - ✅ `user_profiles`
  - ❌ `Property`
  - ❌ `rentalApplication`

### Column Names

- **Format**: `snake_case`
- **Patterns**:
  - **Primary Key**: `id`
  - **Foreign Keys**: `<entity>_id` (e.g., `property_id`, `user_id`)
  - **Timestamps**: `created_at`, `updated_at`
  - **Boolean**: `is_*` or `has_*` (e.g., `is_active`, `has_parking`)
- **Examples**:

  ```sql
  id
  property_title
  monthly_rent
  user_id
  created_at
  updated_at
  is_active
  has_parking
  ```

### Index Names

- **Format**: `idx_<table>_<column(s)>`
- **Examples**:
  - `idx_properties_city`
  - `idx_users_email`
  - `idx_rental_applications_status`

### Constraint Names

- **Primary Key**: `pk_<table>`
- **Foreign Key**: `fk_<table>_<referenced_table>`
- **Unique**: `uk_<table>_<column>`
- **Check**: `ck_<table>_<column>`
- **Examples**:
  - `pk_properties`
  - `fk_properties_users`
  - `uk_users_email`
  - `ck_properties_rent_positive`

---

## API Conventions

### Endpoint Paths

- **Format**: kebab-case, plural nouns
- **Pattern**: `/api/<version>/<resource>/<action>`
- **Examples**:

  ```
  GET    /api/v1/properties
  POST   /api/v1/properties
  GET    /api/v1/properties/{id}
  PUT    /api/v1/properties/{id}
  DELETE /api/v1/properties/{id}
  POST   /api/v1/rental-applications
  GET    /api/v1/rental-applications/my-applications
  ```

### HTTP Methods

- **GET**: Retrieve resources (idempotent, safe)
- **POST**: Create new resources
- **PUT**: Update entire resource
- **PATCH**: Partial update
- **DELETE**: Remove resource

### Request/Response Naming

- **Request DTOs**: `Create*Request`, `Update*Request`
- **Response DTOs**: `*Response`, `*ListResponse`
- **Examples**:
  - `CreatePropertyRequest`
  - `UpdatePropertyRequest`
  - `PropertyResponse`
  - `PropertyListResponse`

### Status Codes

- **200**: OK (successful GET, PUT, PATCH)
- **201**: Created (successful POST)
- **204**: No Content (successful DELETE)
- **400**: Bad Request
- **401**: Unauthorized
- **403**: Forbidden
- **404**: Not Found
- **500**: Internal Server Error

---

## Git Conventions

### Branch Names

- **Format**: `<type>/<short-description>`
- **Types**: `feature`, `bugfix`, `hotfix`, `release`, `docs`
- **Examples**:
  - `feature/payment-integration`
  - `bugfix/fix-login-validation`
  - `docs/update-api-documentation`
  - `hotfix/security-patch`

### Commit Messages

- **Format**: `<type>: <description>`
- **Types**:
  - `feat`: New feature
  - `fix`: Bug fix
  - `docs`: Documentation changes
  - `style`: Code style changes (formatting)
  - `refactor`: Code refactoring
  - `test`: Adding or updating tests
  - `chore`: Maintenance tasks
- **Examples**:

  ```
  feat: add payment confirmation endpoint
  fix: resolve JWT token expiration issue
  docs: update API documentation for properties
  refactor: simplify property search logic
  test: add unit tests for property service
  ```

### Tag Names

- **Format**: `v<major>.<minor>.<patch>`
- **Examples**:
  - `v1.0.0`
  - `v1.2.3`
  - `v2.0.0-beta.1`

---

## General Best Practices

### Do's ✅

- Use descriptive, meaningful names
- Be consistent across the codebase
- Follow established patterns
- Use full words (avoid abbreviations unless widely known)
- Keep names readable and pronounceable

### Don'ts ❌

- Don't use single-letter variables (except loop counters)
- Don't use ambiguous abbreviations
- Don't mix naming conventions
- Don't use Hungarian notation
- Don't include type information in names (e.g., `strName`, `intCount`)

### Common Abbreviations (Acceptable)

- `id` - identifier
- `dto` - data transfer object
- `api` - application programming interface
- `url` - uniform resource locator
- `jwt` - JSON web token
- `http` - hypertext transfer protocol
- `sql` - structured query language
- `max`, `min` - maximum, minimum
- `num` - number (in specific contexts)

---

## Validation Checklist

Before committing code, ensure:

- [ ] All file names follow conventions
- [ ] All class/interface names are PascalCase
- [ ] All method/function names are camelCase
- [ ] All variables follow appropriate conventions
- [ ] Database schema follows snake_case
- [ ] API endpoints follow REST conventions
- [ ] Git commits follow message format
- [ ] Documentation files use kebab-case

---

**Last Updated**: January 28, 2026  
**Version**: 1.0.0
