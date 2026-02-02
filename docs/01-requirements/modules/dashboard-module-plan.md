# 📋 Dashboard Analytics System - Implementation Plan

**Module:** Dashboard Analytics  
**Priority:** ⭐⭐⭐⭐⭐ (Critical)  
**Estimated Time:** 5-6 days  
**Complexity:** Medium  
**Status:** Ready for Implementation

---

## 🎯 Module Overview

### Purpose

Provide real-time analytics and statistics for all user roles (Tenant, Landlord, Admin) to help them understand their activities, track metrics, and make informed decisions.

### Key Benefits

- ✅ **Fast Implementation:** Uses existing data, no external dependencies
- ✅ **High Impact:** All three roles benefit immediately
- ✅ **Showcases Work:** Demonstrates power of completed modules
- ✅ **Frontend Ready:** Clean APIs for dashboard development
- ✅ **Performance Friendly:** Optimized aggregation queries with caching

---

## 📊 Dashboard Features

### 1. Tenant Dashboard

**Overview Section:**

- Active leases count
- Total applications (by status)
- Upcoming payments (next 30 days)
- Overdue payments count
- Favorite properties count
- Total amount paid/pending

**Detailed Sections:**

- Recent applications with status
- Payment schedule (upcoming and overdue)
- Active lease details
- Property browsing history (optional)

**API Endpoint:** `GET /api/dashboard/tenant`

---

### 2. Landlord Dashboard

**Overview Section:**

- Total properties (by status)
- Active leases count
- Monthly rental income
- Pending applications count
- Upcoming lease expirations (next 30 days)
- Payment collection rate

**Detailed Sections:**

- Property performance metrics
- Recent applications by property
- Revenue breakdown (by property, by month)
- Lease expiration alerts
- Maintenance requests (when implemented)

**API Endpoint:** `GET /api/dashboard/landlord`

---

### 3. Admin Dashboard

**Overview Section:**

- Total users (tenants/landlords/admins)
- Total properties (by status)
- Total leases (active/expired/terminated)
- Total payments (by status)
- Platform revenue (current month, total)
- User growth (new registrations)

**Detailed Sections:**

- System health metrics
- Top properties (by applications)
- Top landlords (by revenue)
- Recent activities
- Payment collection statistics

**API Endpoint:** `GET /api/dashboard/admin`

---

## 🏗️ Architecture

### Module Structure

```
com.gharsaathi.dashboard/
├── controller/
│   └── DashboardController.java
├── dto/
│   ├── TenantDashboardResponse.java
│   ├── LandlordDashboardResponse.java
│   ├── AdminDashboardResponse.java
│   ├── PropertyStatistics.java
│   ├── LeaseStatistics.java
│   ├── PaymentStatistics.java
│   └── ApplicationStatistics.java
└── service/
    ├── DashboardService.java
    └── StatisticsService.java
```

### No New Database Tables Required

- Uses existing tables: users, properties, rental_applications, leases, payments
- Aggregation queries on existing data
- Optional: Redis caching for frequently accessed stats

---

## 📝 Implementation Checklist

### Phase 1: DTOs & Response Models (Day 1)

#### Task 1.1: Create Common Statistics DTOs

**Files:**

- `PropertyStatistics.java`
- `ApplicationStatistics.java`
- `LeaseStatistics.java`
- `PaymentStatistics.java`

**Time:** 2 hours

---

#### Task 1.2: Create Tenant Dashboard DTO

**File:** `TenantDashboardResponse.java`

**Fields:**

```java
// Overview
private Integer activeLeases;
private Integer totalApplications;
private Integer pendingApplications;
private Integer approvedApplications;
private Integer rejectedApplications;
private Integer upcomingPayments;
private Integer overduePayments;
private Integer savedProperties;

// Financial Summary
private BigDecimal totalAmountPaid;
private BigDecimal totalAmountPending;
private BigDecimal totalAmountOverdue;

// Recent Activities
private List<ApplicationResponse> recentApplications; // Last 5
private List<PaymentResponse> upcomingPaymentsList; // Next 5
private List<LeaseResponse> activeLeasesList;
```

**Time:** 1 hour

---

#### Task 1.3: Create Landlord Dashboard DTO

**File:** `LandlordDashboardResponse.java`

**Fields:**

