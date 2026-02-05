package com.gharsaathi.payment.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gharsaathi.lease.model.Lease;
import com.gharsaathi.payment.dto.ConfirmPaymentRequest;
import com.gharsaathi.payment.dto.MarkPaymentPaidRequest;
import com.gharsaathi.payment.dto.PaymentListResponse;
import com.gharsaathi.payment.dto.PaymentResponse;
import com.gharsaathi.payment.dto.PaymentStatisticsResponse;
import com.gharsaathi.payment.exception.InvalidPaymentOperationException;
import com.gharsaathi.payment.exception.PaymentGenerationException;
import com.gharsaathi.payment.exception.PaymentNotFoundException;
import com.gharsaathi.payment.exception.PaymentUnauthorizedException;
import com.gharsaathi.payment.model.Payment;
import com.gharsaathi.payment.model.PaymentStatus;
import com.gharsaathi.payment.model.PaymentType;
import com.gharsaathi.payment.repository.PaymentRepository;
import com.gharsaathi.property.model.Property;
import com.gharsaathi.auth.model.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service class for payment operations
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {
    
    private final PaymentRepository paymentRepository;

    /**
     * Generate all payments for a lease (security deposit + monthly rents)
     * Called automatically when lease is created
     */
    @Transactional
    public void generatePaymentsForLease(Lease lease) {
        try {
            log.info("Generating payments for lease ID: {}", lease.getId());
            
            List<Payment> payments = new ArrayList<>();
            
            // Generate security deposit payment (due on lease start date)
            Payment securityDepositPayment = Payment.builder()
                    .lease(lease)
                    .tenant(lease.getTenant())
                    .landlord(lease.getLandlord())
                    .property(lease.getProperty())
                    .paymentType(PaymentType.SECURITY_DEPOSIT)
                    .amount(lease.getSecurityDeposit())
                    .dueDate(lease.getLeaseStartDate())
                    .status(PaymentStatus.PENDING)
                    .monthYear(null) // Security deposit doesn't have month/year
                    .lateFee(BigDecimal.ZERO)
                    .confirmedByLandlord(false)
                    .build();
            payments.add(securityDepositPayment);
            
            // Generate monthly rent payments
            LocalDate currentMonth = lease.getLeaseStartDate();
            LocalDate endDate = lease.getLeaseEndDate();
            
            while (!currentMonth.isAfter(endDate)) {
                // Set due date to 1st of each month
                LocalDate dueDate = currentMonth.withDayOfMonth(1);
                
                // For the first month, if lease starts mid-month, due date is start date
                if (currentMonth.equals(lease.getLeaseStartDate()) && lease.getLeaseStartDate().getDayOfMonth() != 1) {
                    dueDate = lease.getLeaseStartDate();
                }
                
                // Month-year in YYYY-MM format
                String monthYear = currentMonth.format(DateTimeFormatter.ofPattern("yyyy-MM"));
                
                Payment rentPayment = Payment.builder()
                        .lease(lease)
                        .tenant(lease.getTenant())
                        .landlord(lease.getLandlord())
                        .property(lease.getProperty())
                        .paymentType(PaymentType.RENT)
                        .amount(lease.getMonthlyRent())
                        .dueDate(dueDate)
                        .status(PaymentStatus.PENDING)
                        .monthYear(monthYear)
                        .lateFee(BigDecimal.ZERO)
                        .confirmedByLandlord(false)
                        .build();
                payments.add(rentPayment);
                
                // Move to next month
                currentMonth = currentMonth.plusMonths(1);
            }
            
            // Save all payments
            paymentRepository.saveAll(payments);
            log.info("Successfully generated {} payments for lease ID: {}", payments.size(), lease.getId());
            
        } catch (Exception e) {
            log.error("Failed to generate payments for lease ID: {}", lease.getId(), e);
            throw new PaymentGenerationException("Failed to generate payments for lease", e);
        }
    }

    /**
     * Generate payments for renewed lease (only new months)
     */
    @Transactional
    public void generateRenewalPayments(Lease renewedLease, LocalDate oldEndDate) {
        try {
            log.info("Generating renewal payments for lease ID: {} from {}", renewedLease.getId(), oldEndDate);
            
            List<Payment> payments = new ArrayList<>();
            
            // Start from month after old end date
            LocalDate currentMonth = oldEndDate.plusDays(1);
            LocalDate newEndDate = renewedLease.getLeaseEndDate();
            
            while (!currentMonth.isAfter(newEndDate)) {
                LocalDate dueDate = currentMonth.withDayOfMonth(1);
                String monthYear = currentMonth.format(DateTimeFormatter.ofPattern("yyyy-MM"));
                
                Payment rentPayment = Payment.builder()
                        .lease(renewedLease)
                        .tenant(renewedLease.getTenant())
                        .landlord(renewedLease.getLandlord())
                        .property(renewedLease.getProperty())
                        .paymentType(PaymentType.RENT)
                        .amount(renewedLease.getMonthlyRent())
                        .dueDate(dueDate)
                        .status(PaymentStatus.PENDING)
                        .monthYear(monthYear)
                        .lateFee(BigDecimal.ZERO)
                        .confirmedByLandlord(false)
                        .build();
                payments.add(rentPayment);
                
                currentMonth = currentMonth.plusMonths(1);
            }
            
            paymentRepository.saveAll(payments);
            log.info("Successfully generated {} renewal payments for lease ID: {}", payments.size(), renewedLease.getId());
            
        } catch (Exception e) {
            log.error("Failed to generate renewal payments for lease ID: {}", renewedLease.getId(), e);
            throw new PaymentGenerationException("Failed to generate renewal payments", e);
        }
    }

    /**
     * Cancel future payments when lease is terminated
     */
    @Transactional
    public void cancelFuturePayments(Long leaseId, LocalDate terminationDate) {
        try {
            log.info("Cancelling future payments for lease ID: {} after {}", leaseId, terminationDate);
            
            List<Payment> payments = paymentRepository.findByLeaseIdOrderByDueDateAsc(leaseId);
            
            int cancelledCount = 0;
            for (Payment payment : payments) {
                // Cancel payments with due date after termination date and status is PENDING
                if (payment.getDueDate().isAfter(terminationDate) && 
                    payment.getStatus() == PaymentStatus.PENDING) {
                    payment.setStatus(PaymentStatus.CANCELLED);
                    payment.setNotes("Cancelled due to lease termination on " + terminationDate);
                    cancelledCount++;
                }
            }
            
            if (cancelledCount > 0) {
                paymentRepository.saveAll(payments);
                log.info("Cancelled {} future payments for lease ID: {}", cancelledCount, leaseId);
            }
            
        } catch (Exception e) {
            log.error("Failed to cancel future payments for lease ID: {}", leaseId, e);
            // Don't throw exception - this is a cleanup operation
            log.warn("Continuing despite payment cancellation failure");
        }
    }

    /**
     * Mark payment as paid by tenant
     */
    @Transactional
    public PaymentResponse markAsPaid(Long paymentId, Long userId, MarkPaymentPaidRequest request) {
        Payment payment = paymentRepository.findByIdWithDetails(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException(paymentId));
        
        // Check if user is the tenant of this payment
        if (!payment.getTenant().getId().equals(userId)) {
            throw new PaymentUnauthorizedException("You are not authorized to mark this payment as paid");
        }
        
        // Validate payment can be paid
        if (!payment.canBePaid()) {
            throw new InvalidPaymentOperationException(
                "Payment cannot be marked as paid. Current status: " + payment.getStatus()
            );
        }
        
        // Update payment
        payment.setStatus(PaymentStatus.PAID);
        payment.setPaidDate(request.getPaidDate());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setTransactionReference(request.getTransactionReference());
        payment.setLateFee(request.getLateFee() != null ? request.getLateFee() : BigDecimal.ZERO);
        
        // Append notes
        if (request.getNotes() != null && !request.getNotes().isBlank()) {
            String existingNotes = payment.getNotes() != null ? payment.getNotes() : "";
            payment.setNotes(existingNotes.isBlank() ? request.getNotes() : existingNotes + "\n" + request.getNotes());
        }
        
        Payment savedPayment = paymentRepository.save(payment);
        log.info("Payment ID: {} marked as paid by tenant ID: {}", paymentId, userId);
        
        return mapToResponse(savedPayment);
    }

    /**
     * Confirm payment by landlord
     */
    @Transactional
    public PaymentResponse confirmPayment(Long paymentId, Long userId, ConfirmPaymentRequest request) {
        Payment payment = paymentRepository.findByIdWithDetails(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException(paymentId));
        
        // Check if user is the landlord of this payment
        if (!payment.getLandlord().getId().equals(userId)) {
            throw new PaymentUnauthorizedException("You are not authorized to confirm this payment");
        }
        
        // Validate payment can be confirmed
        if (!payment.canBeConfirmed()) {
            throw new InvalidPaymentOperationException(
                "Payment cannot be confirmed. Current status: " + payment.getStatus()
            );
        }
        
        // Update payment
        payment.setStatus(PaymentStatus.CONFIRMED);
        payment.setConfirmedByLandlord(true);
        payment.setConfirmationDate(request.getConfirmationDate().atStartOfDay());
        
        // Append notes
        if (request.getNotes() != null && !request.getNotes().isBlank()) {
            String existingNotes = payment.getNotes() != null ? payment.getNotes() : "";
            payment.setNotes(existingNotes.isBlank() ? request.getNotes() : existingNotes + "\n" + request.getNotes());
        }
        
        Payment savedPayment = paymentRepository.save(payment);
        log.info("Payment ID: {} confirmed by landlord ID: {}", paymentId, userId);
        
        return mapToResponse(savedPayment);
    }

    /**
     * Process overdue payments (called by scheduler)
     */
    @Transactional
    public void processOverduePayments() {
        try {
            LocalDate today = LocalDate.now();
            List<Payment> overduePayments = paymentRepository.findOverduePayments(today);
            
            int processedCount = 0;
            for (Payment payment : overduePayments) {
                if (payment.getStatus() == PaymentStatus.PENDING) {
                    payment.setStatus(PaymentStatus.OVERDUE);
                    
                    // Calculate late fee
                    BigDecimal lateFee = payment.calculateLateFee();
                    payment.setLateFee(lateFee);
                    
                    processedCount++;
                }
            }
            
            if (processedCount > 0) {
                paymentRepository.saveAll(overduePayments);
                log.info("Processed {} overdue payments", processedCount);
            }
            
        } catch (Exception e) {
            log.error("Failed to process overdue payments", e);
        }
    }

    /**
     * Get payment by ID
     */
    public PaymentResponse getPaymentById(Long paymentId, Long userId, String userRole) {
        Payment payment = paymentRepository.findByIdWithDetails(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException(paymentId));
        
        // Check authorization
        validateUserAccess(payment, userId, userRole);
        
        return mapToResponse(payment);
    }

    /**
     * Get all payments for a lease
     */
    public List<PaymentResponse> getPaymentsByLease(Long leaseId, Long userId, String userRole) {
        List<Payment> payments = paymentRepository.findByLeaseIdOrderByDueDateAsc(leaseId);
        
        if (!payments.isEmpty()) {
            validateUserAccess(payments.get(0), userId, userRole);
        }
        
        return payments.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get payments for tenant with pagination
     */
    public PaymentListResponse getPaymentsForTenant(Long tenantId, Long userId, String userRole, 
                                                    PaymentStatus status, int page, int size) {
        // Check authorization
        if (!userRole.equals("ADMIN") && !tenantId.equals(userId)) {
            throw new PaymentUnauthorizedException("You are not authorized to view these payments");
        }
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Payment> paymentPage;
        
        if (status != null) {
            paymentPage = paymentRepository.findByTenantIdAndStatusOrderByDueDateDesc(tenantId, status, pageable);
        } else {
            paymentPage = paymentRepository.findByTenantId(tenantId, pageable);
        }
        
        List<PaymentResponse> paymentResponses = paymentPage.getContent().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        
        return PaymentListResponse.builder()
                .payments(paymentResponses)
                .page(paymentPage.getNumber())
                .size(paymentPage.getSize())
                .totalElements(paymentPage.getTotalElements())
                .totalPages(paymentPage.getTotalPages())
                .first(paymentPage.isFirst())
                .last(paymentPage.isLast())
                .build();
    }

    /**
     * Get payments for landlord with pagination
     */
    public PaymentListResponse getPaymentsForLandlord(Long landlordId, Long userId, String userRole,
                                                      PaymentStatus status, int page, int size) {
        // Check authorization
        if (!userRole.equals("ADMIN") && !landlordId.equals(userId)) {
            throw new PaymentUnauthorizedException("You are not authorized to view these payments");
        }
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Payment> paymentPage;
        
        if (status != null) {
            paymentPage = paymentRepository.findByLandlordIdAndStatusOrderByDueDateDesc(landlordId, status, pageable);
        } else {
            paymentPage = paymentRepository.findByLandlordId(landlordId, pageable);
        }
        
        List<PaymentResponse> paymentResponses = paymentPage.getContent().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        
        return PaymentListResponse.builder()
                .payments(paymentResponses)
                .page(paymentPage.getNumber())
                .size(paymentPage.getSize())
                .totalElements(paymentPage.getTotalElements())
                .totalPages(paymentPage.getTotalPages())
                .first(paymentPage.isFirst())
                .last(paymentPage.isLast())
                .build();
    }

    /**
     * Get payment statistics for tenant
     */
    public PaymentStatisticsResponse getTenantPaymentStatistics(Long tenantId, Long userId, String userRole) {
        // Check authorization
        if (!userRole.equals("ADMIN") && !tenantId.equals(userId)) {
            throw new PaymentUnauthorizedException("You are not authorized to view these statistics");
        }
        
        List<Payment> allPayments = paymentRepository.findByTenantId(tenantId);
        
        return calculateStatistics(allPayments);
    }

    /**
     * Get payment statistics for landlord
     */
    public PaymentStatisticsResponse getLandlordPaymentStatistics(Long landlordId, Long userId, String userRole) {
        // Check authorization
        if (!userRole.equals("ADMIN") && !landlordId.equals(userId)) {
            throw new PaymentUnauthorizedException("You are not authorized to view these statistics");
        }
        
        List<Payment> allPayments = paymentRepository.findByLandlordId(landlordId);
        
        return calculateStatistics(allPayments);
    }

    /**
     * Get upcoming payments for tenant (next 30 days)
     */
    public List<PaymentResponse> getUpcomingPayments(Long tenantId, Long userId, String userRole) {
        // Check authorization
        if (!userRole.equals("ADMIN") && !tenantId.equals(userId)) {
            throw new PaymentUnauthorizedException("You are not authorized to view these payments");
        }
        
        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusDays(30);
        
        List<Payment> upcomingPayments = paymentRepository.findUpcomingPayments(today, endDate);
        
        // Filter by tenant
        return upcomingPayments.stream()
                .filter(p -> p.getTenant().getId().equals(tenantId))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Helper methods

    private void validateUserAccess(Payment payment, Long userId, String userRole) {
        if (userRole.equals("ADMIN")) {
            return; // Admin can access all
        }
        
        boolean isTenant = payment.getTenant().getId().equals(userId);
        boolean isLandlord = payment.getLandlord().getId().equals(userId);
        
        if (!isTenant && !isLandlord) {
            throw new PaymentUnauthorizedException("You are not authorized to access this payment");
        }
    }

    private PaymentStatisticsResponse calculateStatistics(List<Payment> payments) {
        int totalPayments = payments.size();
        int pendingPayments = 0;
        int paidPayments = 0;
        int confirmedPayments = 0;
        int overduePayments = 0;
        
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal pendingAmount = BigDecimal.ZERO;
        BigDecimal paidAmount = BigDecimal.ZERO;
        BigDecimal confirmedAmount = BigDecimal.ZERO;
        BigDecimal overdueAmount = BigDecimal.ZERO;
        BigDecimal totalLateFees = BigDecimal.ZERO;
        
        for (Payment payment : payments) {
            totalAmount = totalAmount.add(payment.getAmount());
            
            if (payment.getLateFee() != null) {
                totalLateFees = totalLateFees.add(payment.getLateFee());
            }
            
            switch (payment.getStatus()) {
                case PENDING:
                    pendingPayments++;
                    pendingAmount = pendingAmount.add(payment.getAmount());
                    break;
                case PAID:
                    paidPayments++;
                    paidAmount = paidAmount.add(payment.getAmount());
                    break;
                case CONFIRMED:
                    confirmedPayments++;
                    confirmedAmount = confirmedAmount.add(payment.getAmount());
                    break;
                case OVERDUE:
                    overduePayments++;
                    overdueAmount = overdueAmount.add(payment.getAmount());
                    break;
                default:
                    break;
            }
        }
        
        return PaymentStatisticsResponse.builder()
                .totalPayments(totalPayments)
                .pendingPayments(pendingPayments)
                .paidPayments(paidPayments)
                .confirmedPayments(confirmedPayments)
                .overduePayments(overduePayments)
                .totalAmount(totalAmount)
                .pendingAmount(pendingAmount)
                .paidAmount(paidAmount)
                .confirmedAmount(confirmedAmount)
                .overdueAmount(overdueAmount)
                .totalLateFees(totalLateFees)
                .build();
    }

    private PaymentResponse mapToResponse(Payment payment) {
        Lease lease = payment.getLease();
        User tenant = payment.getTenant();
        User landlord = payment.getLandlord();
        Property property = payment.getProperty();
        
        return PaymentResponse.builder()
                .id(payment.getId())
                .lease(PaymentResponse.LeaseInfo.builder()
                        .id(lease.getId())
                        .startDate(lease.getLeaseStartDate())
                        .endDate(lease.getLeaseEndDate())
                        .leaseStatus(lease.getStatus().name())
                        .build())
                .tenant(PaymentResponse.TenantInfo.builder()
                        .id(tenant.getId())
                        .fullName(tenant.getFullName())
                        .email(tenant.getEmail())
                        .phone(tenant.getPhoneNumber())
                        .build())
                .landlord(PaymentResponse.LandlordInfo.builder()
                        .id(landlord.getId())
                        .fullName(landlord.getFullName())
                        .email(landlord.getEmail())
                        .phone(landlord.getPhoneNumber())
                        .build())
                .property(PaymentResponse.PropertyInfo.builder()
                        .id(property.getId())
                        .title(property.getTitle())
                        .address(property.getAddress())
                        .city(property.getCity())
                        .build())
                .paymentType(payment.getPaymentType())
                .amount(payment.getAmount())
                .dueDate(payment.getDueDate())
                .paidDate(payment.getPaidDate())
                .status(payment.getStatus())
                .paymentMethod(payment.getPaymentMethod())
                .transactionReference(payment.getTransactionReference())
                .monthYear(payment.getMonthYear())
                .displayMonth(payment.getDisplayMonth())
                .notes(payment.getNotes())
                .lateFee(payment.getLateFee())
                .confirmedByLandlord(payment.getConfirmedByLandlord())
                .confirmationDate(payment.getConfirmationDate() != null ? payment.getConfirmationDate().toLocalDate() : null)
                .overdue(payment.isOverdue())
                .daysOverdue((int) payment.getDaysOverdue())
                .createdAt(payment.getCreatedAt())
                .updatedAt(payment.getUpdatedAt())
                .build();
    }
}
