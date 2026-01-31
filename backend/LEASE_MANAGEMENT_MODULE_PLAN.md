# 📋 LEASE MANAGEMENT MODULE - IMPLEMENTATION PLAN

## GharSaathi Project - Lease Agreement Management System

**Module:** Lease Management  
**Version:** 1.0.0  
**Created:** January 27, 2026  
**Dependencies:** Property Management Module, Rental Applications Module, Authentication System

---

## 🎯 MODULE OVERVIEW

The Lease Management Module formalizes the rental agreement between landlord and tenant after an application is approved. It manages the entire lease lifecycle from creation to expiration/termination.

**Core Features:**

- **Automatic lease creation** when application is approved
- Track lease start date, end date, and status
- Store lease terms (rent amount, security deposit, special conditions)
- Monitor active leases and upcoming expirations
- Handle lease renewal and early termination
- Maintain historical lease records
- Provide lease information to both landlord and tenant

---

## 🔄 WORKFLOW INTEGRATION

### Current Workflow (BEFORE Lease Module)

```
1. Tenant submits application → Application Status: PENDING
2. Landlord approves → Application Status: APPROVED + Property Status: RENTED
3. ❌ NO FORMAL LEASE EXISTS
```

### Enhanced Workflow (AFTER Lease Module)

```
1. Tenant submits application → Application Status: PENDING
2. Landlord approves → Application Status: APPROVED
3. ✅ AUTO-CREATE LEASE → Lease Status: ACTIVE
4. Property Status: RENTED (already implemented)
5. Lease tracks rental period and terms
6. On expiration → Lease Status: EXPIRED → Property Status: AVAILABLE (if not renewed)
```

---

## 🗄️ DATABASE SCHEMA

### Table: leases

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Unique lease ID |
| property_id | BIGINT | FOREIGN KEY → properties(id), NOT NULL | Property being leased |
| tenant_id | BIGINT | FOREIGN KEY → users(id), NOT NULL | Tenant renting the property |
| landlord_id | BIGINT | FOREIGN KEY → users(id), NOT NULL | Property owner |
| application_id | BIGINT | FOREIGN KEY → rental_applications(id), UNIQUE | Original application (one-to-one) |
| lease_start_date | DATE | NOT NULL | Lease start date |
| lease_end_date | DATE | NOT NULL | Lease end date |
| monthly_rent | DECIMAL(10,2) | NOT NULL | Monthly rent amount |
| security_deposit | DECIMAL(10,2) | NOT NULL | Security deposit amount |
| status | ENUM | NOT NULL, DEFAULT 'ACTIVE' | Lease status |
| number_of_occupants | INT | NULL | Number of people living in property |
| special_terms | TEXT | NULL | Additional lease terms and conditions |
| auto_renew | BOOLEAN | DEFAULT false | Whether lease auto-renews |
| early_termination_notice_days | INT | DEFAULT 30 | Notice period for early termination |
| termination_date | DATE | NULL | Actual termination date if ended early |
| termination_reason | TEXT | NULL | Reason for early termination |
| created_at | DATETIME | NOT NULL | Lease creation timestamp |
| updated_at | DATETIME | NOT NULL | Last update timestamp |
| signed_at | DATETIME | NULL | When both parties signed (future feature) |

**Indexes:**

- idx_property_id: (property_id)
- idx_tenant_id: (tenant_id)
- idx_landlord_id: (landlord_id)
- idx_application_id: (application_id)
- idx_status: (status)
- idx_lease_end_date: (lease_end_date)
- idx_active_property: (property_id, status) WHERE status = 'ACTIVE'

**Constraints:**

- unique_active_lease_per_property: Only one ACTIVE lease per property
- check_dates: lease_end_date > lease_start_date
- check_rent: monthly_rent > 0
- check_deposit: security_deposit >= 0
- check_occupants: number_of_occupants > 0

---

## 📊 LEASE STATUS ENUM

```java
public enum LeaseStatus {
    DRAFT,      // Lease created but not yet active (future feature)
    ACTIVE,     // Lease is currently active
    EXPIRED,    // Lease ended naturally (lease_end_date passed)
    TERMINATED, // Lease ended early by either party
    RENEWED     // Lease was renewed (for historical tracking)
}
```