```java
// Property Overview
private Integer totalProperties;
private Integer availableProperties;
private Integer rentedProperties;
private Integer underMaintenanceProperties;

// Lease Overview
private Integer activeLeases;
private Integer expiringLeases; // Next 30 days

// Application Overview
private Integer pendingApplications;
private Integer totalApplicationsThisMonth;

// Financial Overview
private BigDecimal monthlyRentalIncome;
private BigDecimal expectedMonthlyIncome;
private BigDecimal totalRevenue;
private Double paymentCollectionRate; // Percentage

// Recent Activities
private List<PropertyResponse> topProperties; // By applications
private List<ApplicationResponse> recentApplications;
private List<LeaseResponse> expiringLeasesList;

// Revenue Breakdown
private Map<String, BigDecimal> revenueByProperty;
private Map<String, BigDecimal> revenueByMonth; // Last 6 months
```

**Time:** 1.5 hours

---

#### Task 1.4: Create Admin Dashboard DTO

**File:** `AdminDashboardResponse.java`

**Fields:**

```java
// User Statistics
private Integer totalUsers;
private Integer totalTenants;
private Integer totalLandlords;
private Integer totalAdmins;
private Integer newUsersThisMonth;

// Property Statistics
private Integer totalProperties;
private Integer availableProperties;
private Integer rentedProperties;

// Lease Statistics
private Integer totalLeases;
private Integer activeLeases;
private Integer expiredLeases;
private Integer terminatedLeases;

// Payment Statistics
private Integer totalPayments;
private Integer pendingPayments;
private Integer paidPayments;
private Integer confirmedPayments;
private Integer overduePayments;

// Financial Statistics
private BigDecimal platformRevenue;
private BigDecimal monthlyRevenue;
private BigDecimal totalSecurityDeposits;

// Application Statistics
private Integer totalApplications;
private Integer pendingApplications;
private Integer approvedApplications;
private Integer rejectedApplications;

// Growth Metrics
private Map<String, Integer> userGrowth; // Last 12 months
private Map<String, Integer> revenueGrowth; // Last 12 months

// Top Performers
private List<PropertyResponse> topProperties; // By applications
private List<LandlordInfo> topLandlords; // By revenue
```

**Time:** 2 hours

---

### Phase 2: Service Layer (Day 2-3)

#### Task 2.1: Create StatisticsService

**File:** `StatisticsService.java`

**Purpose:** Helper service for common statistics calculations

**Methods:**

```java
// Property Statistics
PropertyStatistics getPropertyStatistics(Long landlordId);
PropertyStatistics getPropertyStatisticsForAdmin();

// Application Statistics
ApplicationStatistics getApplicationStatistics(Long userId, String role);

// Lease Statistics
LeaseStatistics getLeaseStatistics(Long userId, String role);

// Payment Statistics  
PaymentStatistics getPaymentStatistics(Long userId, String role);

// Revenue Calculations
BigDecimal calculateMonthlyRevenue(Long landlordId);
Double calculatePaymentCollectionRate(Long landlordId);
Map<String, BigDecimal> getRevenueByProperty(Long landlordId);
Map<String, BigDecimal> getRevenueByMonth(Long landlordId, int months);
```

**Time:** 4 hours

---

#### Task 2.2: Create DashboardService

**File:** `DashboardService.java`

**Methods:**

```java
// Tenant Dashboard
TenantDashboardResponse getTenantDashboard(Long tenantId);

// Landlord Dashboard
LandlordDashboardResponse getLandlordDashboard(Long landlordId);

// Admin Dashboard
AdminDashboardResponse getAdminDashboard();
```

**Implementation Pattern:**

