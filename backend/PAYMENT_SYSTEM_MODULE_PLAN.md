# 💳 PAYMENT SYSTEM MODULE - IMPLEMENTATION PLAN

## GharSaathi Project - Rent Payment Management System

**Module:** Payment System  
**Version:** 1.0.0  
**Created:** January 27, 2026  
**Dependencies:** Authentication, Property Management, Rental Applications, Lease Management

---

## 📋 ANALYSIS & CURRENT STATE

### What We Have

✅ **Lease Management** - Complete with:

- Monthly rent amount (`monthlyRent` - BigDecimal)
- Security deposit (`securityDeposit` - BigDecimal)
- Lease start/end dates
- Lease status (ACTIVE, EXPIRED, TERMINATED, RENEWED)
- Tenant and Landlord relationships

✅ **Property Management** - Contains:

- Property price (monthly rent)
- Security deposit

✅ **Rental Applications** - Contains:

- Tenant monthly income
- Move-in date
- Lease duration

### What We Need

❌ Payment tracking system
❌ Payment status management
❌ Payment history/records
❌ Payment due date calculation
❌ Late payment tracking
❌ Security deposit management
❌ Payment receipts
❌ Payment statistics

---

## 🎯 MODULE OVERVIEW

The Payment System Module manages all financial transactions between tenants and landlords, including:

- **Monthly rent payments**
- **Security deposit tracking**
- **Payment due dates and reminders**
- **Payment history and receipts**
- **Late payment penalties**
- **Automatic payment schedule generation**
- **Payment statistics and reporting**

### Core Features

1. **Automatic Payment Schedule Generation** - When lease is created, generate all monthly payment records
2. **Payment Recording** - Tenants can mark payments as paid, landlords can confirm
3. **Payment Status Tracking** - PENDING, PAID, OVERDUE, PARTIALLY_PAID, CANCELLED
4. **Late Payment Detection** - Automatic marking of overdue payments
5. **Security Deposit Management** - Track deposit payment and refunds
6. **Payment History** - Complete transaction history for tenants and landlords
7. **Payment Statistics** - Dashboard metrics (paid/pending/overdue amounts)
8. **Payment Receipts** - Generate payment confirmation data

---

## 🔄 PAYMENT WORKFLOW

### Initial Setup (When Lease Created)

```
1. Lease created → Status: ACTIVE
2. Security deposit payment created → Status: PENDING
3. Monthly rent payments auto-generated for entire lease duration
   Example: 12-month lease = 12 monthly payment records created
4. Each payment has due date (start date + N months)
```

### Monthly Payment Flow

```
Tenant Side:
1. View pending payments
2. Make payment (external - bank/cash/online)
3. Mark payment as PAID → Upload proof (future feature)
4. Status: PENDING → PAID

Landlord Side:
1. View payment records
2. Confirm payment received → Status: CONFIRMED
3. Reject if not received → Status: PENDING (with note)
```

### Late Payment Detection

```
System (Scheduler):
1. Daily check at 3:00 AM
2. If due date < today AND status = PENDING
3. Mark as OVERDUE
4. Calculate late fee (optional)
```

---

## 🗄️ DATABASE SCHEMA

### Table: payments

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Unique payment ID |
| lease_id | BIGINT | FOREIGN KEY → leases(id), NOT NULL | Associated lease |
| tenant_id | BIGINT | FOREIGN KEY → users(id), NOT NULL | Tenant making payment |
| landlord_id | BIGINT | FOREIGN KEY → users(id), NOT NULL | Landlord receiving payment |
| property_id | BIGINT | FOREIGN KEY → properties(id), NOT NULL | Property for which payment is made |
| payment_type | ENUM | NOT NULL | RENT, SECURITY_DEPOSIT, LATE_FEE |
| amount | DECIMAL(10,2) | NOT NULL | Payment amount |
| due_date | DATE | NOT NULL | Payment due date |
| paid_date | DATE | NULL | Actual payment date |
| status | ENUM | NOT NULL, DEFAULT 'PENDING' | Payment status |
| payment_method | VARCHAR(50) | NULL | CASH, BANK_TRANSFER, ESEWA, KHALTI, etc. |
| transaction_reference | VARCHAR(100) | NULL | Transaction ID/reference number |
| month_year | VARCHAR(7) | NOT NULL | Format: "2026-02" (for rent payments) |
| notes | TEXT | NULL | Additional notes |
| late_fee | DECIMAL(10,2) | DEFAULT 0 | Late payment fee |
| confirmed_by_landlord | BOOLEAN | DEFAULT false | Landlord confirmation |
| confirmation_date | DATETIME | NULL | When landlord confirmed |
| created_at | DATETIME | NOT NULL | Record creation timestamp |
| updated_at | DATETIME | NOT NULL | Last update timestamp |

