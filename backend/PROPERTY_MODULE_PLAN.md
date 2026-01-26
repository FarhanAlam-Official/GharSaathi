# 🏠 Property Management Module - Implementation Plan

## 📋 Overview

This document outlines the step-by-step implementation plan for the Property Management Module in GharSaathi backend.

---

## 🎯 Module Objectives

- Enable landlords to create, update, and manage property listings
- Allow tenants to browse, search, and filter properties
- Support multiple images per property
- Implement comprehensive property details and amenities
- Provide role-based access control for property operations

---

## 📊 Implementation Steps

### **Phase 1: Data Models & Entities** 🗄️

#### ✅ Step 1: Create Property Entity
**File**: `backend/src/main/java/com/gharsaathi/property/model/Property.java`

**Fields**:
- `id` (Long) - Primary key
- `title` (String) - Property title
- `description` (Text) - Detailed description
- `propertyType` (Enum) - APARTMENT, HOUSE, ROOM, COMMERCIAL, LAND
- `status` (Enum) - AVAILABLE, RENTED, MAINTENANCE, UNAVAILABLE
- `address` (String) - Full address
- `city` (String) - City name
- `area` (String) - Area/Neighborhood
- `latitude` (Double) - GPS coordinate
- `longitude` (Double) - GPS coordinate
- `price` (BigDecimal) - Monthly rent amount
- `securityDeposit` (BigDecimal) - Security deposit amount
- `bedrooms` (Integer) - Number of bedrooms
- `bathrooms` (Integer) - Number of bathrooms
- `area` (Double) - Property area in sq ft
- `furnished` (Boolean) - Furnished status
- `parkingAvailable` (Boolean) - Parking availability
- `petsAllowed` (Boolean) - Pet policy
- `amenities` (Set<String>) - List of amenities
- `landlord` (User) - ManyToOne relationship
- `images` (List<PropertyImage>) - OneToMany relationship
- `createdAt` (LocalDateTime) - Timestamp
- `updatedAt` (LocalDateTime) - Timestamp
- `availableFrom` (LocalDate) - Available from date

#### ✅ Step 2: Create Property Enums
**Files**: 
- `backend/src/main/java/com/gharsaathi/property/model/PropertyType.java`
  - APARTMENT, HOUSE, ROOM, STUDIO, VILLA, COMMERCIAL, LAND, OTHER
  
- `backend/src/main/java/com/gharsaathi/property/model/PropertyStatus.java`
  - AVAILABLE, RENTED, PENDING, MAINTENANCE, UNAVAILABLE
  
- `backend/src/main/java/com/gharsaathi/property/model/AmenityType.java`
  - WIFI, AC, HEATING, WASHER, DRYER, DISHWASHER, GYM, POOL, GARDEN, BALCONY, ELEVATOR, SECURITY, PARKING, etc.

#### ✅ Step 3: Create PropertyImage Entity
**File**: `backend/src/main/java/com/gharsaathi/property/model/PropertyImage.java`

**Fields**:
- `id` (Long)
- `property` (Property) - ManyToOne
- `imageUrl` (String) - Image URL/path
- `isPrimary` (Boolean) - Primary image flag
- `displayOrder` (Integer) - Image ordering
- `uploadedAt` (LocalDateTime)

---

### **Phase 2: Data Transfer Objects (DTOs)** 📦

#### ✅ Step 4: Create Request DTOs
**Files**:

1. `CreatePropertyRequest.java` - For creating new properties
   - All property fields with validation annotations
   - @NotNull, @NotBlank, @Min, @Max, @Size, @Pattern
   - Nested address object (optional)

2. `UpdatePropertyRequest.java` - For updating existing properties
   - Similar to CreatePropertyRequest but all fields optional
   - Only provided fields will be updated

#### ✅ Step 5: Create Response DTOs
**Files**:

1. `PropertyResponse.java` - Basic property info for listings
   - Essential fields only (id, title, price, city, propertyType, status)
   - Primary image URL
   - Landlord basic info (id, name)

2. `PropertyDetailResponse.java` - Complete property info
   - All property fields
   - All images
   - Landlord details
   - Metadata (views, created date, etc.)

3. `PropertyListResponse.java` - Paginated list wrapper
   - List<PropertyResponse>
   - Total count
   - Current page
   - Total pages

#### ✅ Step 6: Create Search/Filter DTO
**File**: `PropertySearchCriteria.java`

**Fields**:
- `city` (String)
- `area` (String)
- `propertyType` (PropertyType)
- `minPrice` (BigDecimal)
- `maxPrice` (BigDecimal)
- `minBedrooms` (Integer)
- `maxBedrooms` (Integer)
- `minBathrooms` (Integer)
- `furnished` (Boolean)
- `parkingAvailable` (Boolean)
- `petsAllowed` (Boolean)
- `amenities` (List<String>)
- `availableFrom` (LocalDate)
- `page` (Integer)
- `size` (Integer)
- `sortBy` (String) - price, createdAt, bedrooms
- `sortDirection` (String) - ASC, DESC

