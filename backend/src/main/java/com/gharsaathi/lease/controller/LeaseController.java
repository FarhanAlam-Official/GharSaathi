package com.gharsaathi.lease.controller;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gharsaathi.auth.model.User;
import com.gharsaathi.common.dto.CreateLeaseRequest;
import com.gharsaathi.common.dto.LeaseListResponse;
import com.gharsaathi.common.dto.LeaseResponse;
import com.gharsaathi.common.dto.TerminateLeaseRequest;
import com.gharsaathi.common.dto.UpdateLeaseRequest;
import com.gharsaathi.lease.model.LeaseStatus;
import com.gharsaathi.lease.service.LeaseService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller for lease management endpoints
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class LeaseController {
    
    private final LeaseService leaseService;
    
    // ==================== TENANT ENDPOINTS ====================
    
    /**
     * Get all leases for authenticated tenant
     * GET /api/tenant/leases?status=ACTIVE&page=0&size=10
     */
    @GetMapping("/tenant/leases")
    @PreAuthorize("hasRole('TENANT')")
    public ResponseEntity<LeaseListResponse> getTenantLeases(
            @AuthenticationPrincipal User user,
            @RequestParam(required = false) LeaseStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        log.info("Tenant {} fetching their leases", user.getId());
        LeaseListResponse response = leaseService.getTenantLeases(user.getId(), status, page, size);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Get specific lease details for tenant
     * GET /api/tenant/leases/{id}
     */
    @GetMapping("/tenant/leases/{id}")
    @PreAuthorize("hasRole('TENANT')")
    public ResponseEntity<LeaseResponse> getTenantLeaseById(
            @AuthenticationPrincipal User user,
            @PathVariable Long id) {
        
        log.info("Tenant {} fetching lease: {}", user.getId(), id);
        LeaseResponse response = leaseService.getLeaseById(id, user.getId());
        return ResponseEntity.ok(response);
    }
    
    /**
     * Get active lease for tenant
     * GET /api/tenant/leases/active
     */
    @GetMapping("/tenant/leases/active")
    @PreAuthorize("hasRole('TENANT')")
    public ResponseEntity<LeaseListResponse> getTenantActiveLeases(
            @AuthenticationPrincipal User user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        log.info("Tenant {} fetching active leases", user.getId());
        LeaseListResponse response = leaseService.getTenantLeases(user.getId(), LeaseStatus.ACTIVE, page, size);
        return ResponseEntity.ok(response);
    }
    
    // ==================== LANDLORD ENDPOINTS ====================
    
    /**
     * Get all leases for authenticated landlord
     * GET /api/landlord/leases?status=ACTIVE&page=0&size=10
     */
    @GetMapping("/landlord/leases")
    @PreAuthorize("hasRole('LANDLORD')")
    public ResponseEntity<LeaseListResponse> getLandlordLeases(
            @AuthenticationPrincipal User user,
            @RequestParam(required = false) LeaseStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        log.info("Landlord {} fetching their leases", user.getId());
        LeaseListResponse response = leaseService.getLandlordLeases(user.getId(), status, page, size);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Get specific lease details for landlord
     * GET /api/landlord/leases/{id}
     */
    @GetMapping("/landlord/leases/{id}")
    @PreAuthorize("hasRole('LANDLORD')")
    public ResponseEntity<LeaseResponse> getLandlordLeaseById(
            @AuthenticationPrincipal User user,
            @PathVariable Long id) {
        
        log.info("Landlord {} fetching lease: {}", user.getId(), id);
        LeaseResponse response = leaseService.getLeaseById(id, user.getId());
        return ResponseEntity.ok(response);
    }
    
    /**
     * Create manual lease (not from application)
     * POST /api/landlord/leases
     */
    @PostMapping("/landlord/leases")
    @PreAuthorize("hasRole('LANDLORD')")
    public ResponseEntity<LeaseResponse> createManualLease(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody CreateLeaseRequest request) {
        
        log.info("Landlord {} creating manual lease for property: {}", user.getId(), request.getPropertyId());
        LeaseResponse response = leaseService.createManualLease(request, user.getId());
        return ResponseEntity.ok(response);
    }
    
    /**
     * Update lease terms
     * PUT /api/landlord/leases/{id}
     */
    @PutMapping("/landlord/leases/{id}")
    @PreAuthorize("hasRole('LANDLORD')")
    public ResponseEntity<LeaseResponse> updateLease(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @Valid @RequestBody UpdateLeaseRequest request) {
        
        log.info("Landlord {} updating lease: {}", user.getId(), id);
        LeaseResponse response = leaseService.updateLease(id, request, user.getId());
        return ResponseEntity.ok(response);
    }
    
    /**
     * Terminate lease early
     * POST /api/landlord/leases/{id}/terminate
     */
    @PostMapping("/landlord/leases/{id}/terminate")
    @PreAuthorize("hasRole('LANDLORD') or hasRole('TENANT')")
    public ResponseEntity<LeaseResponse> terminateLease(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @Valid @RequestBody TerminateLeaseRequest request) {
        
        log.info("User {} terminating lease: {}", user.getId(), id);
        LeaseResponse response = leaseService.terminateLease(id, request, user.getId());
        return ResponseEntity.ok(response);
    }
    
    /**
     * Renew lease
     * POST /api/landlord/leases/{id}/renew?newEndDate=2027-01-01
     */
    @PostMapping("/landlord/leases/{id}/renew")
    @PreAuthorize("hasRole('LANDLORD')")
    public ResponseEntity<LeaseResponse> renewLease(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @RequestParam LocalDate newEndDate) {
        
        log.info("Landlord {} renewing lease: {} with new end date: {}", user.getId(), id, newEndDate);
        LeaseResponse response = leaseService.renewLease(id, newEndDate, user.getId());
        return ResponseEntity.ok(response);
    }
    
    /**
     * Get active lease for a specific property
     * GET /api/landlord/properties/{propertyId}/lease/active
     */
    @GetMapping("/landlord/properties/{propertyId}/lease/active")
    @PreAuthorize("hasRole('LANDLORD')")
    public ResponseEntity<LeaseResponse> getActiveLeaseForProperty(
            @PathVariable Long propertyId) {
        
        log.info("Fetching active lease for property: {}", propertyId);
        LeaseResponse response = leaseService.getActiveLeaseForProperty(propertyId);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Get leases expiring soon (within 30 days)
     * GET /api/landlord/leases/expiring?days=30
     */
    @GetMapping("/landlord/leases/expiring")
    @PreAuthorize("hasRole('LANDLORD')")
    public ResponseEntity<?> getLeasesExpiringSoon(
            @RequestParam(defaultValue = "30") int days) {
        
        log.info("Fetching leases expiring within {} days", days);
        var response = leaseService.getLeasesExpiringSoon(days);
        return ResponseEntity.ok(response);
    }
}