**Indexes:**

- idx_lease_id: (lease_id)
- idx_tenant_id: (tenant_id)
- idx_landlord_id: (landlord_id)
- idx_property_id: (property_id)
- idx_status: (status)
- idx_due_date: (due_date)
- idx_payment_type: (payment_type)
- idx_month_year: (month_year)

**Constraints:**

- unique_rent_payment: UNIQUE (lease_id, month_year, payment_type)
- check_amount: amount > 0
- check_dates: paid_date >= due_date OR paid_date IS NULL
- check_late_fee: late_fee >= 0

---

## 📊 PAYMENT STATUS ENUM

```java
public enum PaymentStatus {
    PENDING,         // Payment is due but not yet paid
    PAID,           // Tenant marked as paid
    CONFIRMED,      // Landlord confirmed payment received
    OVERDUE,        // Payment is past due date
    PARTIALLY_PAID, // Partial payment received (future feature)
    CANCELLED       // Payment cancelled (lease terminated)
}
```

## 💰 PAYMENT TYPE ENUM

```java
public enum PaymentType {
    RENT,              // Monthly rent payment
    SECURITY_DEPOSIT,  // Initial security deposit
    LATE_FEE          // Late payment fee (future feature)
}
```

## 💳 PAYMENT METHOD ENUM

```java
public enum PaymentMethod {
    CASH,
    BANK_TRANSFER,
    ESEWA,
    KHALTI,
    IME_PAY,
    CONNECT_IPS,
    OTHER
}
```

---

## 🔗 INTEGRATION POINTS WITH EXISTING MODULES

### 1. ✅ Lease Management Integration

**Location:** `LeaseService.createLeaseFromApplication()` and `LeaseService.createManualLease()`  

**Integration Flow:**

```java
// After lease is created:
1. Create security deposit payment record (due date = lease start date)
2. Generate monthly rent payments for entire lease duration
   - For 12-month lease: create 12 payment records
   - Due dates: leaseStartDate + 1 month, +2 months, +3 months, etc.
```

**New Method Required:**

```java
PaymentService.generatePaymentsForLease(Lease lease)
```

### 2. ✅ Lease Termination Integration

**Location:** `LeaseService.terminateLease()`

**Integration Flow:**

```java
// When lease is terminated:
1. Cancel all future pending payments (due date > termination date)
2. Keep past payments intact for history
```

### 3. ✅ Lease Renewal Integration

**Location:** `LeaseService.renewLease()`

**Integration Flow:**

```java
// When lease is renewed:
1. Generate additional payment records for extension period
2. If renewed for 6 months, create 6 more payment records
```

---

## 🏗️ IMPLEMENTATION PLAN - DETAILED CHECKLIST

### **PHASE 1: ENUMS & ENTITY** ✅

#### ☐ Task 1.1: Create PaymentStatus Enum

- **File:** `src/main/java/com/gharsaathi/payment/model/PaymentStatus.java`
- **Values:** PENDING, PAID, CONFIRMED, OVERDUE, PARTIALLY_PAID, CANCELLED
- **Time:** 5 minutes

#### ☐ Task 1.2: Create PaymentType Enum

- **File:** `src/main/java/com/gharsaathi/payment/model/PaymentType.java`
- **Values:** RENT, SECURITY_DEPOSIT, LATE_FEE
- **Time:** 5 minutes

#### ☐ Task 1.3: Create PaymentMethod Enum

- **File:** `src/main/java/com/gharsaathi/payment/model/PaymentMethod.java`
- **Values:** CASH, BANK_TRANSFER, ESEWA, KHALTI, IME_PAY, CONNECT_IPS, OTHER
- **Time:** 5 minutes

#### ☐ Task 1.4: Create Payment Entity

- **File:** `src/main/java/com/gharsaathi/payment/model/Payment.java`
- **Relationships:**
  - `@ManyToOne` → Lease
  - `@ManyToOne` → User (tenant)
  - `@ManyToOne` → User (landlord)
  - `@ManyToOne` → Property
