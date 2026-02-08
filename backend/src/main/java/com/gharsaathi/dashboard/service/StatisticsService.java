package com.gharsaathi.dashboard.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gharsaathi.auth.model.Role;
import com.gharsaathi.auth.repository.UserRepository;
import com.gharsaathi.dashboard.dto.ApplicationStatistics;
import com.gharsaathi.dashboard.dto.LeaseStatistics;
import com.gharsaathi.dashboard.dto.PaymentStatistics;
import com.gharsaathi.dashboard.dto.PropertyStatistics;
import com.gharsaathi.lease.model.LeaseStatus;
import com.gharsaathi.lease.repository.LeaseRepository;
import com.gharsaathi.payment.model.Payment;
import com.gharsaathi.payment.model.PaymentStatus;
import com.gharsaathi.payment.repository.PaymentRepository;
import com.gharsaathi.property.model.PropertyStatus;
import com.gharsaathi.property.repository.PropertyRepository;
import com.gharsaathi.rental.application.model.ApplicationStatus;
import com.gharsaathi.rental.application.repository.RentalApplicationRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service for generating statistics from various entities
 * READ-ONLY operations for dashboard aggregation
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatisticsService {

    private final PropertyRepository propertyRepository;
    private final RentalApplicationRepository rentalApplicationRepository;
    private final LeaseRepository leaseRepository;
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;

    // ===== Property Statistics =====

    /**
     * Get property statistics for a specific landlord
     */
    public PropertyStatistics getPropertyStatisticsByLandlord(Long landlordId) {
        long total = propertyRepository.countByLandlordId(landlordId);
        long available = propertyRepository.countByLandlordIdAndStatus(landlordId, PropertyStatus.AVAILABLE);
        long rented = propertyRepository.countByLandlordIdAndStatus(landlordId, PropertyStatus.RENTED);
        long maintenance = propertyRepository.countByLandlordIdAndStatus(landlordId, PropertyStatus.MAINTENANCE);
        long unavailable = propertyRepository.countByLandlordIdAndStatus(landlordId, PropertyStatus.UNAVAILABLE);

        return PropertyStatistics.builder()
                .totalProperties((int) total)
                .availableProperties((int) available)
                .rentedProperties((int) rented)
                .underMaintenanceProperties((int) maintenance)
                .unavailableProperties((int) unavailable)
                .build();
    }

    /**
     * Get global property statistics (admin only)
     */
    public PropertyStatistics getGlobalPropertyStatistics() {
        long total = propertyRepository.count();
        long available = propertyRepository.countByStatus(PropertyStatus.AVAILABLE);
        long rented = propertyRepository.countByStatus(PropertyStatus.RENTED);
        long maintenance = propertyRepository.countByStatus(PropertyStatus.MAINTENANCE);
        long unavailable = propertyRepository.countByStatus(PropertyStatus.UNAVAILABLE);

        return PropertyStatistics.builder()
                .totalProperties((int) total)
                .availableProperties((int) available)
                .rentedProperties((int) rented)
                .underMaintenanceProperties((int) maintenance)
                .unavailableProperties((int) unavailable)
                .build();
    }

    // ===== Application Statistics =====

    /**
     * Get application statistics for a specific tenant
     */
    public ApplicationStatistics getApplicationStatisticsByTenant(Long tenantId) {
        long total = rentalApplicationRepository.countByTenantId(tenantId);
        long pending = rentalApplicationRepository.countByTenantIdAndStatus(tenantId, ApplicationStatus.PENDING);
        long approved = rentalApplicationRepository.countByTenantIdAndStatus(tenantId, ApplicationStatus.APPROVED);
        long rejected = rentalApplicationRepository.countByTenantIdAndStatus(tenantId, ApplicationStatus.REJECTED);
        long withdrawn = rentalApplicationRepository.countByTenantIdAndStatus(tenantId, ApplicationStatus.WITHDRAWN);

        return ApplicationStatistics.builder()
                .totalApplications((int) total)
                .pendingApplications((int) pending)
                .approvedApplications((int) approved)
                .rejectedApplications((int) rejected)
                .withdrawnApplications((int) withdrawn)
                .build();
    }

    /**
     * Get application statistics for a specific landlord
     */
    public ApplicationStatistics getApplicationStatisticsByLandlord(Long landlordId) {
        long total = rentalApplicationRepository.countByLandlordId(landlordId);
        long pending = rentalApplicationRepository.countByLandlordIdAndStatus(landlordId, ApplicationStatus.PENDING);
        long approved = rentalApplicationRepository.countByLandlordIdAndStatus(landlordId, ApplicationStatus.APPROVED);
        long rejected = rentalApplicationRepository.countByLandlordIdAndStatus(landlordId, ApplicationStatus.REJECTED);
        long withdrawn = rentalApplicationRepository.countByLandlordIdAndStatus(landlordId, ApplicationStatus.WITHDRAWN);

        return ApplicationStatistics.builder()
                .totalApplications((int) total)
                .pendingApplications((int) pending)
                .approvedApplications((int) approved)
                .rejectedApplications((int) rejected)
                .withdrawnApplications((int) withdrawn)
                .build();
    }

    /**
     * Get global application statistics (admin only)
     */
    public ApplicationStatistics getGlobalApplicationStatistics() {
        long total = rentalApplicationRepository.count();
        long pending = rentalApplicationRepository.countByStatus(ApplicationStatus.PENDING);
        long approved = rentalApplicationRepository.countByStatus(ApplicationStatus.APPROVED);
        long rejected = rentalApplicationRepository.countByStatus(ApplicationStatus.REJECTED);
        long withdrawn = rentalApplicationRepository.countByStatus(ApplicationStatus.WITHDRAWN);

        return ApplicationStatistics.builder()
                .totalApplications((int) total)
                .pendingApplications((int) pending)
                .approvedApplications((int) approved)
                .rejectedApplications((int) rejected)
                .withdrawnApplications((int) withdrawn)
                .build();
    }

    // ===== Lease Statistics =====

    /**
     * Get lease statistics for a specific tenant
     */
    public LeaseStatistics getLeaseStatisticsByTenant(Long tenantId) {
        long total = leaseRepository.countByTenantId(tenantId);
        long active = leaseRepository.countByTenantIdAndStatus(tenantId, LeaseStatus.ACTIVE);
        long expired = leaseRepository.countByTenantIdAndStatus(tenantId, LeaseStatus.EXPIRED);
        long terminated = leaseRepository.countByTenantIdAndStatus(tenantId, LeaseStatus.TERMINATED);
        
        LocalDate thirtyDaysFromNow = LocalDate.now().plusDays(30);
        long expiring = leaseRepository.countByTenantIdAndStatusAndLeaseEndDateBetween(
            tenantId, LeaseStatus.ACTIVE, LocalDate.now(), thirtyDaysFromNow);

        return LeaseStatistics.builder()
                .totalLeases((int) total)
                .activeLeases((int) active)
                .expiredLeases((int) expired)
                .terminatedLeases((int) terminated)
                .expiringLeases((int) expiring)
                .build();
    }

    /**
     * Get lease statistics for a specific landlord
     */
    public LeaseStatistics getLeaseStatisticsByLandlord(Long landlordId) {
        long total = leaseRepository.countByLandlordId(landlordId);
        long active = leaseRepository.countByLandlordIdAndStatus(landlordId, LeaseStatus.ACTIVE);
        long expired = leaseRepository.countByLandlordIdAndStatus(landlordId, LeaseStatus.EXPIRED);
        long terminated = leaseRepository.countByLandlordIdAndStatus(landlordId, LeaseStatus.TERMINATED);
        
        LocalDate thirtyDaysFromNow = LocalDate.now().plusDays(30);
        long expiring = leaseRepository.countByLandlordIdAndStatusAndLeaseEndDateBetween(
            landlordId, LeaseStatus.ACTIVE, LocalDate.now(), thirtyDaysFromNow);

        return LeaseStatistics.builder()
                .totalLeases((int) total)
                .activeLeases((int) active)
                .expiredLeases((int) expired)
                .terminatedLeases((int) terminated)
                .expiringLeases((int) expiring)
                .build();
    }

    /**
     * Get global lease statistics (admin only)
     */
    public LeaseStatistics getGlobalLeaseStatistics() {
        long total = leaseRepository.count();
        long active = leaseRepository.countByStatus(LeaseStatus.ACTIVE);
        long expired = leaseRepository.countByStatus(LeaseStatus.EXPIRED);
        long terminated = leaseRepository.countByStatus(LeaseStatus.TERMINATED);
        
        LocalDate thirtyDaysFromNow = LocalDate.now().plusDays(30);
        long expiring = leaseRepository.countByStatusAndLeaseEndDateBetween(
            LeaseStatus.ACTIVE, LocalDate.now(), thirtyDaysFromNow);

        return LeaseStatistics.builder()
                .totalLeases((int) total)
                .activeLeases((int) active)
                .expiredLeases((int) expired)
                .terminatedLeases((int) terminated)
                .expiringLeases((int) expiring)
                .build();
    }

    // ===== Payment Statistics =====

    /**
     * Get payment statistics for a specific tenant
     */
    public PaymentStatistics getPaymentStatisticsByTenant(Long tenantId) {
        List<Payment> allPayments = paymentRepository.findByTenantId(tenantId);
        
        return buildPaymentStatistics(allPayments);
    }

    /**
     * Get payment statistics for a specific landlord
     */
    public PaymentStatistics getPaymentStatisticsByLandlord(Long landlordId) {
        List<Payment> allPayments = paymentRepository.findByLandlordId(landlordId);
        
        return buildPaymentStatistics(allPayments);
    }

    /**
     * Get global payment statistics (admin only)
     */
    public PaymentStatistics getGlobalPaymentStatistics() {
        List<Payment> allPayments = paymentRepository.findAll();
        
        return buildPaymentStatistics(allPayments);
    }

    /**
     * Helper method to build payment statistics from payment list
     */
    private PaymentStatistics buildPaymentStatistics(List<Payment> payments) {
        LocalDate thirtyDaysFromNow = LocalDate.now().plusDays(30);
        LocalDate today = LocalDate.now();

        int totalCount = payments.size();
        int pendingCount = 0, paidCount = 0, confirmedCount = 0, overdueCount = 0, upcomingCount = 0;
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal pendingAmount = BigDecimal.ZERO;
        BigDecimal paidAmount = BigDecimal.ZERO;
        BigDecimal confirmedAmount = BigDecimal.ZERO;
        BigDecimal overdueAmount = BigDecimal.ZERO;
        BigDecimal totalLateFees = BigDecimal.ZERO;

        for (Payment payment : payments) {
            BigDecimal amount = payment.getAmount();
            PaymentStatus status = payment.getStatus();

            totalAmount = totalAmount.add(amount);

            switch (status) {
                case PENDING:
                    pendingCount++;
                    pendingAmount = pendingAmount.add(amount);
                    // Check if overdue
                    if (payment.getDueDate().isBefore(today)) {
                        overdueCount++;
                        overdueAmount = overdueAmount.add(amount);
                    }
                    // Check if upcoming
                    if (payment.getDueDate().isAfter(today) && payment.getDueDate().isBefore(thirtyDaysFromNow)) {
                        upcomingCount++;
                    }
                    break;
                case PAID:
                    paidCount++;
                    paidAmount = paidAmount.add(amount);
                    break;
                case CONFIRMED:
                    confirmedCount++;
                    confirmedAmount = confirmedAmount.add(amount);
                    break;
                case OVERDUE:
                    overdueCount++;
                    overdueAmount = overdueAmount.add(amount);
                    break;
            }

            // Add late fees
            if (payment.getLateFee() != null) {
                totalLateFees = totalLateFees.add(payment.getLateFee());
            }
        }

        return PaymentStatistics.builder()
                .totalPayments(totalCount)
                .pendingPayments(pendingCount)
                .paidPayments(paidCount)
                .confirmedPayments(confirmedCount)
                .overduePayments(overdueCount)
                .upcomingPayments(upcomingCount)
                .totalAmount(totalAmount)
                .pendingAmount(pendingAmount)
                .paidAmount(paidAmount)
                .confirmedAmount(confirmedAmount)
                .overdueAmount(overdueAmount)
                .totalLateFees(totalLateFees)
                .build();
    }

    // ===== User Statistics (Admin Only) =====

    /**
     * Get total user count
     */
    public int getTotalUserCount() {
        return (int) userRepository.count();
    }

    /**
     * Get user count by role
     */
    public int getUserCountByRole(Role role) {
        return (int) userRepository.countByRole(role);
    }

    /**
     * Get new users count for current month
     */
    public int getNewUsersThisMonth() {
        LocalDateTime startOfMonth = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        return (int) userRepository.countByCreatedAtAfter(startOfMonth);
    }
}