---

## 🔗 INTEGRATION POINTS WITH EXISTING MODULES

### 1. ✅ Rental Application Module Integration

**Location:** `RentalApplicationService.approveApplication()`  
**Current Code:**

```java
// Update application status
application.setStatus(ApplicationStatus.APPROVED);

// Update property status to RENTED
property.setStatus(PropertyStatus.RENTED);
propertyRepository.save(property);
```

**⚠️ MODIFICATION REQUIRED:**

```java
// Update application status
application.setStatus(ApplicationStatus.APPROVED);

// ✅ CREATE LEASE (NEW CODE)
Lease lease = leaseService.createLeaseFromApplication(application);

// Update property status to RENTED (existing code - KEEP)
property.setStatus(PropertyStatus.RENTED);
propertyRepository.save(property);
```

**Files to Modify:**

- ✏️ `RentalApplicationService.java` - Add `LeaseService` dependency and call lease creation

---

### 2. ✅ Property Status Management

**Current Behavior:**

- When application approved → Property Status: RENTED ✅ (KEEP THIS)
- When property deleted → Cannot delete if RENTED ✅ (KEEP THIS)

**New Behavior:**

- When lease expires → Property Status: AVAILABLE (NEW)
- When lease terminated → Property Status: AVAILABLE (NEW)
- When lease renewed → Property Status: RENTED (already RENTED, no change)

**Files to Modify:**

- ✏️ `LeaseService.java` - Update property status when lease status changes

---

### 3. ✅ Future Payment Module Integration

**Lease provides data for payments:**

- Monthly rent amount
- Payment due dates (start date + monthly intervals)
- Security deposit tracking
- Lease duration for payment schedule

**No modifications needed now** - Payment module will reference Lease entity.

---

## 🏗️ IMPLEMENTATION PLAN - DETAILED CHECKLIST

### **PHASE 1: ENTITY & ENUMS** ✅

#### ☐ Task 1.1: Create LeaseStatus Enum

- **File:** `src/main/java/com/gharsaathi/lease/model/LeaseStatus.java`
- **Values:** DRAFT, ACTIVE, EXPIRED, TERMINATED, RENEWED
- **Time:** 5 minutes

#### ☐ Task 1.2: Create Lease Entity

- **File:** `src/main/java/com/gharsaathi/lease/model/Lease.java`
- **Relationships:**
  - `@ManyToOne` → Property
  - `@ManyToOne` → User (tenant)
  - `@ManyToOne` → User (landlord)
  - `@OneToOne` → RentalApplication (optional, can be null for manual leases)
- **Annotations:** @Entity, @Table, @PrePersist, @PreUpdate
- **Helper Methods:**
  - `isActive()` - Check if status is ACTIVE
  - `isExpired()` - Check if current date > end date
  - `getDaysRemaining()` - Calculate days until expiration
  - `getDurationInMonths()` - Calculate total lease duration
- **Time:** 20 minutes

---

### **PHASE 2: REPOSITORY LAYER** ✅

#### ☐ Task 2.1: Create LeaseRepository

- **File:** `src/main/java/com/gharsaathi/lease/repository/LeaseRepository.java`
- **Extends:** `JpaRepository<Lease, Long>`
- **Custom Queries:**

  ```java
  Optional<Lease> findByApplicationId(Long applicationId);
  List<Lease> findByPropertyId(Long propertyId);
  List<Lease> findByPropertyIdAndStatus(Long propertyId, LeaseStatus status);
  List<Lease> findByTenantId(Long tenantId);
  List<Lease> findByTenantIdAndStatus(Long tenantId, LeaseStatus status);
  List<Lease> findByLandlordId(Long landlordId);
  List<Lease> findByLandlordIdAndStatus(Long landlordId, LeaseStatus status);
  Page<Lease> findByTenantId(Long tenantId, Pageable pageable);
  Page<Lease> findByLandlordId(Long landlordId, Pageable pageable);
  
  // For expiration monitoring
  List<Lease> findByStatusAndLeaseEndDateBefore(LeaseStatus status, LocalDate date);
  List<Lease> findByStatusAndLeaseEndDateBetween(LeaseStatus status, LocalDate start, LocalDate end);
  
  // Check if active lease exists
  boolean existsByPropertyIdAndStatus(Long propertyId, LeaseStatus status);
  ```