- **Annotations:** @Entity, @Table, @PrePersist, @PreUpdate
- **Helper Methods:**
  - `isOverdue()` - Check if payment is overdue
  - `isPending()` - Check if status is PENDING
  - `isConfirmed()` - Check if confirmed by landlord
  - `calculateLateFee()` - Calculate late fee based on days overdue
  - `getDaysOverdue()` - Get number of days past due date
  - `canBePaid()` - Check if payment can be marked as paid
  - `canBeConfirmed()` - Check if landlord can confirm
- **Time:** 30 minutes

---

### **PHASE 2: REPOSITORY LAYER** ✅

#### ☐ Task 2.1: Create PaymentRepository

- **File:** `src/main/java/com/gharsaathi/payment/repository/PaymentRepository.java`
- **Extends:** `JpaRepository<Payment, Long>`
- **Custom Queries:**

  ```java
  // Find by lease
  List<Payment> findByLeaseId(Long leaseId);
  List<Payment> findByLeaseIdOrderByDueDateAsc(Long leaseId);
  
  // Find by tenant
  List<Payment> findByTenantId(Long tenantId);
  Page<Payment> findByTenantId(Long tenantId, Pageable pageable);
  List<Payment> findByTenantIdAndStatus(Long tenantId, PaymentStatus status);
  
  // Find by landlord
  List<Payment> findByLandlordId(Long landlordId);
  Page<Payment> findByLandlordId(Long landlordId, Pageable pageable);
  List<Payment> findByLandlordIdAndStatus(Long landlordId, PaymentStatus status);
  
  // Find by status
  List<Payment> findByStatus(PaymentStatus status);
  List<Payment> findByStatusAndDueDateBefore(PaymentStatus status, LocalDate date);
  
  // Find by property
  List<Payment> findByPropertyId(Long propertyId);
  
  // Find overdue payments
  @Query("SELECT p FROM Payment p WHERE p.status = 'PENDING' AND p.dueDate < :currentDate")
  List<Payment> findOverduePayments(@Param("currentDate") LocalDate currentDate);
  
  // Find upcoming payments (due in next X days)
  @Query("SELECT p FROM Payment p WHERE p.status = 'PENDING' " +
         "AND p.dueDate BETWEEN :startDate AND :endDate")
  List<Payment> findUpcomingPayments(@Param("startDate") LocalDate startDate, 
                                     @Param("endDate") LocalDate endDate);
  
  // Statistics queries
  @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.landlordId = :landlordId AND p.status = :status")
  BigDecimal getTotalAmountByLandlordAndStatus(@Param("landlordId") Long landlordId, 
                                                @Param("status") PaymentStatus status);
  
  @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.tenantId = :tenantId AND p.status = :status")
  BigDecimal getTotalAmountByTenantAndStatus(@Param("tenantId") Long tenantId, 
                                             @Param("status") PaymentStatus status);
  ```

- **Time:** 20 minutes

---

### **PHASE 3: EXCEPTION CLASSES** ✅

#### ☐ Task 3.1: Create Payment Exceptions

- **Files:**
  - `src/main/java/com/gharsaathi/payment/exception/PaymentNotFoundException.java`
  - `src/main/java/com/gharsaathi/payment/exception/PaymentAccessDeniedException.java`
  - `src/main/java/com/gharsaathi/payment/exception/InvalidPaymentStateException.java`
  - `src/main/java/com/gharsaathi/payment/exception/PaymentAlreadyPaidException.java`
- **Time:** 15 minutes

#### ☐ Task 3.2: Update GlobalExceptionHandler

- **File:** `src/main/java/com/gharsaathi/common/exception/GlobalExceptionHandler.java`
- **Add handlers for:**
  - `PaymentNotFoundException` → 404
  - `PaymentAccessDeniedException` → 403
  - `InvalidPaymentStateException` → 400
  - `PaymentAlreadyPaidException` → 409
- **Time:** 10 minutes

---

### **PHASE 4: DTO CLASSES** ✅

#### ☐ Task 4.1: Create PaymentRequest DTO (For marking as paid)

