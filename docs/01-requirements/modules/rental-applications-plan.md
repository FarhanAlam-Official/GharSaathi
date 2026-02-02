# RENTAL APPLICATIONS MODULE - IMPLEMENTATION PLAN
## GharSaathi Project - Rental Application Management System

**Module:** Rental Applications  
**Version:** 1.0.0  
**Created:** January 27, 2026  
**Dependencies:** Property Management Module, Authentication System

---

## MODULE OVERVIEW

The Rental Applications Module enables tenants to apply for properties and landlords to manage incoming applications. This module bridges the gap between property browsing and actual rental agreements.

**Core Features:**
- Tenants can submit rental applications for available properties
- Landlords receive and manage applications for their properties
- Application workflow: Submit → Pending → Approved/Rejected
- Application history tracking for both tenants and landlords
- Prevent duplicate applications for the same property
- Automatic property status updates when application is approved

---

## DATABASE SCHEMA

### Table: rental_applications

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Unique application ID |
| property_id | BIGINT | FOREIGN KEY → properties(id), NOT NULL | Property being applied for |
| tenant_id | BIGINT | FOREIGN KEY → users(id), NOT NULL | Tenant submitting application |
| status | ENUM | NOT NULL, DEFAULT 'PENDING' | Application status |
| message | TEXT | NULL | Tenant's message to landlord |
| move_in_date | DATE | NULL | Desired move-in date |
| lease_duration_months | INT | NULL | Requested lease duration |
| number_of_occupants | INT | NULL | Number of people moving in |
| employment_status | VARCHAR(100) | NULL | Tenant's employment status |
| monthly_income | DECIMAL(10,2) | NULL | Tenant's monthly income |
| has_pets | BOOLEAN | DEFAULT false | Whether tenant has pets |
| emergency_contact_name | VARCHAR(100) | NULL | Emergency contact name |
| emergency_contact_phone | VARCHAR(20) | NULL | Emergency contact phone |
| landlord_response | TEXT | NULL | Landlord's response/notes |
| reviewed_at | DATETIME | NULL | When landlord reviewed |
| created_at | DATETIME | NOT NULL | Application submission time |
| updated_at | DATETIME | NOT NULL | Last update time |

**Indexes:**
- idx_property_id: (property_id)
- idx_tenant_id: (tenant_id)
- idx_status: (status)
- idx_created_at: (created_at DESC)
- unique_active_application: (property_id, tenant_id, status) WHERE status IN ('PENDING', 'APPROVED')

**Constraints:**
- unique_active_application: Prevent duplicate active applications
- check_occupants: number_of_occupants > 0
- check_lease_duration: lease_duration_months > 0

---

## IMPLEMENTATION STEPS

### ✅ **Step 1-2: Entity Models and Enums** (2 files)

**1.1 Create ApplicationStatus Enum**
- File: `src/main/java/com/gharsaathi/application/model/ApplicationStatus.java`
- Values: PENDING, APPROVED, REJECTED, WITHDRAWN, CANCELLED
- Purpose: Track application lifecycle states

**1.2 Create RentalApplication Entity**
- File: `src/main/java/com/gharsaathi/application/model/RentalApplication.java`
- Relationships:
  - ManyToOne → Property (property)
  - ManyToOne → User (tenant)
- Annotations: @Entity, @Table, @ManyToOne, @Enumerated, @PrePersist, @PreUpdate
- Fields: All from schema above

---

### ✅ **Step 3-5: DTOs (Data Transfer Objects)** (3 files)

**3.1 CreateApplicationRequest DTO**
- File: `src/main/java/com/gharsaathi/common/dto/CreateApplicationRequest.java`
- Fields: propertyId, message, moveInDate, leaseDurationMonths, numberOfOccupants, employmentStatus, monthlyIncome, hasPets, emergencyContactName, emergencyContactPhone
- Validation:
  - @NotNull(propertyId, moveInDate, leaseDurationMonths, numberOfOccupants)
  - @Size(message: max=2000, employmentStatus: max=100)
  - @Min(leaseDurationMonths: 1, numberOfOccupants: 1, monthlyIncome: 0)
  - @Pattern(emergencyContactPhone: valid phone format)

**3.2 ApplicationResponse DTO**
- File: `src/main/java/com/gharsaathi/common/dto/ApplicationResponse.java`
- Fields: All application fields + property summary + tenant info
- Nested classes:
  - PropertySummary (id, title, address, city, price, landlordId, landlordName)
  - TenantInfo (id, fullName, email, phoneNumber)
- Purpose: Complete application details for landlords

**3.3 ApplicationListResponse DTO**
- File: `src/main/java/com/gharsaathi/common/dto/ApplicationListResponse.java`
- Fields: applications (List<ApplicationSummary>), pagination info
- ApplicationSummary: Lightweight version for list views
- Purpose: Paginated application lists