---

### **Phase 3: Repository Layer** 🗃️

#### ✅ Step 7: Create PropertyRepository
**File**: `backend/src/main/java/com/gharsaathi/property/repository/PropertyRepository.java`

**Methods**:
- `findAll()` - Get all properties
- `findById(Long id)` - Get property by ID
- `findByLandlordId(Long landlordId)` - Get landlord's properties
- `findByStatus(PropertyStatus status)` - Get properties by status
- `findByCity(String city)` - Get properties by city
- `existsByIdAndLandlordId(Long id, Long landlordId)` - Check ownership
- Custom query methods for search and filtering

#### ✅ Step 8: Create PropertyImageRepository
**File**: `backend/src/main/java/com/gharsaathi/property/repository/PropertyImageRepository.java`

**Methods**:
- `findByPropertyId(Long propertyId)` - Get all images for a property
- `findByPropertyIdAndIsPrimaryTrue(Long propertyId)` - Get primary image
- `deleteByPropertyId(Long propertyId)` - Delete all property images

---

### **Phase 4: Service Layer** 🔧

#### ✅ Step 9: Create PropertyService
**File**: `backend/src/main/java/com/gharsaathi/property/service/PropertyService.java`

**Methods**:
- `createProperty(CreatePropertyRequest request, Long landlordId)` - Create property
- `updateProperty(Long id, UpdatePropertyRequest request, Long landlordId)` - Update property
- `deleteProperty(Long id, Long landlordId)` - Delete property (soft delete)
- `getPropertyById(Long id)` - Get property details
- `getAllProperties(Pageable pageable)` - Get all properties (paginated)
- `getLandlordProperties(Long landlordId, Pageable pageable)` - Get landlord's properties
- `updatePropertyStatus(Long id, PropertyStatus status, Long landlordId)` - Change status
- `verifyPropertyOwnership(Long propertyId, Long landlordId)` - Ownership check

#### ✅ Step 10: Implement Search & Filtering
**Enhanced PropertyService methods**:
- `searchProperties(PropertySearchCriteria criteria)` - Advanced search
- Build dynamic queries using JPA Specifications
- Support multiple filter combinations
- Implement pagination and sorting

---

### **Phase 5: Controller Layer** 🎮

#### ✅ Step 11: Create PropertyController
**File**: `backend/src/main/java/com/gharsaathi/property/controller/PropertyController.java`

**Endpoints**:

**Public Endpoints** (No authentication required):
- `GET /api/properties` - List all available properties (with search/filter)
- `GET /api/properties/{id}` - Get property details

**Tenant Endpoints** (TENANT role required):
- Same as public endpoints

**Landlord Endpoints** (LANDLORD role required):
- `POST /api/landlord/properties` - Create new property
- `GET /api/landlord/properties` - Get my properties
- `GET /api/landlord/properties/{id}` - Get my property details
- `PUT /api/landlord/properties/{id}` - Update my property
- `DELETE /api/landlord/properties/{id}` - Delete my property
- `PATCH /api/landlord/properties/{id}/status` - Update property status

**Admin Endpoints** (ADMIN role required):
- `GET /api/admin/properties` - Get all properties (including unavailable)
- `DELETE /api/admin/properties/{id}` - Force delete property
- `PATCH /api/admin/properties/{id}/moderate` - Moderate property

---

### **Phase 6: Exception Handling** ⚠️

#### ✅ Step 12: Create Property-Specific Exceptions
**Files**:
- `PropertyNotFoundException.java` - When property not found
- `PropertyAccessDeniedException.java` - When user lacks permission
- `InvalidPropertyDataException.java` - When validation fails
- `PropertyAlreadyRentedException.java` - When trying to modify rented property

**Update**: `GlobalExceptionHandler.java` with new exception handlers

---

### **Phase 7: Validation & Security** 🔒

#### ✅ Step 13: Add Validation
**Validation Annotations** in DTOs:
- `@NotNull`, `@NotBlank` - Required fields
- `@Size` - String length validation
- `@Min`, `@Max` - Number range validation
- `@Email` - Email format
- `@Pattern` - Regex validation for phone, postal code
- `@DecimalMin`, `@DecimalMax` - Price validation
- Custom validators for business rules

#### ✅ Step 14: Update SecurityConfig
**File**: `backend/src/main/java/com/gharsaathi/common/security/SecurityConfig.java`

**Add Authorization Rules**:
```java
.requestMatchers("/api/properties/**").permitAll()
.requestMatchers("/api/landlord/properties/**").hasRole("LANDLORD")
.requestMatchers("/api/admin/properties/**").hasRole("ADMIN")
```