- **File:** `src/main/java/com/gharsaathi/common/dto/PaymentRequest.java`
- **Fields:**
  - paymentMethod (PaymentMethod) - @NotNull
  - transactionReference (String) - Optional
  - paidDate (LocalDate) - @NotNull
  - notes (String) - Optional
- **Time:** 10 minutes

#### ☐ Task 4.2: Create ConfirmPaymentRequest DTO

- **File:** `src/main/java/com/gharsaathi/common/dto/ConfirmPaymentRequest.java`
- **Fields:**
  - confirmed (Boolean) - @NotNull
  - notes (String) - Optional (reason if rejecting)
- **Time:** 10 minutes

#### ☐ Task 4.3: Create PaymentResponse DTO

- **File:** `src/main/java/com/gharsaathi/common/dto/PaymentResponse.java`
- **Fields:** All Payment fields + nested PropertyInfo, TenantInfo, LandlordInfo, LeaseInfo
- **Nested Classes:**
  - PropertyInfo (id, title, address, city)
  - TenantInfo (id, fullName, email, phoneNumber)
  - LandlordInfo (id, fullName, email, phoneNumber)
  - LeaseInfo (id, leaseStartDate, leaseEndDate, monthlyRent)
- **Time:** 25 minutes

#### ☐ Task 4.4: Create PaymentListResponse DTO

- **File:** `src/main/java/com/gharsaathi/common/dto/PaymentListResponse.java`
- **Fields:**
  - payments (List<PaymentResponse>)
  - totalElements, totalPages, currentPage, pageSize, hasNext, hasPrevious
- **Time:** 5 minutes

#### ☐ Task 4.5: Create PaymentStatisticsResponse DTO

- **File:** `src/main/java/com/gharsaathi/common/dto/PaymentStatisticsResponse.java`
- **Fields:**
  - totalPaid (BigDecimal)
  - totalPending (BigDecimal)
  - totalOverdue (BigDecimal)
  - totalConfirmed (BigDecimal)
  - paidCount (Integer)
  - pendingCount (Integer)
  - overdueCount (Integer)
  - nextPaymentDueDate (LocalDate)
  - nextPaymentAmount (BigDecimal)
- **Time:** 10 minutes

---

### **PHASE 5: SERVICE LAYER** ✅

#### ☐ Task 5.1: Create PaymentService

- **File:** `src/main/java/com/gharsaathi/payment/service/PaymentService.java`
- **Dependencies:**
  - PaymentRepository
  - LeaseRepository
  - PropertyRepository
  - UserRepository

**Methods to Implement:**

```java
// ✅ Auto-generate payments when lease is created
void generatePaymentsForLease(Lease lease);

// ✅ Cancel future payments when lease is terminated
void cancelFuturePayments(Long leaseId, LocalDate terminationDate);

// ✅ Generate additional payments when lease is renewed
void generateRenewalPayments(Lease lease, LocalDate oldEndDate);

// Get payment by ID
PaymentResponse getPaymentById(Long paymentId, Long userId);

// Get all payments for a tenant
PaymentListResponse getTenantPayments(Long tenantId, PaymentStatus status, int page, int size);

// Get all payments for a landlord
PaymentListResponse getLandlordPayments(Long landlordId, PaymentStatus status, int page, int size);

// Get payments for a specific lease
PaymentListResponse getLeasePayments(Long leaseId, Long userId);

// Mark payment as paid (by tenant)
PaymentResponse markAsPaid(Long paymentId, PaymentRequest request, Long tenantId);

// Confirm payment received (by landlord)
PaymentResponse confirmPayment(Long paymentId, ConfirmPaymentRequest request, Long landlordId);

// Get payment statistics for tenant
PaymentStatisticsResponse getTenantPaymentStatistics(Long tenantId);

// Get payment statistics for landlord
PaymentStatisticsResponse getLandlordPaymentStatistics(Long landlordId);

// System task: Mark overdue payments
void processOverduePayments(); // Scheduled task

// Get upcoming payments (due in next X days)
List<PaymentResponse> getUpcomingPayments(Long userId, int daysAhead);

// Helper methods
private Payment getPaymentAndVerifyAccess(Long paymentId, Long userId);
private void validatePaymentRequest(PaymentRequest request);
```

- **Time:** 3 hours

---

### **PHASE 6: SCHEDULED TASKS** ✅

#### ☐ Task 6.1: Create PaymentOverdueScheduler