---

### ✅ **Step 6: Repository** (1 file)

**6.1 Create RentalApplicationRepository**
- File: `src/main/java/com/gharsaathi/application/repository/RentalApplicationRepository.java`
- Extends: JpaRepository<RentalApplication, Long>
- Custom Queries:
  ```java
  // Find all applications for a property
  List<RentalApplication> findByPropertyIdOrderByCreatedAtDesc(Long propertyId);
  
  // Find all applications by tenant
  List<RentalApplication> findByTenantIdOrderByCreatedAtDesc(Long tenantId);
  
  // Find by property and tenant
  Optional<RentalApplication> findByPropertyIdAndTenantId(Long propertyId, Long tenantId);
  
  // Check for existing active application
  boolean existsByPropertyIdAndTenantIdAndStatusIn(Long propertyId, Long tenantId, List<ApplicationStatus> statuses);
  
  // Count applications by status for a property
  long countByPropertyIdAndStatus(Long propertyId, ApplicationStatus status);
  ```

---

### ✅ **Step 7: Service Layer** (1 file)

**7.1 Create RentalApplicationService**
- File: `src/main/java/com/gharsaathi/application/service/RentalApplicationService.java`
- Annotations: @Service, @Transactional, @RequiredArgsConstructor

**Business Logic Methods:**

```java
// Tenant submits application
ApplicationResponse submitApplication(CreateApplicationRequest request, Long tenantId)
  - Validate property exists and is AVAILABLE
  - Check tenant doesn't have active application for this property
  - Check property isn't owned by the tenant (can't apply to own property)
  - Create and save application with status PENDING
  - Return ApplicationResponse

// Tenant gets their applications (with filters)
ApplicationListResponse getMyApplications(Long tenantId, ApplicationStatus status, int page, int size)
  - Fetch tenant's applications with optional status filter
  - Return paginated list

// Tenant views specific application details
ApplicationResponse getMyApplicationById(Long applicationId, Long tenantId)
  - Fetch application
  - Verify ownership (tenantId matches)
  - Return details

// Tenant withdraws application
ApplicationResponse withdrawApplication(Long applicationId, Long tenantId)
  - Fetch application
  - Verify ownership and status is PENDING
  - Update status to WITHDRAWN
  - Save and return

// Landlord views applications for their property
ApplicationListResponse getApplicationsForProperty(Long propertyId, Long landlordId, ApplicationStatus status, int page, int size)
  - Verify property ownership
  - Fetch applications with optional status filter
  - Return paginated list

// Landlord views application details
ApplicationResponse getApplicationDetails(Long applicationId, Long landlordId)
  - Fetch application
  - Verify landlord owns the property
  - Return details

// Landlord approves application
ApplicationResponse approveApplication(Long applicationId, Long landlordId, String response)
  - Fetch application and verify ownership
  - Check status is PENDING
  - Update status to APPROVED
  - Set landlord_response and reviewed_at
  - Update property status to RENTED (using PropertyService)
  - Reject other pending applications for same property
  - Save and return

// Landlord rejects application
ApplicationResponse rejectApplication(Long applicationId, Long landlordId, String response)
  - Fetch application and verify ownership
  - Check status is PENDING
  - Update status to REJECTED
  - Set landlord_response and reviewed_at
  - Save and return

// Get application statistics for landlord dashboard
Map<String, Long> getApplicationStatistics(Long landlordId)
  - Count PENDING, APPROVED, REJECTED applications across all properties
  - Return as map
```

---

### ✅ **Step 8: Controller Layer** (1 file)

**8.1 Create RentalApplicationController**
- File: `src/main/java/com/gharsaathi/application/controller/RentalApplicationController.java`
- Base Path: `/api`
- Annotations: @RestController, @RequestMapping, @RequiredArgsConstructor, @Slf4j, @CrossOrigin

**Endpoints:**

**Tenant Endpoints** (`/api/tenant/applications`)
```java
POST /api/tenant/applications - Submit application
GET /api/tenant/applications - Get my applications (with status filter)
GET /api/tenant/applications/{id} - Get application details
DELETE /api/tenant/applications/{id} - Withdraw application
```

**Landlord Endpoints** (`/api/landlord/applications`)
```java
GET /api/landlord/applications/property/{propertyId} - Get applications for property
GET /api/landlord/applications/{id} - Get application details
PATCH /api/landlord/applications/{id}/approve - Approve application
PATCH /api/landlord/applications/{id}/reject - Reject application
GET /api/landlord/applications/statistics - Get application statistics
```

**Response Codes:**
- 201 Created: Application submitted
- 200 OK: Get, Update operations
- 400 Bad Request: Validation errors
- 401 Unauthorized: Not authenticated
- 403 Forbidden: Access denied
- 404 Not Found: Application/Property not found
- 409 Conflict: Duplicate application, invalid state