```java
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class DashboardService {
    
    private final PropertyRepository propertyRepository;
    private final RentalApplicationRepository applicationRepository;
    private final LeaseRepository leaseRepository;
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final StatisticsService statisticsService;
    
    public TenantDashboardResponse getTenantDashboard(Long tenantId) {
        log.info("Fetching dashboard for tenant: {}", tenantId);
        
        // Aggregate data from all modules
        TenantDashboardResponse dashboard = new TenantDashboardResponse();
        
        // Leases
        dashboard.setActiveLeases(
            leaseRepository.countByTenantIdAndStatus(tenantId, LeaseStatus.ACTIVE)
        );
        
        // Applications
        ApplicationStatistics appStats = 
            statisticsService.getApplicationStatistics(tenantId, "TENANT");
        dashboard.setTotalApplications(appStats.getTotalApplications());
        dashboard.setPendingApplications(appStats.getPendingApplications());
        // ... more fields
        
        // Payments
        PaymentStatistics paymentStats = 
            statisticsService.getPaymentStatistics(tenantId, "TENANT");
        dashboard.setUpcomingPayments(paymentStats.getUpcomingCount());
        dashboard.setOverduePayments(paymentStats.getOverdueCount());
        dashboard.setTotalAmountPaid(paymentStats.getPaidAmount());
        // ... more fields
        
        // Recent activities
        dashboard.setRecentApplications(
            applicationRepository.findTop5ByTenantIdOrderByCreatedAtDesc(tenantId)
                .stream()
                .map(this::mapToApplicationResponse)
                .collect(Collectors.toList())
        );
        
        return dashboard;
    }
}
```

**Time:** 6 hours

---

### Phase 3: Repository Queries (Day 3-4)

#### Task 3.1: Add Queries to PropertyRepository

```java
// Count by status
Long countByLandlordIdAndStatus(Long landlordId, PropertyStatus status);
Long countByStatus(PropertyStatus status);

// Top properties by applications
@Query("SELECT p FROM Property p " +
       "LEFT JOIN p.applications a " +
       "GROUP BY p.id " +
       "ORDER BY COUNT(a) DESC")
List<Property> findTopPropertiesByApplications(Pageable pageable);
```

**Time:** 1 hour

---

#### Task 3.2: Add Queries to RentalApplicationRepository

```java
// Count by status for user
Long countByTenantIdAndStatus(Long tenantId, ApplicationStatus status);
Long countByLandlordId(Long landlordId);
Long countByLandlordIdAndStatus(Long landlordId, ApplicationStatus status);

// Recent applications
List<RentalApplication> findTop5ByTenantIdOrderByCreatedAtDesc(Long tenantId);
List<RentalApplication> findTop5ByLandlordIdOrderByCreatedAtDesc(Long landlordId);

// This month count
Long countByLandlordIdAndCreatedAtAfter(Long landlordId, LocalDateTime startDate);
```

**Time:** 1 hour

---

#### Task 3.3: Add Queries to LeaseRepository

```java
// Count by status
Long countByTenantIdAndStatus(Long tenantId, LeaseStatus status);
Long countByLandlordIdAndStatus(Long landlordId, LeaseStatus status);
Long countByStatus(LeaseStatus status);

// Expiring leases (next 30 days)
@Query("SELECT l FROM Lease l WHERE l.landlord.id = :landlordId " +
       "AND l.status = :status " +
       "AND l.leaseEndDate BETWEEN :startDate AND :endDate")
List<Lease> findExpiringLeases(
    @Param("landlordId") Long landlordId,
    @Param("status") LeaseStatus status,
    @Param("startDate") LocalDate startDate,
    @Param("endDate") LocalDate endDate
);

// Monthly revenue calculation
@Query("SELECT SUM(l.monthlyRent) FROM Lease l " +
       "WHERE l.landlord.id = :landlordId " +
       "AND l.status = 'ACTIVE'")
BigDecimal sumMonthlyRentByLandlordId(@Param("landlordId") Long landlordId);
```

**Time:** 1.5 hours

---

#### Task 3.4: Add Queries to PaymentRepository

**Note:** Many already exist from Payment System implementation

Additional queries if needed:

```java
// Count by status for tenant
Long countByTenantIdAndStatus(Long tenantId, PaymentStatus status);

// Upcoming payments (next 30 days)
@Query("SELECT p FROM Payment p WHERE p.tenant.id = :tenantId " +
       "AND p.status = 'PENDING' " +
       "AND p.dueDate BETWEEN :startDate AND :endDate " +
       "ORDER BY p.dueDate ASC")
List<Payment> findUpcomingPaymentsByTenantId(
    @Param("tenantId") Long tenantId,
    @Param("startDate") LocalDate startDate,
    @Param("endDate") LocalDate endDate
);
```

**Time:** 1 hour

---

#### Task 3.5: Add Queries to UserRepository