- **File:** `src/main/java/com/gharsaathi/payment/scheduler/PaymentOverdueScheduler.java`
- **Purpose:** Automatically mark payments as OVERDUE
- **Schedule:** Run daily at 3:00 AM
- **Task:**

  ```java
  @Scheduled(cron = "0 0 3 * * *") // 3 AM daily
  public void checkOverduePayments() {
      paymentService.processOverduePayments();
  }
  ```

- **Time:** 20 minutes

---

### **PHASE 7: CONTROLLER LAYER** ✅

#### ☐ Task 7.1: Create PaymentController

- **File:** `src/main/java/com/gharsaathi/payment/controller/PaymentController.java`
- **Base Path:** `/api`

**Endpoints:**

**Tenant Endpoints:**

```java
GET /api/tenant/payments - Get all payments for tenant
GET /api/tenant/payments/{id} - Get payment details
GET /api/tenant/payments/pending - Get pending payments
GET /api/tenant/payments/overdue - Get overdue payments
POST /api/tenant/payments/{id}/pay - Mark payment as paid
GET /api/tenant/payments/statistics - Get payment statistics
GET /api/tenant/payments/upcoming - Get upcoming payments
```

**Landlord Endpoints:**

```java
GET /api/landlord/payments - Get all payments for landlord properties
GET /api/landlord/payments/{id} - Get payment details
GET /api/landlord/payments/pending - Get pending confirmations
GET /api/landlord/payments/overdue - Get overdue payments
POST /api/landlord/payments/{id}/confirm - Confirm payment received
GET /api/landlord/payments/statistics - Get payment statistics
GET /api/landlord/leases/{leaseId}/payments - Get payments for specific lease
```

- **Security:** Use `@PreAuthorize` for role-based access
- **Time:** 2 hours

---

### **PHASE 8: INTEGRATE WITH LEASE MODULE** ⚠️

#### ☐ Task 8.1: Update LeaseService

- **File:** `src/main/java/com/gharsaathi/lease/service/LeaseService.java`
- **Methods to Modify:**
  1. `createLeaseFromApplication()` - Add payment generation
  2. `createManualLease()` - Add payment generation
  3. `terminateLease()` - Cancel future payments
  4. `renewLease()` - Generate renewal payments

**Changes:**

```java
// In createLeaseFromApplication():
Lease savedLease = leaseRepository.save(lease);
paymentService.generatePaymentsForLease(savedLease); // NEW

// In terminateLease():
lease.setStatus(LeaseStatus.TERMINATED);
Lease terminatedLease = leaseRepository.save(lease);
paymentService.cancelFuturePayments(leaseId, terminationDate); // NEW

// In renewLease():
lease.setLeaseEndDate(newEndDate);
LocalDate oldEndDate = lease.getLeaseEndDate();
Lease renewedLease = leaseRepository.save(lease);
paymentService.generateRenewalPayments(renewedLease, oldEndDate); // NEW
```

- **Time:** 30 minutes

---

### **PHASE 9: TESTING DOCUMENTATION** ✅

#### ☐ Task 9.1: Create API Test Document

- **File:** `backend/tests/PAYMENT_MODULE_API_TESTS.txt`
- **Test Scenarios:**
  1. View tenant payments
  2. View landlord payments
  3. Mark payment as paid
  4. Confirm payment
  5. View payment statistics
  6. View overdue payments
  7. View upcoming payments
  8. Test payment auto-generation on lease creation
  9. Test payment cancellation on lease termination
  10. Negative tests (unauthorized access, invalid states)
- **Time:** 45 minutes

#### ☐ Task 9.2: Manual API Testing

- Use Postman to test all endpoints
- Verify payment generation on lease creation
- Test access control
- **Time:** 1.5 hours

---

## 📁 FILES TO CREATE (Total: 19 files)

### Models & Enums (4 files)

1. ☐ `src/main/java/com/gharsaathi/payment/model/PaymentStatus.java`
2. ☐ `src/main/java/com/gharsaathi/payment/model/PaymentType.java`
3. ☐ `src/main/java/com/gharsaathi/payment/model/PaymentMethod.java`
4. ☐ `src/main/java/com/gharsaathi/payment/model/Payment.java`

### Repository (1 file)

5. ☐ `src/main/java/com/gharsaathi/payment/repository/PaymentRepository.java`

### Exceptions (4 files)