- **Time:** 15 minutes

---

### **PHASE 3: EXCEPTION CLASSES** ✅

#### ☐ Task 3.1: Create Lease Exceptions

- **Files:**
  - `src/main/java/com/gharsaathi/lease/exception/LeaseNotFoundException.java`
  - `src/main/java/com/gharsaathi/lease/exception/LeaseAlreadyExistsException.java`
  - `src/main/java/com/gharsaathi/lease/exception/LeaseAccessDeniedException.java`
  - `src/main/java/com/gharsaathi/lease/exception/InvalidLeaseStateException.java`
  - `src/main/java/com/gharsaathi/lease/exception/InvalidLeaseDateException.java`
- **Time:** 10 minutes

#### ☐ Task 3.2: Update GlobalExceptionHandler

- **File:** `src/main/java/com/gharsaathi/common/exception/GlobalExceptionHandler.java`
- **Add handlers for:**
  - `LeaseNotFoundException` → 404
  - `LeaseAlreadyExistsException` → 409
  - `LeaseAccessDeniedException` → 403
  - `InvalidLeaseStateException` → 400
  - `InvalidLeaseDateException` → 400
- **Time:** 10 minutes

---

### **PHASE 4: DTO CLASSES** ✅

#### ☐ Task 4.1: Create CreateLeaseRequest DTO (Manual Lease Creation)

- **File:** `src/main/java/com/gharsaathi/common/dto/CreateLeaseRequest.java`
- **Fields:**
  - propertyId (Long) - @NotNull
  - tenantId (Long) - @NotNull
  - leaseStartDate (LocalDate) - @NotNull, @Future or @Present
  - leaseEndDate (LocalDate) - @NotNull, @Future
  - monthlyRent (BigDecimal) - @NotNull, @Positive
  - securityDeposit (BigDecimal) - @NotNull, @PositiveOrZero
  - numberOfOccupants (Integer) - @Positive
  - specialTerms (String) - @Size(max=5000)
  - autoRenew (Boolean) - Optional
- **Validation:** Custom validator to ensure endDate > startDate
- **Time:** 15 minutes

#### ☐ Task 4.2: Create LeaseResponse DTO

- **File:** `src/main/java/com/gharsaathi/common/dto/LeaseResponse.java`
- **Fields:** All Lease fields + nested PropertyInfo, TenantInfo, LandlordInfo
- **Nested Classes:**
  - PropertyInfo (id, title, address, city)
  - TenantInfo (id, fullName, email, phoneNumber)
  - LandlordInfo (id, fullName, email, phoneNumber)
- **Time:** 20 minutes

#### ☐ Task 4.3: Create LeaseListResponse DTO

- **File:** `src/main/java/com/gharsaathi/common/dto/LeaseListResponse.java`
- **Fields:**
  - leases (List<LeaseResponse>)
  - totalElements, totalPages, currentPage, pageSize, hasNext, hasPrevious
- **Time:** 5 minutes

#### ☐ Task 4.4: Create UpdateLeaseRequest DTO (Optional fields)

- **File:** `src/main/java/com/gharsaathi/common/dto/UpdateLeaseRequest.java`
- **Fields:**
  - specialTerms (String) - Optional
  - autoRenew (Boolean) - Optional
  - earlyTerminationNoticeDays (Integer) - Optional
- **Time:** 10 minutes

#### ☐ Task 4.5: Create TerminateLeaseRequest DTO

- **File:** `src/main/java/com/gharsaathi/common/dto/TerminateLeaseRequest.java`
- **Fields:**
  - terminationDate (LocalDate) - @NotNull
  - terminationReason (String) - @NotBlank, @Size(min=10, max=1000)
- **Time:** 10 minutes

---

### **PHASE 5: SERVICE LAYER** ✅

#### ☐ Task 5.1: Create LeaseService

- **File:** `src/main/java/com/gharsaathi/lease/service/LeaseService.java`
- **Dependencies:**
  - LeaseRepository
  - PropertyRepository
  - UserRepository
  - RentalApplicationRepository