---

### ✅ **Step 9: Exception Classes** (4 files)

**9.1 ApplicationNotFoundException**
- File: `src/main/java/com/gharsaathi/application/exception/ApplicationNotFoundException.java`
- Extends: RuntimeException
- Message: "Rental application not found with id: {id}"

**9.2 DuplicateApplicationException**
- File: `src/main/java/com/gharsaathi/application/exception/DuplicateApplicationException.java`
- Message: "You already have an active application for this property"

**9.3 ApplicationAccessDeniedException**
- File: `src/main/java/com/gharsaathi/application/exception/ApplicationAccessDeniedException.java`
- Message: "You do not have access to this application"

**9.4 InvalidApplicationStateException**
- File: `src/main/java/com/gharsaathi/application/exception/InvalidApplicationStateException.java`
- Message: "Invalid operation for application in {status} state"

---

### ✅ **Step 10: Update GlobalExceptionHandler** (1 file modification)

**10.1 Add Application Exception Handlers**
- File: `src/main/java/com/gharsaathi/common/exception/GlobalExceptionHandler.java`
- Add handlers for:
  - ApplicationNotFoundException → 404
  - DuplicateApplicationException → 409
  - ApplicationAccessDeniedException → 403
  - InvalidApplicationStateException → 409

---

### ✅ **Step 11: Update SecurityConfig** (1 file modification)

**11.1 Add Application Endpoint Security**
- File: `src/main/java/com/gharsaathi/common/security/SecurityConfig.java`
- Add matchers:
  ```java
  .requestMatchers("/api/tenant/applications/**").hasRole("TENANT")
  .requestMatchers("/api/landlord/applications/**").hasRole("LANDLORD")
  ```

---

## VALIDATION RULES

### Application Submission
- ✅ Property must exist and be AVAILABLE
- ✅ Property must not be owned by applicant
- ✅ Tenant must not have active application for property
- ✅ Move-in date must be in the future
- ✅ Lease duration must be at least 1 month
- ✅ Number of occupants must be positive
- ✅ Monthly income must be non-negative

### Application Approval
- ✅ Only property owner can approve
- ✅ Application must be in PENDING state
- ✅ Approving sets property status to RENTED
- ✅ Other pending applications auto-rejected

### Application Rejection
- ✅ Only property owner can reject
- ✅ Application must be in PENDING state

### Application Withdrawal
- ✅ Only tenant who submitted can withdraw
- ✅ Can only withdraw PENDING applications

---

## BUSINESS RULES

1. **One Active Application Per Property**: Tenant cannot have multiple PENDING or APPROVED applications for same property
2. **Property Status Sync**: When application approved, property status automatically changes to RENTED
3. **Auto-Rejection**: When one application approved, all other PENDING applications for that property are auto-rejected
4. **Owner Restriction**: Landlords cannot apply to their own properties
5. **Application Lifecycle**: PENDING → APPROVED/REJECTED/WITHDRAWN (no reversal)
6. **Timestamp Tracking**: created_at for submission, reviewed_at when landlord responds

---

## API SECURITY MATRIX

| Endpoint | Method | Role | Auth Required |
|----------|--------|------|---------------|
| Submit Application | POST | TENANT | ✅ |
| Get My Applications | GET | TENANT | ✅ |
| Get My Application Details | GET | TENANT | ✅ |
| Withdraw Application | DELETE | TENANT | ✅ |
| Get Property Applications | GET | LANDLORD | ✅ |
| Get Application Details | GET | LANDLORD | ✅ |
| Approve Application | PATCH | LANDLORD | ✅ |
| Reject Application | PATCH | LANDLORD | ✅ |
| Get Statistics | GET | LANDLORD | ✅ |

---

## SUCCESS CRITERIA

✅ All 11 implementation steps completed  
✅ Zero compilation errors  
✅ All entities, DTOs, repositories, services, controllers created  
✅ Custom exceptions properly handled  
✅ Security configuration updated  
✅ Business rules enforced  
✅ Validation working correctly  
✅ Database constraints in place  
✅ No breaking changes to existing modules  
✅ API test documentation created  
✅ All endpoints testable via Postman  

---

## TESTING PLAN

After implementation, we will create comprehensive API tests covering:
- Tenant application submission (success and failure cases)
- Duplicate application prevention
- Application withdrawal
- Landlord application viewing and filtering
- Application approval workflow
- Application rejection workflow
- Property status synchronization
- Access control verification
- Negative test cases (unauthorized access, invalid states, etc.)

---

## NOTES

- This module depends on Property Management and Authentication modules
- Property status will be automatically updated when applications are approved
- Consider adding email notifications in future iterations
- Payment integration will be handled in separate Payment Module
- Review/rating system will be separate module after rental completion

---

**END OF IMPLEMENTATION PLAN**