6. ☐ `src/main/java/com/gharsaathi/payment/exception/PaymentNotFoundException.java`
2. ☐ `src/main/java/com/gharsaathi/payment/exception/PaymentAccessDeniedException.java`
3. ☐ `src/main/java/com/gharsaathi/payment/exception/InvalidPaymentStateException.java`
4. ☐ `src/main/java/com/gharsaathi/payment/exception/PaymentAlreadyPaidException.java`

### DTOs (5 files)

10. ☐ `src/main/java/com/gharsaathi/common/dto/PaymentRequest.java`
2. ☐ `src/main/java/com/gharsaathi/common/dto/ConfirmPaymentRequest.java`
3. ☐ `src/main/java/com/gharsaathi/common/dto/PaymentResponse.java`
4. ☐ `src/main/java/com/gharsaathi/common/dto/PaymentListResponse.java`
5. ☐ `src/main/java/com/gharsaathi/common/dto/PaymentStatisticsResponse.java`

### Service & Scheduler (2 files)

15. ☐ `src/main/java/com/gharsaathi/payment/service/PaymentService.java`
2. ☐ `src/main/java/com/gharsaathi/payment/scheduler/PaymentOverdueScheduler.java`

### Controller (1 file)

17. ☐ `src/main/java/com/gharsaathi/payment/controller/PaymentController.java`

### Documentation (1 file)

18. ☐ `backend/tests/PAYMENT_MODULE_API_TESTS.txt`

### Modified Files (2 files)

19. ☐ `src/main/java/com/gharsaathi/lease/service/LeaseService.java` (MODIFY)
2. ☐ `src/main/java/com/gharsaathi/common/exception/GlobalExceptionHandler.java` (MODIFY)

---

## ⚠️ IMPORTANT DESIGN DECISIONS

### 1. Payment Generation Strategy

**Decision:** Auto-generate ALL payments when lease is created

**Rationale:**

- Provides complete visibility of payment schedule upfront
- Tenant can see all upcoming payments
- Landlord can track expected income
- Simplifies payment tracking

**Alternative:** Create payments month-by-month (not chosen)

### 2. Payment Confirmation Workflow

**Decision:** Two-step verification (Tenant marks paid → Landlord confirms)

**Rationale:**

- Prevents disputes ("I paid" vs "I didn't receive")
- Creates audit trail
- Landlord has final say on payment receipt

**Alternative:** Single-step (tenant marks paid, automatically confirmed) - not secure

### 3. Late Fee Calculation

**Decision:** Store late_fee field but don't auto-calculate initially (future feature)

**Rationale:**

- Needs business rules (grace period, percentage, fixed amount)
- May vary by landlord preference
- Can be added later without schema changes

### 4. Security Deposit Handling

**Decision:** Security deposit is a special payment type, tracked like rent

**Rationale:**

- Consistent tracking mechanism
- Can mark as paid/refunded
- Appears in payment history

---

## 🎯 SUCCESS CRITERIA

### ✅ Functional Requirements

- [ ] Payments auto-generated when lease created
- [ ] Tenant can view all their payments
- [ ] Landlord can view all payments for their properties
- [ ] Tenant can mark payment as paid
- [ ] Landlord can confirm payment received
- [ ] Overdue payments automatically marked by scheduler
- [ ] Payment statistics calculated correctly
- [ ] Future payments cancelled when lease terminated
- [ ] Additional payments generated when lease renewed
- [ ] Access control: users only see their payments

### ✅ Technical Requirements

- [ ] No breaking changes to existing modules
- [ ] All API endpoints secured with proper roles
- [ ] Proper exception handling for all edge cases
- [ ] Database constraints prevent invalid data
- [ ] Scheduled task runs reliably
- [ ] Response times < 500ms for all endpoints

### ✅ Integration Requirements