```java
// Count by role
Long countByRole(String role);

// New users this month
Long countByCreatedAtAfter(LocalDateTime startDate);

// Top landlords by revenue (complex query)
@Query("SELECT u.id, u.fullName, SUM(p.amount) as revenue " +
       "FROM User u " +
       "JOIN Payment p ON p.landlord.id = u.id " +
       "WHERE p.status IN ('PAID', 'CONFIRMED') " +
       "GROUP BY u.id " +
       "ORDER BY revenue DESC")
List<Object[]> findTopLandlordsByRevenue(Pageable pageable);
```

**Time:** 1 hour

---

### Phase 4: Controller Layer (Day 4-5)

#### Task 4.1: Create DashboardController

**File:** `DashboardController.java`

**Endpoints:**

```java
@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@Slf4j
public class DashboardController {
    
    private final DashboardService dashboardService;
    
    /**
     * Get tenant dashboard
     * Accessible by: TENANT (own), ADMIN
     */
    @GetMapping("/tenant")
    @PreAuthorize("hasAnyRole('TENANT', 'ADMIN')")
    public ResponseEntity<TenantDashboardResponse> getTenantDashboard(
            Authentication authentication) {
        
        User user = (User) authentication.getPrincipal();
        Long tenantId = user.getId();
        
        log.info("Fetching tenant dashboard for user: {}", tenantId);
        
        TenantDashboardResponse dashboard = 
            dashboardService.getTenantDashboard(tenantId);
        
        return ResponseEntity.ok(dashboard);
    }
    
    /**
     * Get landlord dashboard
     * Accessible by: LANDLORD (own), ADMIN
     */
    @GetMapping("/landlord")
    @PreAuthorize("hasAnyRole('LANDLORD', 'ADMIN')")
    public ResponseEntity<LandlordDashboardResponse> getLandlordDashboard(
            Authentication authentication) {
        
        User user = (User) authentication.getPrincipal();
        Long landlordId = user.getId();
        
        log.info("Fetching landlord dashboard for user: {}", landlordId);
        
        LandlordDashboardResponse dashboard = 
            dashboardService.getLandlordDashboard(landlordId);
        
        return ResponseEntity.ok(dashboard);
    }
    
    /**
     * Get admin dashboard
     * Accessible by: ADMIN only
     */
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminDashboardResponse> getAdminDashboard() {
        log.info("Fetching admin dashboard");
        
        AdminDashboardResponse dashboard = 
            dashboardService.getAdminDashboard();
        
        return ResponseEntity.ok(dashboard);
    }
}
```

**Time:** 2 hours

---

### Phase 5: Testing & Documentation (Day 5-6)

#### Task 5.1: Create API Test Cases

**File:** `DASHBOARD_API_TESTS.txt`

**Test Scenarios:**

- Tenant dashboard: All statistics accurate
- Landlord dashboard: Revenue calculations correct
- Admin dashboard: Platform-wide stats correct
- Role-based access control
- Empty data handling (new users)
- Performance with large datasets

**Time:** 2 hours

---

#### Task 5.2: Manual Testing with Postman

- Test all three dashboard endpoints
- Verify aggregation accuracy
- Test with different user roles
- Check response times
- Validate against database counts

**Time:** 2 hours

---

#### Task 5.3: Create Documentation

**File:** `DASHBOARD_MODULE_DOCUMENTATION.md`

**Contents:**

- API endpoint reference
- Response structure examples
- Performance notes
- Caching strategy (optional)
- Future enhancements

**Time:** 2 hours

---

#### Task 5.4: Update SecurityConfig (if needed)

Add dashboard endpoints to security configuration:

```java
.requestMatchers("/api/dashboard/tenant").hasAnyRole("TENANT", "ADMIN")
.requestMatchers("/api/dashboard/landlord").hasAnyRole("LANDLORD", "ADMIN")
.requestMatchers("/api/dashboard/admin").hasRole("ADMIN")
```

**Time:** 30 minutes

---

## 📦 Expected Files Created

### Total: ~12 Files

**DTOs (7 files):**

1. `PropertyStatistics.java`
2. `ApplicationStatistics.java`
3. `LeaseStatistics.java`
4. `PaymentStatistics.java`
5. `TenantDashboardResponse.java`
6. `LandlordDashboardResponse.java`
7. `AdminDashboardResponse.java`

