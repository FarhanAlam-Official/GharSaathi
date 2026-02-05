package com.gharsaathi.payment.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gharsaathi.payment.dto.ConfirmPaymentRequest;
import com.gharsaathi.payment.dto.MarkPaymentPaidRequest;
import com.gharsaathi.payment.dto.PaymentListResponse;
import com.gharsaathi.payment.dto.PaymentResponse;
import com.gharsaathi.payment.dto.PaymentStatisticsResponse;
import com.gharsaathi.payment.model.PaymentStatus;
import com.gharsaathi.payment.service.PaymentService;
import com.gharsaathi.auth.model.User;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * REST Controller for payment operations
 */
@Slf4j
@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    
    private final PaymentService paymentService;

    /**
     * Get payment by ID
     * Accessible by: Tenant, Landlord, Admin
     */
    @GetMapping("/{paymentId}")
    @PreAuthorize("hasAnyRole('TENANT', 'LANDLORD', 'ADMIN')")
    public ResponseEntity<PaymentResponse> getPaymentById(
            @PathVariable Long paymentId,
            Authentication authentication) {
        
        User user = (User) authentication.getPrincipal();
        Long userId = user.getId();
        String userRole = user.getRole().name();
        
        log.info("User {} requesting payment ID: {}", userId, paymentId);
        
        PaymentResponse response = paymentService.getPaymentById(paymentId, userId, userRole);
        return ResponseEntity.ok(response);
    }

    /**
     * Get all payments for a lease
     * Accessible by: Tenant (own), Landlord (own), Admin (all)
     */
    @GetMapping("/lease/{leaseId}")
    @PreAuthorize("hasAnyRole('TENANT', 'LANDLORD', 'ADMIN')")
    public ResponseEntity<List<PaymentResponse>> getPaymentsByLease(
            @PathVariable Long leaseId,
            Authentication authentication) {
        
        User user = (User) authentication.getPrincipal();
        Long userId = user.getId();
        String userRole = user.getRole().name();
        
        log.info("User {} requesting payments for lease ID: {}", userId, leaseId);
        
        List<PaymentResponse> response = paymentService.getPaymentsByLease(leaseId, userId, userRole);
        return ResponseEntity.ok(response);
    }

    /**
     * Get payments for tenant with pagination and optional status filter
     * Accessible by: Tenant (own), Admin (all)
     */
    @GetMapping("/tenant/{tenantId}")
    @PreAuthorize("hasAnyRole('TENANT', 'ADMIN')")
    public ResponseEntity<PaymentListResponse> getPaymentsForTenant(
            @PathVariable Long tenantId,
            @RequestParam(required = false) PaymentStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Authentication authentication) {
        
        User user = (User) authentication.getPrincipal();
        Long userId = user.getId();
        String userRole = user.getRole().name();
        
        log.info("User {} requesting payments for tenant ID: {} (status: {}, page: {}, size: {})",
                userId, tenantId, status, page, size);
        
        PaymentListResponse response = paymentService.getPaymentsForTenant(
                tenantId, userId, userRole, status, page, size);
        return ResponseEntity.ok(response);
    }

    /**
     * Get payments for landlord with pagination and optional status filter
     * Accessible by: Landlord (own), Admin (all)
     */
    @GetMapping("/landlord/{landlordId}")
    @PreAuthorize("hasAnyRole('LANDLORD', 'ADMIN')")
    public ResponseEntity<PaymentListResponse> getPaymentsForLandlord(
            @PathVariable Long landlordId,
            @RequestParam(required = false) PaymentStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Authentication authentication) {
        
        User user = (User) authentication.getPrincipal();
        Long userId = user.getId();
        String userRole = user.getRole().name();
        
        log.info("User {} requesting payments for landlord ID: {} (status: {}, page: {}, size: {})",
                userId, landlordId, status, page, size);
        
        PaymentListResponse response = paymentService.getPaymentsForLandlord(
                landlordId, userId, userRole, status, page, size);
        return ResponseEntity.ok(response);
    }

    /**
     * Mark payment as paid by tenant
     * Accessible by: Tenant (own payments only)
     */
    @PutMapping("/{paymentId}/mark-paid")
    @PreAuthorize("hasRole('TENANT')")
    public ResponseEntity<PaymentResponse> markAsPaid(
            @PathVariable Long paymentId,
            @Valid @RequestBody MarkPaymentPaidRequest request,
            Authentication authentication) {
        
        User user = (User) authentication.getPrincipal();
        Long userId = user.getId();
        
        log.info("Tenant {} marking payment {} as paid", userId, paymentId);
        
        PaymentResponse response = paymentService.markAsPaid(paymentId, userId, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Confirm payment by landlord
     * Accessible by: Landlord (own payments only)
     */
    @PutMapping("/{paymentId}/confirm")
    @PreAuthorize("hasRole('LANDLORD')")
    public ResponseEntity<PaymentResponse> confirmPayment(
            @PathVariable Long paymentId,
            @Valid @RequestBody ConfirmPaymentRequest request,
            Authentication authentication) {
        
        User user = (User) authentication.getPrincipal();
        Long userId = user.getId();
        
        log.info("Landlord {} confirming payment {}", userId, paymentId);
        
        PaymentResponse response = paymentService.confirmPayment(paymentId, userId, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Get payment statistics for tenant
     * Accessible by: Tenant (own), Admin (all)
     */
    @GetMapping("/tenant/{tenantId}/statistics")
    @PreAuthorize("hasAnyRole('TENANT', 'ADMIN')")
    public ResponseEntity<PaymentStatisticsResponse> getTenantStatistics(
            @PathVariable Long tenantId,
            Authentication authentication) {
        
        User user = (User) authentication.getPrincipal();
        Long userId = user.getId();
        String userRole = user.getRole().name();
        
        log.info("User {} requesting payment statistics for tenant ID: {}", userId, tenantId);
        
        PaymentStatisticsResponse response = paymentService.getTenantPaymentStatistics(
                tenantId, userId, userRole);
        return ResponseEntity.ok(response);
    }

    /**
     * Get payment statistics for landlord
     * Accessible by: Landlord (own), Admin (all)
     */
    @GetMapping("/landlord/{landlordId}/statistics")
    @PreAuthorize("hasAnyRole('LANDLORD', 'ADMIN')")
    public ResponseEntity<PaymentStatisticsResponse> getLandlordStatistics(
            @PathVariable Long landlordId,
            Authentication authentication) {
        
        User user = (User) authentication.getPrincipal();
        Long userId = user.getId();
        String userRole = user.getRole().name();
        
        log.info("User {} requesting payment statistics for landlord ID: {}", userId, landlordId);
        
        PaymentStatisticsResponse response = paymentService.getLandlordPaymentStatistics(
                landlordId, userId, userRole);
        return ResponseEntity.ok(response);
    }

    /**
     * Get upcoming payments for tenant (next 30 days)
     * Accessible by: Tenant (own), Admin (all)
     */
    @GetMapping("/tenant/{tenantId}/upcoming")
    @PreAuthorize("hasAnyRole('TENANT', 'ADMIN')")
    public ResponseEntity<List<PaymentResponse>> getUpcomingPayments(
            @PathVariable Long tenantId,
            Authentication authentication) {
        
        User user = (User) authentication.getPrincipal();
        Long userId = user.getId();
        String userRole = user.getRole().name();
        
        log.info("User {} requesting upcoming payments for tenant ID: {}", userId, tenantId);
        
        List<PaymentResponse> response = paymentService.getUpcomingPayments(
                tenantId, userId, userRole);
        return ResponseEntity.ok(response);
    }
}