**Methods to Implement:**

```java
// ✅ Auto-create lease from approved application
LeaseResponse createLeaseFromApplication(RentalApplication application);

// Create manual lease (admin or landlord)
LeaseResponse createManualLease(CreateLeaseRequest request, Long landlordId);

// Get lease by ID
LeaseResponse getLeaseById(Long leaseId, Long userId);

// Get all leases for a tenant
LeaseListResponse getTenantLeases(Long tenantId, LeaseStatus status, int page, int size);

// Get all leases for a landlord
LeaseListResponse getLandlordLeases(Long landlordId, LeaseStatus status, int page, int size);

// Get active lease for a property
LeaseResponse getActiveLeaseForProperty(Long propertyId);

// Update lease terms
LeaseResponse updateLease(Long leaseId, UpdateLeaseRequest request, Long userId);

// Terminate lease early
LeaseResponse terminateLease(Long leaseId, TerminateLeaseRequest request, Long userId);

// Renew lease
LeaseResponse renewLease(Long leaseId, LocalDate newEndDate, Long userId);

// System task: Mark expired leases
void processExpiredLeases(); // Scheduled task

// Get leases expiring soon (for notifications)
List<LeaseResponse> getLeasesExpiringSoon(int daysAhead);

// Helper methods
private Lease getLeaseAndVerifyAccess(Long leaseId, Long userId);
private void validateLeaseDates(LocalDate startDate, LocalDate endDate);
```

- **Time:** 2 hours

---

### **PHASE 6: SCHEDULED TASKS** ✅

#### ☐ Task 6.1: Create LeaseExpirationScheduler

- **File:** `src/main/java/com/gharsaathi/lease/scheduler/LeaseExpirationScheduler.java`
- **Purpose:** Automatically mark leases as EXPIRED and update property status
- **Schedule:** Run daily at 2:00 AM
- **Task:**

  ```java
  @Scheduled(cron = "0 0 2 * * *") // 2 AM daily
  public void checkExpiredLeases() {
      leaseService.processExpiredLeases();
  }
  ```

- **Enable in SecurityConfig:** Add `@EnableScheduling`
- **Time:** 30 minutes

---

### **PHASE 7: CONTROLLER LAYER** ✅

#### ☐ Task 7.1: Create LeaseController

- **File:** `src/main/java/com/gharsaathi/lease/controller/LeaseController.java`
- **Base Path:** `/api`

**Endpoints:**

**Tenant Endpoints:**

```java
GET /api/tenant/leases - Get my leases (with status filter)
GET /api/tenant/leases/{id} - Get lease details
GET /api/tenant/leases/active - Get my active lease
```

**Landlord Endpoints:**

```java
GET /api/landlord/leases - Get leases for my properties (with status filter)
GET /api/landlord/leases/{id} - Get lease details
POST /api/landlord/leases - Create manual lease
PUT /api/landlord/leases/{id} - Update lease terms
POST /api/landlord/leases/{id}/terminate - Terminate lease early
POST /api/landlord/leases/{id}/renew - Renew lease
GET /api/landlord/properties/{propertyId}/lease/active - Get active lease for property
GET /api/landlord/leases/expiring - Get leases expiring soon
```

**Admin Endpoints (Future):**

```java
GET /api/admin/leases - Get all leases
GET /api/admin/leases/{id} - Get any lease details
```

- **Security:** Use `@PreAuthorize` for role-based access
- **Time:** 1.5 hours

---

### **PHASE 8: MODIFY EXISTING CODE** ⚠️

#### ☐ Task 8.1: Update RentalApplicationService

- **File:** `src/main/java/com/gharsaathi/rental/application/service/RentalApplicationService.java`
- **Method:** `approveApplication()`
- **Changes:**
  1. Add `LeaseService` as dependency
  2. After setting application status to APPROVED, call:

     ```java
     leaseService.createLeaseFromApplication(application);
     ```

  3. Keep existing code that updates property status to RENTED
- **⚠️ CRITICAL:** This is the main integration point!
- **Time:** 15 minutes