---

### **Phase 8: Testing & Verification** ✅

#### ✅ Step 15: Testing Checklist

**Unit Tests**:
- [ ] PropertyService tests
- [ ] PropertyRepository tests
- [ ] DTO validation tests

**Integration Tests**:
- [ ] Create property (Landlord)
- [ ] Update property (Landlord - own property)
- [ ] Delete property (Landlord - own property)
- [ ] List all properties (Public)
- [ ] Get property details (Public)
- [ ] Search properties with filters
- [ ] Property ownership validation
- [ ] Role-based access control
- [ ] Error handling scenarios

**Manual Testing with Postman**:
- [ ] All CRUD operations
- [ ] Search and filtering
- [ ] Pagination and sorting
- [ ] Permission checks
- [ ] Error responses

---

## 📋 Database Schema

### **Properties Table**
```sql
CREATE TABLE properties (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    property_type VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    address VARCHAR(500) NOT NULL,
    city VARCHAR(100) NOT NULL,
    area VARCHAR(100),
    latitude DECIMAL(10, 8),
    longitude DECIMAL(11, 8),
    price DECIMAL(10, 2) NOT NULL,
    security_deposit DECIMAL(10, 2),
    bedrooms INT,
    bathrooms INT,
    property_area DOUBLE,
    furnished BOOLEAN DEFAULT FALSE,
    parking_available BOOLEAN DEFAULT FALSE,
    pets_allowed BOOLEAN DEFAULT FALSE,
    amenities JSON,
    landlord_id BIGINT NOT NULL,
    available_from DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (landlord_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_city (city),
    INDEX idx_status (status),
    INDEX idx_landlord (landlord_id),
    INDEX idx_price (price),
    INDEX idx_property_type (property_type)
);
```

### **Property Images Table**
```sql
CREATE TABLE property_images (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    property_id BIGINT NOT NULL,
    image_url VARCHAR(500) NOT NULL,
    is_primary BOOLEAN DEFAULT FALSE,
    display_order INT DEFAULT 0,
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (property_id) REFERENCES properties(id) ON DELETE CASCADE,
    INDEX idx_property (property_id)
);
```

---

## 🎯 Expected API Endpoints Summary

| Method | Endpoint | Role | Description |
|--------|----------|------|-------------|
| GET | `/api/properties` | Public | List all available properties |
| GET | `/api/properties/{id}` | Public | Get property details |
| POST | `/api/landlord/properties` | Landlord | Create new property |
| GET | `/api/landlord/properties` | Landlord | Get my properties |
| GET | `/api/landlord/properties/{id}` | Landlord | Get my property details |
| PUT | `/api/landlord/properties/{id}` | Landlord | Update my property |
| DELETE | `/api/landlord/properties/{id}` | Landlord | Delete my property |
| PATCH | `/api/landlord/properties/{id}/status` | Landlord | Update property status |
| GET | `/api/admin/properties` | Admin | Get all properties |
| DELETE | `/api/admin/properties/{id}` | Admin | Force delete property |

---

## 📦 Expected Project Structure

```
backend/src/main/java/com/gharsaathi/property/
├── controller/
│   └── PropertyController.java
├── model/
│   ├── Property.java
│   ├── PropertyImage.java
│   ├── PropertyType.java (enum)
│   ├── PropertyStatus.java (enum)
│   └── AmenityType.java (enum)
├── repository/
│   ├── PropertyRepository.java
│   └── PropertyImageRepository.java
└── service/
    └── PropertyService.java

backend/src/main/java/com/gharsaathi/common/
├── dto/
│   ├── CreatePropertyRequest.java
│   ├── UpdatePropertyRequest.java
│   ├── PropertyResponse.java
│   ├── PropertyDetailResponse.java
│   ├── PropertyListResponse.java
│   └── PropertySearchCriteria.java
└── exception/
    ├── PropertyNotFoundException.java
    ├── PropertyAccessDeniedException.java
    ├── InvalidPropertyDataException.java
    └── PropertyAlreadyRentedException.java
```

---

## 🚀 Next Steps After Completion

1. **Image Upload Service** - Implement file upload functionality
2. **Property Favorites** - Allow tenants to save properties
3. **Property Analytics** - Track views, inquiries
4. **Email Notifications** - Notify landlords of new inquiries
5. **Property Reviews** - Add rating and review system

---

## ⚡ Quick Start Commands

```bash
# Start MySQL database (make sure it's running on port 3307)
# Backend will create tables automatically with JPA

# Run the Spring Boot application
cd backend
./mvnw spring-boot:run

# Test with Postman or curl
curl -X GET http://localhost:8080/api/properties
```

---

**Status**: Ready for Implementation 🎯  
**Estimated Time**: 6-8 hours for complete module  
**Last Updated**: January 27, 2026