- [ ] Lease creation triggers payment generation
- [ ] Lease termination cancels future payments
- [ ] Lease renewal generates additional payments
- [ ] All integrations are non-blocking (failures don't stop lease operations)

---

## ⏱️ TIME ESTIMATE

| Phase | Tasks | Estimated Time |
|-------|-------|----------------|
| Phase 1: Enums & Entity | 4 tasks | 45 minutes |
| Phase 2: Repository | 1 task | 20 minutes |
| Phase 3: Exceptions | 2 tasks | 25 minutes |
| Phase 4: DTOs | 5 tasks | 60 minutes |
| Phase 5: Service Layer | 1 task | 180 minutes |
| Phase 6: Scheduled Tasks | 1 task | 20 minutes |
| Phase 7: Controller | 1 task | 120 minutes |
| Phase 8: Integration | 1 task | 30 minutes |
| Phase 9: Testing | 2 tasks | 135 minutes |
| **TOTAL** | **18 tasks** | **~10 hours** |

**Recommendation:** Split into 3 coding sessions:

- **Session 1 (3 hours):** Phases 1-4 (Models, Repository, Exceptions, DTOs)
- **Session 2 (4 hours):** Phases 5-6 (Service Layer, Scheduler)
- **Session 3 (3 hours):** Phases 7-9 (Controller, Integration, Testing)

---

## 🚀 IMPLEMENTATION ORDER

Follow this exact sequence to avoid dependency issues:

1. ✅ **Create folder structure** (`payment/model`, `payment/repository`, `payment/service`, `payment/controller`, `payment/exception`, `payment/scheduler`)
2. ✅ **Phase 1** - Enums & Entity (no dependencies)
3. ✅ **Phase 2** - Repository (depends on entity)
4. ✅ **Phase 3** - Exceptions (no dependencies)
5. ✅ **Phase 4** - DTOs (depends on entity & enums)
6. ✅ **Phase 5** - Service (depends on everything above + lease service)
7. ✅ **Phase 6** - Scheduler (depends on service)
8. ✅ **Phase 7** - Controller (depends on service & DTOs)
9. ✅ **Phase 8** - Integrate with LeaseService
10. ✅ **Phase 9** - Testing & Validation

---

## 📝 EXAMPLE PAYMENT GENERATION

### Scenario: 12-month lease starting Feb 1, 2026

**Lease Details:**

- Start Date: 2026-02-01
- End Date: 2027-02-01
- Monthly Rent: Rs. 25,000
- Security Deposit: Rs. 50,000

**Generated Payments:**

1. Payment #1: Security Deposit - Rs. 50,000 (Due: 2026-02-01)
2. Payment #2: Rent - Rs. 25,000 (Due: 2026-02-01, Month: 2026-02)
3. Payment #3: Rent - Rs. 25,000 (Due: 2026-03-01, Month: 2026-03)
4. Payment #4: Rent - Rs. 25,000 (Due: 2026-04-01, Month: 2026-04)
... (continues for all 12 months)
5. Payment #14: Rent - Rs. 25,000 (Due: 2027-01-01, Month: 2027-01)

**Total:** 14 payment records (1 security deposit + 12 monthly rents + 1 last month)

---

## 🔮 FUTURE ENHANCEMENTS (NOT in this implementation)

- [ ] Online payment gateway integration (Khalti, eSewa, IME Pay)
- [ ] Automatic payment reminders via email/SMS
- [ ] PDF receipt generation
- [ ] Partial payment support
- [ ] Late fee auto-calculation with configurable rules
- [ ] Security deposit refund workflow
- [ ] Payment proof upload (images/PDFs)
- [ ] Recurring payment setup
- [ ] Payment analytics dashboard
- [ ] Export payment history (CSV/PDF)

---

## 📚 API ENDPOINT SUMMARY

### Tenant Endpoints (7)

- GET `/api/tenant/payments` - List all payments
- GET `/api/tenant/payments/{id}` - Get payment details
- GET `/api/tenant/payments/pending` - Pending payments
- GET `/api/tenant/payments/overdue` - Overdue payments
- POST `/api/tenant/payments/{id}/pay` - Mark as paid
- GET `/api/tenant/payments/statistics` - Payment stats
- GET `/api/tenant/payments/upcoming` - Upcoming payments

### Landlord Endpoints (7)

- GET `/api/landlord/payments` - List all payments
- GET `/api/landlord/payments/{id}` - Get payment details
- GET `/api/landlord/payments/pending` - Pending confirmations
- GET `/api/landlord/payments/overdue` - Overdue payments
- POST `/api/landlord/payments/{id}/confirm` - Confirm payment
- GET `/api/landlord/payments/statistics` - Payment stats
- GET `/api/landlord/leases/{leaseId}/payments` - Lease payments

**Total:** 14 API endpoints

---

**Ready to implement? Let's start with Phase 1: Enums & Entity!** 🚀
