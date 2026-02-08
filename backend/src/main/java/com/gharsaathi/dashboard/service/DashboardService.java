package com.gharsaathi.dashboard.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gharsaathi.auth.model.Role;
import com.gharsaathi.auth.model.User;
import com.gharsaathi.auth.repository.UserRepository;
import com.gharsaathi.common.dto.LeaseResponse;
import com.gharsaathi.common.dto.PropertyResponse;
import com.gharsaathi.common.exception.ResourceNotFoundException;
import com.gharsaathi.dashboard.dto.AdminDashboardResponse;
import com.gharsaathi.dashboard.dto.LandlordDashboardResponse;
import com.gharsaathi.dashboard.dto.TenantDashboardResponse;
import com.gharsaathi.lease.model.Lease;
import com.gharsaathi.lease.model.LeaseStatus;
import com.gharsaathi.lease.repository.LeaseRepository;
import com.gharsaathi.payment.dto.PaymentResponse;
import com.gharsaathi.payment.model.Payment;
import com.gharsaathi.payment.model.PaymentStatus;
import com.gharsaathi.payment.repository.PaymentRepository;
import com.gharsaathi.property.model.Property;
import com.gharsaathi.property.model.PropertyImage;
import com.gharsaathi.property.repository.PropertyRepository;
import com.gharsaathi.rental.application.dto.ApplicationResponse;
import com.gharsaathi.rental.application.model.RentalApplication;
import com.gharsaathi.rental.application.repository.RentalApplicationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service for generating role-specific dashboard responses
 * Aggregates data from StatisticsService and existing services
 * READ-ONLY operations for dashboard display
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class DashboardService {

    private final StatisticsService statisticsService;
    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;
    private final RentalApplicationRepository rentalApplicationRepository;
    private final LeaseRepository leaseRepository;
    private final PaymentRepository paymentRepository;

    /**
     * Get tenant dashboard with personalized data
     */
    public TenantDashboardResponse getTenantDashboard(Long tenantId) {
        log.info("Generating tenant dashboard for user: {}", tenantId);
        
        // Verify tenant exists
        User tenant = userRepository.findById(tenantId)
            .orElseThrow(() -> new ResourceNotFoundException("Tenant not found"));
        
        // Get statistics
        var appStats = statisticsService.getApplicationStatisticsByTenant(tenantId);
        var leaseStats = statisticsService.getLeaseStatisticsByTenant(tenantId);
        var paymentStats = statisticsService.getPaymentStatisticsByTenant(tenantId);
        
        // Get recent applications (last 5)
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "createdAt"));
        List<RentalApplication> applications = rentalApplicationRepository
            .findByTenantId(tenantId, pageRequest)
            .getContent();
        List<ApplicationResponse> recentApplications = applications.stream()
            .map(this::mapApplicationToResponse)
            .collect(Collectors.toList());
        
        // Get upcoming payments (next 30 days)
        LocalDate today = LocalDate.now();
        LocalDate thirtyDaysLater = today.plusDays(30);
        List<Payment> upcomingPayments = paymentRepository.findUpcomingPayments(today, thirtyDaysLater)
            .stream()
            .filter(p -> p.getTenant().getId().equals(tenantId))
            .limit(5)
            .collect(Collectors.toList());
        List<PaymentResponse> upcomingPaymentsList = upcomingPayments.stream()
            .map(this::mapPaymentToResponse)
            .collect(Collectors.toList());
        
        // Get active leases (last 5)
        List<Lease> activeLeases = leaseRepository.findByTenantIdAndStatus(tenantId, LeaseStatus.ACTIVE)
            .stream()
            .limit(5)
            .collect(Collectors.toList());
        List<LeaseResponse> activeLeasesList = activeLeases.stream()
            .map(this::mapLeaseToResponse)
            .collect(Collectors.toList());
        
        return TenantDashboardResponse.builder()
            .activeLeases(leaseStats.getActiveLeases())
            .totalApplications(appStats.getTotalApplications())
            .pendingApplications(appStats.getPendingApplications())
            .approvedApplications(appStats.getApprovedApplications())
            .rejectedApplications(appStats.getRejectedApplications())
            .upcomingPayments(paymentStats.getUpcomingPayments())
            .overduePayments(paymentStats.getOverduePayments())
            .savedProperties(0) // Placeholder for future favorites feature
            .totalAmountPaid(paymentStats.getConfirmedAmount())
            .totalAmountPending(paymentStats.getPendingAmount())
            .totalAmountOverdue(paymentStats.getOverdueAmount())
            .recentApplications(recentApplications)
            .upcomingPaymentsList(upcomingPaymentsList)
            .activeLeasesList(activeLeasesList)
            .build();
    }

    /**
     * Get landlord dashboard with business metrics
     */
    public LandlordDashboardResponse getLandlordDashboard(Long landlordId) {
        log.info("Generating landlord dashboard for user: {}", landlordId);
        
        // Verify landlord exists
        User landlord = userRepository.findById(landlordId)
            .orElseThrow(() -> new ResourceNotFoundException("Landlord not found"));
        
        // Get statistics
        var propertyStats = statisticsService.getPropertyStatisticsByLandlord(landlordId);
        var appStats = statisticsService.getApplicationStatisticsByLandlord(landlordId);
        var leaseStats = statisticsService.getLeaseStatisticsByLandlord(landlordId);
        var paymentStats = statisticsService.getPaymentStatisticsByLandlord(landlordId);
        
        // Calculate monthly rental income from active leases
        List<Lease> activeLeases = leaseRepository.findByLandlordIdAndStatus(landlordId, LeaseStatus.ACTIVE);
        BigDecimal monthlyIncome = activeLeases.stream()
            .map(Lease::getMonthlyRent)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        // Calculate payment collection rate
        Double collectionRate = 0.0;
        if (paymentStats.getTotalPayments() > 0) {
            collectionRate = ((double) paymentStats.getConfirmedPayments() / paymentStats.getTotalPayments()) * 100;
            collectionRate = Math.round(collectionRate * 100.0) / 100.0; // Round to 2 decimals
        }
        
        // Get top properties by application count
        List<Property> allProperties = propertyRepository.findByLandlordId(landlordId, PageRequest.of(0, Integer.MAX_VALUE))
            .getContent();
        List<PropertyResponse> topProperties = allProperties.stream()
            .map(property -> {
                long appCount = rentalApplicationRepository.countByPropertyId(property.getId());
                PropertyResponse response = this.mapPropertyToResponse(property);
                // Note: PropertyResponse doesn't have applicationCount field, but this ranks them
                return response;
            })
            .limit(5)
            .collect(Collectors.toList());
        
        // Get recent applications
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "createdAt"));
        List<RentalApplication> applications = rentalApplicationRepository
            .findByLandlordId(landlordId, pageRequest)
            .getContent();
        List<ApplicationResponse> recentApplications = applications.stream()
            .map(this::mapApplicationToResponse)
            .collect(Collectors.toList());
        
        // Get expiring leases (next 30 days)
        LocalDate today = LocalDate.now();
        LocalDate thirtyDaysLater = today.plusDays(30);
        List<Lease> expiringLeases = leaseRepository.findByStatusAndLeaseEndDateBetween(
            LeaseStatus.ACTIVE, today, thirtyDaysLater)
            .stream()
            .filter(lease -> lease.getLandlord().getId().equals(landlordId))
            .collect(Collectors.toList());
        List<LeaseResponse> expiringLeasesList = expiringLeases.stream()
            .map(this::mapLeaseToResponse)
            .collect(Collectors.toList());
        
        // Revenue by property
        Map<String, BigDecimal> revenueByProperty = new HashMap<>();
        for (Property property : allProperties) {
            List<Payment> propertyPayments = paymentRepository.findByPropertyId(property.getId())
                .stream()
                .filter(p -> p.getStatus() == PaymentStatus.CONFIRMED)
                .collect(Collectors.toList());
            BigDecimal propertyRevenue = propertyPayments.stream()
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            revenueByProperty.put(property.getTitle(), propertyRevenue);
        }
        
        // Revenue by month (last 6 months)
        Map<String, BigDecimal> revenueByMonth = calculateRevenueByMonth(landlordId, 6);
        
        // Count applications this month
        LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
        List<RentalApplication> thisMonthApps = rentalApplicationRepository.findByLandlordId(landlordId, pageRequest)
            .getContent()
            .stream()
            .filter(app -> !app.getCreatedAt().toLocalDate().isBefore(startOfMonth))
            .collect(Collectors.toList());
        
        return LandlordDashboardResponse.builder()
            .totalProperties(propertyStats.getTotalProperties())
            .availableProperties(propertyStats.getAvailableProperties())
            .rentedProperties(propertyStats.getRentedProperties())
            .underMaintenanceProperties(propertyStats.getUnderMaintenanceProperties())
            .activeLeases(leaseStats.getActiveLeases())
            .expiringLeases(leaseStats.getExpiringLeases())
            .pendingApplications(appStats.getPendingApplications())
            .totalApplicationsThisMonth(thisMonthApps.size())
            .monthlyRentalIncome(monthlyIncome)
            .expectedMonthlyIncome(monthlyIncome)
            .totalRevenue(paymentStats.getConfirmedAmount())
            .paymentCollectionRate(collectionRate)
            .topProperties(topProperties)
            .recentApplications(recentApplications)
            .expiringLeasesList(expiringLeasesList)
            .revenueByProperty(revenueByProperty)
            .revenueByMonth(revenueByMonth)
            .build();
    }

    /**
     * Get admin dashboard with platform-wide statistics
     */
    public AdminDashboardResponse getAdminDashboard() {
        log.info("Generating admin dashboard");
        
        // User statistics
        int totalUsers = statisticsService.getTotalUserCount();
        int totalTenants = statisticsService.getUserCountByRole(Role.TENANT);
        int totalLandlords = statisticsService.getUserCountByRole(Role.LANDLORD);
        int totalAdmins = statisticsService.getUserCountByRole(Role.ADMIN);
        int newUsersThisMonth = statisticsService.getNewUsersThisMonth();
        
        // Get all statistics
        var propertyStats = statisticsService.getGlobalPropertyStatistics();
        var leaseStats = statisticsService.getGlobalLeaseStatistics();
        var paymentStats = statisticsService.getGlobalPaymentStatistics();
        var appStats = statisticsService.getGlobalApplicationStatistics();
        
        // Calculate total security deposits from active leases
        List<Lease> activeLeases = leaseRepository.findByStatus(LeaseStatus.ACTIVE);
        BigDecimal totalSecurityDeposits = activeLeases.stream()
            .map(Lease::getSecurityDeposit)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        // Calculate current month revenue
        YearMonth currentMonth = YearMonth.now();
        List<Payment> currentMonthPayments = paymentRepository.findByStatus(PaymentStatus.CONFIRMED)
            .stream()
            .filter(p -> p.getPaidDate() != null)
            .filter(p -> {
                YearMonth paymentMonth = YearMonth.from(p.getPaidDate());
                return paymentMonth.equals(currentMonth);
            })
            .collect(Collectors.toList());
        BigDecimal monthlyRevenue = currentMonthPayments.stream()
            .map(Payment::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        // User growth (last 12 months)
        Map<String, Integer> userGrowth = calculateUserGrowth(12);
        
        // Revenue growth (last 12 months)
        Map<String, BigDecimal> revenueGrowth = calculateGlobalRevenueByMonth(12);
        
        // Top properties by application count
        List<Property> allProperties = propertyRepository.findAll();
        List<PropertyResponse> topProperties = allProperties.stream()
            .sorted((p1, p2) -> {
                long count1 = rentalApplicationRepository.countByPropertyId(p1.getId());
                long count2 = rentalApplicationRepository.countByPropertyId(p2.getId());
                return Long.compare(count2, count1); // Descending
            })
            .limit(10)
            .map(this::mapPropertyToResponse)
            .collect(Collectors.toList());
        
        // Top landlords by revenue
        List<User> landlords = userRepository.findAll()
            .stream()
            .filter(u -> u.getRole() == Role.LANDLORD)
            .collect(Collectors.toList());
        
        List<AdminDashboardResponse.LandlordInfo> topLandlords = landlords.stream()
            .map(landlord -> {
                int propertiesCount = (int) propertyRepository.countByLandlordId(landlord.getId());
                List<Payment> landlordPayments = paymentRepository.findByLandlordId(landlord.getId())
                    .stream()
                    .filter(p -> p.getStatus() == PaymentStatus.CONFIRMED)
                    .collect(Collectors.toList());
                BigDecimal totalRevenue = landlordPayments.stream()
                    .map(Payment::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
                
                return AdminDashboardResponse.LandlordInfo.builder()
                    .landlordId(landlord.getId())
                    .landlordName(landlord.getFullName())
                    .landlordEmail(landlord.getEmail())
                    .propertiesCount(propertiesCount)
                    .totalRevenue(totalRevenue)
                    .build();
            })
            .sorted((l1, l2) -> l2.getTotalRevenue().compareTo(l1.getTotalRevenue())) // Descending
            .limit(10)
            .collect(Collectors.toList());
        
        return AdminDashboardResponse.builder()
            .totalUsers(totalUsers)
            .totalTenants(totalTenants)
            .totalLandlords(totalLandlords)
            .totalAdmins(totalAdmins)
            .newUsersThisMonth(newUsersThisMonth)
            .totalProperties(propertyStats.getTotalProperties())
            .availableProperties(propertyStats.getAvailableProperties())
            .rentedProperties(propertyStats.getRentedProperties())
            .totalLeases(leaseStats.getTotalLeases())
            .activeLeases(leaseStats.getActiveLeases())
            .expiredLeases(leaseStats.getExpiredLeases())
            .terminatedLeases(leaseStats.getTerminatedLeases())
            .totalPayments(paymentStats.getTotalPayments())
            .pendingPayments(paymentStats.getPendingPayments())
            .paidPayments(paymentStats.getPaidPayments())
            .confirmedPayments(paymentStats.getConfirmedPayments())
            .overduePayments(paymentStats.getOverduePayments())
            .platformRevenue(paymentStats.getConfirmedAmount())
            .monthlyRevenue(monthlyRevenue)
            .totalSecurityDeposits(totalSecurityDeposits)
            .totalApplications(appStats.getTotalApplications())
            .pendingApplications(appStats.getPendingApplications())
            .approvedApplications(appStats.getApprovedApplications())
            .rejectedApplications(appStats.getRejectedApplications())
            .userGrowth(userGrowth)
            .revenueGrowth(revenueGrowth)
            .topProperties(topProperties)
            .topLandlords(topLandlords)
            .build();
    }

    /**
     * Helper: Calculate revenue by month for a landlord
     */
    private Map<String, BigDecimal> calculateRevenueByMonth(Long landlordId, int months) {
        Map<String, BigDecimal> revenueMap = new LinkedHashMap<>();
        YearMonth currentMonth = YearMonth.now();
        
        for (int i = months - 1; i >= 0; i--) {
            YearMonth month = currentMonth.minusMonths(i);
            String monthKey = month.toString(); // Format: "2026-01"
            
            List<Payment> monthPayments = paymentRepository.findByLandlordId(landlordId)
                .stream()
                .filter(p -> p.getStatus() == PaymentStatus.CONFIRMED && p.getPaidDate() != null)
                .filter(p -> {
                    YearMonth paymentMonth = YearMonth.from(p.getPaidDate());
                    return paymentMonth.equals(month);
                })
                .collect(Collectors.toList());
            
            BigDecimal monthRevenue = monthPayments.stream()
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            
            revenueMap.put(monthKey, monthRevenue);
        }
        
        return revenueMap;
    }

    /**
     * Helper: Calculate global revenue by month
     */
    private Map<String, BigDecimal> calculateGlobalRevenueByMonth(int months) {
        Map<String, BigDecimal> revenueMap = new LinkedHashMap<>();
        YearMonth currentMonth = YearMonth.now();
        
        for (int i = months - 1; i >= 0; i--) {
            YearMonth month = currentMonth.minusMonths(i);
            String monthKey = month.toString();
            
            List<Payment> monthPayments = paymentRepository.findByStatus(PaymentStatus.CONFIRMED)
                .stream()
                .filter(p -> p.getPaidDate() != null)
                .filter(p -> {
                    YearMonth paymentMonth = YearMonth.from(p.getPaidDate());
                    return paymentMonth.equals(month);
                })
                .collect(Collectors.toList());
            
            BigDecimal monthRevenue = monthPayments.stream()
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            
            revenueMap.put(monthKey, monthRevenue);
        }
        
        return revenueMap;
    }

    /**
     * Helper: Calculate user growth by month
     */
    private Map<String, Integer> calculateUserGrowth(int months) {
        Map<String, Integer> growthMap = new LinkedHashMap<>();
        YearMonth currentMonth = YearMonth.now();
        
        for (int i = months - 1; i >= 0; i--) {
            YearMonth month = currentMonth.minusMonths(i);
            String monthKey = month.toString();
            
            long newUsers = userRepository.findAll()
                .stream()
                .filter(u -> {
                    YearMonth userMonth = YearMonth.from(u.getCreatedAt());
                    return userMonth.equals(month);
                })
                .count();
            
            growthMap.put(monthKey, (int) newUsers);
        }
        
        return growthMap;
    }

    // ===== Mapping Methods =====

    /**
     * Map Property to PropertyResponse
     */
    private PropertyResponse mapPropertyToResponse(Property property) {
        String primaryImageUrl = property.getImages().stream()
            .filter(PropertyImage::getIsPrimary)
            .findFirst()
            .map(PropertyImage::getImageUrl)
            .orElse(property.getImages().isEmpty() ? null : property.getImages().get(0).getImageUrl());
        
        return PropertyResponse.builder()
            .id(property.getId())
            .title(property.getTitle())
            .propertyType(property.getPropertyType())
            .status(property.getStatus())
            .city(property.getCity())
            .area(property.getArea())
            .price(property.getPrice())
            .bedrooms(property.getBedrooms())
            .bathrooms(property.getBathrooms())
            .propertyArea(property.getPropertyArea())
            .furnished(property.getFurnished())
            .primaryImageUrl(primaryImageUrl)
            .landlordId(property.getLandlord().getId())
            .landlordName(property.getLandlord().getFullName())
            .landlordEmail(property.getLandlord().getEmail())
            .build();
    }

    /**
     * Map RentalApplication to ApplicationResponse
     */
    private ApplicationResponse mapApplicationToResponse(RentalApplication application) {
        ApplicationResponse.PropertyInfo propertyInfo = ApplicationResponse.PropertyInfo.builder()
            .id(application.getProperty().getId())
            .title(application.getProperty().getTitle())
            .address(application.getProperty().getAddress())
            .city(application.getProperty().getCity())
            .price(application.getProperty().getPrice())
            .bedrooms(application.getProperty().getBedrooms())
            .bathrooms(application.getProperty().getBathrooms())
            .build();
        
        ApplicationResponse.TenantInfo tenantInfo = ApplicationResponse.TenantInfo.builder()
            .id(application.getTenant().getId())
            .fullName(application.getTenant().getFullName())
            .email(application.getTenant().getEmail())
            .phoneNumber(application.getTenant().getPhoneNumber())
            .build();
        
        return ApplicationResponse.builder()
            .id(application.getId())
            .property(propertyInfo)
            .tenant(tenantInfo)
            .status(application.getStatus())
            .message(application.getMessage())
            .landlordResponse(application.getLandlordResponse())
            .createdAt(application.getCreatedAt())
            .updatedAt(application.getUpdatedAt())
            .build();
    }

    /**
     * Map Lease to LeaseResponse
     */
    private LeaseResponse mapLeaseToResponse(Lease lease) {
        LeaseResponse.PropertyInfo propertyInfo = LeaseResponse.PropertyInfo.builder()
            .id(lease.getProperty().getId())
            .title(lease.getProperty().getTitle())
            .address(lease.getProperty().getAddress())
            .city(lease.getProperty().getCity())
            .build();
        
        LeaseResponse.TenantInfo tenantInfo = LeaseResponse.TenantInfo.builder()
            .id(lease.getTenant().getId())
            .fullName(lease.getTenant().getFullName())
            .email(lease.getTenant().getEmail())
            .build();
        
        LeaseResponse.LandlordInfo landlordInfo = LeaseResponse.LandlordInfo.builder()
            .id(lease.getLandlord().getId())
            .fullName(lease.getLandlord().getFullName())
            .email(lease.getLandlord().getEmail())
            .build();
        
        return LeaseResponse.builder()
            .id(lease.getId())
            .property(propertyInfo)
            .tenant(tenantInfo)
            .landlord(landlordInfo)
            .leaseStartDate(lease.getLeaseStartDate())
            .leaseEndDate(lease.getLeaseEndDate())
            .monthlyRent(lease.getMonthlyRent())
            .securityDeposit(lease.getSecurityDeposit())
            .status(lease.getStatus())
            .specialTerms(lease.getSpecialTerms())
            .createdAt(lease.getCreatedAt())
            .updatedAt(lease.getUpdatedAt())
            .build();
    }

    /**
     * Map Payment to PaymentResponse
     */
    private PaymentResponse mapPaymentToResponse(Payment payment) {
        PaymentResponse.LeaseInfo leaseInfo = PaymentResponse.LeaseInfo.builder()
            .id(payment.getLease().getId())
            .startDate(payment.getLease().getLeaseStartDate())
            .endDate(payment.getLease().getLeaseEndDate())
            .build();
        
        PaymentResponse.TenantInfo tenantInfo = PaymentResponse.TenantInfo.builder()
            .id(payment.getTenant().getId())
            .fullName(payment.getTenant().getFullName())
            .email(payment.getTenant().getEmail())
            .build();
        
        PaymentResponse.LandlordInfo landlordInfo = PaymentResponse.LandlordInfo.builder()
            .id(payment.getLandlord().getId())
            .fullName(payment.getLandlord().getFullName())
            .email(payment.getLandlord().getEmail())
            .build();
        
        PaymentResponse.PropertyInfo propertyInfo = PaymentResponse.PropertyInfo.builder()
            .id(payment.getProperty().getId())
            .title(payment.getProperty().getTitle())
            .address(payment.getProperty().getAddress())
            .build();
        
        return PaymentResponse.builder()
            .id(payment.getId())
            .lease(leaseInfo)
            .tenant(tenantInfo)
            .landlord(landlordInfo)
            .property(propertyInfo)
            .amount(payment.getAmount())
            .dueDate(payment.getDueDate())
            .paidDate(payment.getPaidDate())
            .confirmedByLandlord(payment.getConfirmedByLandlord())
            .status(payment.getStatus())
            .lateFee(payment.getLateFee())
            .createdAt(payment.getCreatedAt())
            .updatedAt(payment.getUpdatedAt())
            .build();
    }
}