#### ☐ Task 8.2: Update SecurityConfig (if not already enabled)

- **File:** `src/main/java/com/gharsaathi/common/security/SecurityConfig.java`
- **Add:** `@EnableScheduling` annotation
- **Time:** 2 minutes

---

### **PHASE 9: TESTING** ✅

#### ☐ Task 9.1: Create API Test Document

- **File:** `backend/tests/LEASE_MODULE_API_TESTS.txt`
- **Test Scenarios:**
  1. Approve application → Verify lease auto-created
  2. Get tenant leases
  3. Get landlord leases
  4. Get active lease for property
  5. Update lease terms
  6. Terminate lease early
  7. Renew lease
  8. Test expiration scheduler
  9. Negative tests (unauthorized access, invalid dates, etc.)
- **Time:** 30 minutes

#### ☐ Task 9.2: Manual API Testing

- Use Postman to test all endpoints
- Verify lease creation happens when application approved
- Test access control (tenant can only see their leases)
- **Time:** 1 hour

---

## 📁 FILES TO CREATE (Total: 18 files)

### Models & Enums (2 files)

1. ☐ `src/main/java/com/gharsaathi/lease/model/LeaseStatus.java`
2. ☐ `src/main/java/com/gharsaathi/lease/model/Lease.java`

### Repository (1 file)

1. ☐ `src/main/java/com/gharsaathi/lease/repository/LeaseRepository.java`

### Exceptions (5 files)

1. ☐ `src/main/java/com/gharsaathi/lease/exception/LeaseNotFoundException.java`
2. ☐ `src/main/java/com/gharsaathi/lease/exception/LeaseAlreadyExistsException.java`
3. ☐ `src/main/java/com/gharsaathi/lease/exception/LeaseAccessDeniedException.java`
4. ☐ `src/main/java/com/gharsaathi/lease/exception/InvalidLeaseStateException.java`
5. ☐ `src/main/java/com/gharsaathi/lease/exception/InvalidLeaseDateException.java`

### DTOs (5 files)

1. ☐ `src/main/java/com/gharsaathi/common/dto/CreateLeaseRequest.java`
2. ☐ `src/main/java/com/gharsaathi/common/dto/LeaseResponse.java`
3. ☐ `src/main/java/com/gharsaathi/common/dto/LeaseListResponse.java`
4. ☐ `src/main/java/com/gharsaathi/common/dto/UpdateLeaseRequest.java`
5. ☐ `src/main/java/com/gharsaathi/common/dto/TerminateLeaseRequest.java`

### Service & Scheduler (2 files)

1. ☐ `src/main/java/com/gharsaathi/lease/service/LeaseService.java`
2. ☐ `src/main/java/com/gharsaathi/lease/scheduler/LeaseExpirationScheduler.java`

### Controller (1 file)

1. ☐ `src/main/java/com/gharsaathi/lease/controller/LeaseController.java`

### Documentation (1 file)

1. ☐ `backend/tests/LEASE_MODULE_API_TESTS.txt`

### Modified Files (1 file)

1. ☐ `src/main/java/com/gharsaathi/rental/application/service/RentalApplicationService.java` (MODIFY)

---

## ⚠️ POTENTIAL ISSUES & SOLUTIONS

### Issue 1: Application Already Approved (Legacy Data)

**Problem:** Existing approved applications have no leases  
**Solution:**

- Add migration endpoint: `POST /api/admin/leases/migrate-from-applications`
- Create leases for all APPROVED applications without leases
- Calculate start date as application `reviewedAt`, end date as start + `leaseDurationMonths`

### Issue 2: Multiple Active Leases

**Problem:** Database constraint violation if trying to create 2nd active lease  
**Solution:**

- Check in `LeaseService.createLeaseFromApplication()`:

  ```java
  if (leaseRepository.existsByPropertyIdAndStatus(propertyId, LeaseStatus.ACTIVE)) {
      throw new LeaseAlreadyExistsException("Property already has an active lease");
  }
  ```

### Issue 3: Lease Expiration vs Property Status

**Problem:** Property might be RENTED even after lease expires  
**Solution:**