**Services (2 files):**
8. `StatisticsService.java`
9. `DashboardService.java`

**Controller (1 file):**
10. `DashboardController.java`

**Documentation (2 files):**
11. `DASHBOARD_API_TESTS.txt`
12. `DASHBOARD_MODULE_DOCUMENTATION.md`

**Modified Files:**

- PropertyRepository.java (add 3 queries)
- RentalApplicationRepository.java (add 4 queries)
- LeaseRepository.java (add 5 queries)
- PaymentRepository.java (add 2 queries - if needed)
- UserRepository.java (add 3 queries)

---

## 🎯 Success Criteria

### Functional Requirements

- ✅ Tenant sees accurate overview of their activities
- ✅ Landlord sees property/revenue analytics
- ✅ Admin sees platform-wide statistics
- ✅ All statistics calculated correctly
- ✅ Real-time data (no caching initially)

### Non-Functional Requirements

- ✅ Response time < 500ms (with <1000 records per entity)
- ✅ No N+1 query problems
- ✅ Proper error handling
- ✅ Role-based access enforced
- ✅ Clean, consistent API design

---

## 🚀 Performance Optimization (Optional)

### Redis Caching Strategy

```java
@Cacheable(value = "dashboard:tenant", key = "#tenantId")
public TenantDashboardResponse getTenantDashboard(Long tenantId) {
    // ... implementation
}

// Cache eviction on data changes
@CacheEvict(value = "dashboard:tenant", key = "#payment.tenant.id")
public void onPaymentStatusChange(Payment payment) {
    // Evict tenant dashboard cache when payment status changes
}
```

**Time:** 3 hours (if implementing caching)

---

## 📊 Expected API Responses

### Tenant Dashboard Example

```json
{
  "activeLeases": 1,
  "totalApplications": 5,
  "pendingApplications": 2,
  "approvedApplications": 2,
  "rejectedApplications": 1,
  "upcomingPayments": 3,
  "overduePayments": 0,
  "savedProperties": 12,
  "totalAmountPaid": 30000.00,
  "totalAmountPending": 20000.00,
  "totalAmountOverdue": 0.00,
  "recentApplications": [
    {
      "id": 5,
      "propertyTitle": "2BHK Apartment",
      "status": "PENDING",
      "createdAt": "2026-01-25T10:30:00"
    }
  ],
  "upcomingPaymentsList": [
    {
      "id": 10,
      "amount": 10000.00,
      "dueDate": "2026-02-01",
      "type": "RENT",
      "status": "PENDING"
    }
  ],
  "activeLeasesList": [
    {
      "id": 1,
      "propertyTitle": "2BHK Apartment",
      "startDate": "2026-01-01",
      "endDate": "2026-12-31",
      "monthlyRent": 10000.00
    }
  ]
}
```

---

## 🎓 Benefits of This Module

1. **User Engagement:** Users see value immediately
2. **Decision Support:** Landlords can track property performance
3. **Platform Insights:** Admins understand platform health
4. **Frontend Ready:** Clean APIs for dashboard UI development
5. **Scalable:** Queries optimized, caching possible
6. **Maintainable:** Clear separation of concerns

---

## 📋 Implementation Timeline

| Day | Tasks | Hours |
|-----|-------|-------|
| **Day 1** | DTOs & Response Models (Tasks 1.1-1.4) | 6.5 |
| **Day 2** | StatisticsService (Task 2.1) | 4 |
| **Day 3** | DashboardService (Task 2.2) | 6 |
| **Day 4** | Repository Queries (Tasks 3.1-3.5) | 5.5 |
| **Day 5** | Controller & Testing (Tasks 4.1, 5.1-5.2) | 6 |
| **Day 6** | Documentation & Polish (Tasks 5.3-5.4) | 4.5 |
| **TOTAL** | **Complete Module** | **32.5 hours** |

**Estimated Calendar Time:** 5-6 working days

---

## 🔄 Next Steps After Completion

1. **Notification System** - Send alerts for dashboard events
2. **File Upload Service** - Enable image/document uploads
3. **User Profile Management** - Complete user features
4. **Property Favorites** - Save/bookmark properties

---

**Plan Status:** ✅ Ready for Implementation  
**Created:** January 28, 2026  
**Complexity:** Medium  
**Priority:** Critical (Must-Have)

---