- Scheduler updates both:

  ```java
  lease.setStatus(LeaseStatus.EXPIRED);
  property.setStatus(PropertyStatus.AVAILABLE);
  ```

### Issue 4: Early Termination Notice Period

**Problem:** Tenant/landlord terminates without proper notice  
**Solution:**

- Validate in `terminateLease()`:

  ```java
  if (terminationDate.isBefore(LocalDate.now().plusDays(noticeDays))) {
      throw new InvalidLeaseDateException("Must provide X days notice");
  }
  ```

---

## 🎯 SUCCESS CRITERIA

### ✅ Functional Requirements

- [ ] Lease is automatically created when application approved
- [ ] Tenant can view their active and past leases
- [ ] Landlord can view all leases for their properties
- [ ] Lease expiration is detected and status updated automatically
- [ ] Property status changes when lease expires/terminates
- [ ] Other pending applications are rejected when lease created
- [ ] Lease can be renewed with new end date
- [ ] Lease can be terminated early with reason
- [ ] Access control: tenants/landlords only see their leases

### ✅ Technical Requirements

- [ ] No breaking changes to existing Property/Application modules
- [ ] All API endpoints secured with proper roles
- [ ] Proper exception handling for all edge cases
- [ ] Database constraints prevent invalid data
- [ ] Scheduled task runs reliably
- [ ] Response times < 500ms for all endpoints

### ✅ Testing Requirements

- [ ] All API endpoints tested with Postman
- [ ] Integration with application approval tested
- [ ] Scheduler tested (manually trigger or wait 24 hours)
- [ ] Negative test cases pass (unauthorized access, invalid data)

---

## ⏱️ TIME ESTIMATE

| Phase | Tasks | Estimated Time |
|-------|-------|----------------|
| Phase 1: Entity & Enums | 2 tasks | 25 minutes |
| Phase 2: Repository | 1 task | 15 minutes |
| Phase 3: Exceptions | 2 tasks | 20 minutes |
| Phase 4: DTOs | 5 tasks | 60 minutes |
| Phase 5: Service Layer | 1 task | 120 minutes |
| Phase 6: Scheduled Tasks | 1 task | 30 minutes |
| Phase 7: Controller | 1 task | 90 minutes |
| Phase 8: Modify Existing Code | 2 tasks | 20 minutes |
| Phase 9: Testing | 2 tasks | 90 minutes |
| **TOTAL** | **17 tasks** | **~7.5 hours** |

**Recommendation:** Split into 2 coding sessions:

- **Session 1 (4 hours):** Phases 1-5 (Core implementation)
- **Session 2 (3.5 hours):** Phases 6-9 (Integration, testing, polish)

---

## 🚀 IMPLEMENTATION ORDER

Follow this exact sequence to avoid dependency issues:

1. ✅ **Create folder structure** (`lease/model`, `lease/repository`, `lease/service`, `lease/controller`, `lease/exception`, `lease/scheduler`)
2. ✅ **Phase 1** - Enums & Entity (no dependencies)
3. ✅ **Phase 2** - Repository (depends on entity)
4. ✅ **Phase 3** - Exceptions (no dependencies)
5. ✅ **Phase 4** - DTOs (depends on entity & enums)
6. ✅ **Phase 5** - Service (depends on everything above)
7. ✅ **Phase 6** - Scheduler (depends on service)
8. ✅ **Phase 7** - Controller (depends on service & DTOs)
9. ✅ **Phase 8** - Modify RentalApplicationService (integrate lease creation)
10. ✅ **Phase 9** - Testing & Validation

---

## 📝 NOTES & CONSIDERATIONS

### For Future Enhancement (NOT in this implementation)

- [ ] Digital lease signing with e-signatures
- [ ] Lease document upload (PDF storage)
- [ ] Lease template generation
- [ ] Multiple renewals tracking (renewal history)
- [ ] Lease amendment support
- [ ] Rent escalation clauses
- [ ] Maintenance responsibilities in lease
- [ ] Pet agreement addendums

### Database Migration Note

If you have existing data, you'll need to:

1. Add `leases` table via migration
2. Run data migration script to create leases for existing approved applications
3. Verify property statuses are correct

---

**Ready to implement? Let's start with Phase 1! 🎉**
