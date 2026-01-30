package com.gharsaathi.rental.application.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gharsaathi.rental.application.dto.ApplicationListResponse;
import com.gharsaathi.rental.application.dto.ApplicationResponse;
import com.gharsaathi.rental.application.dto.CreateApplicationRequest;
import com.gharsaathi.rental.application.service.RentalApplicationService;
import com.gharsaathi.auth.model.User;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for rental application operations
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
@Slf4j
public class RentalApplicationController {

    private final RentalApplicationService applicationService;

    // ==================== TENANT ENDPOINTS ====================

    /**
     * Submit a new rental application (TENANT only)
     */
    @PostMapping("/tenant/applications")
    @PreAuthorize("hasRole('TENANT')")
    public ResponseEntity<ApplicationResponse> submitApplication(
            @Valid @RequestBody CreateApplicationRequest request,
            Authentication authentication) {
        log.info("Received request to submit application for property {}", request.getPropertyId());
        
        Long tenantId = getCurrentUserId(authentication);
        ApplicationResponse response = applicationService.submitApplication(request, tenantId);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Get all applications submitted by current tenant
     */
    @GetMapping("/tenant/applications")
    @PreAuthorize("hasRole('TENANT')")
    public ResponseEntity<ApplicationListResponse> getMyApplications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection,
            Authentication authentication) {
        log.info("Fetching applications for tenant");
        
        Long tenantId = getCurrentUserId(authentication);
        ApplicationListResponse response = applicationService.getMyApplications(
            tenantId, page, size, sortBy, sortDirection
        );
        
        return ResponseEntity.ok(response);
    }

    /**
     * Get specific application by ID for tenant
     */
    @GetMapping("/tenant/applications/{id}")
    @PreAuthorize("hasRole('TENANT')")
    public ResponseEntity<ApplicationResponse> getApplicationById(
            @PathVariable Long id,
            Authentication authentication) {
        log.info("Fetching application {} for tenant", id);
        
        Long tenantId = getCurrentUserId(authentication);
        ApplicationResponse response = applicationService.getApplicationById(id, tenantId);
        
        return ResponseEntity.ok(response);
    }

    /**
     * Withdraw an application (TENANT only)
     */
    @PatchMapping("/tenant/applications/{id}/withdraw")
    @PreAuthorize("hasRole('TENANT')")
    public ResponseEntity<ApplicationResponse> withdrawApplication(
            @PathVariable Long id,
            Authentication authentication) {
        log.info("Received request to withdraw application {}", id);
        
        Long tenantId = getCurrentUserId(authentication);
        ApplicationResponse response = applicationService.withdrawApplication(id, tenantId);
        
        return ResponseEntity.ok(response);
    }

    /**
     * Get application statistics for tenant
     */
    @GetMapping("/tenant/applications/statistics")
    @PreAuthorize("hasRole('TENANT')")
    public ResponseEntity<Map<String, Long>> getTenantStatistics(Authentication authentication) {
        log.info("Fetching application statistics for tenant");
        
        Long tenantId = getCurrentUserId(authentication);
        Map<String, Long> stats = applicationService.getTenantStatistics(tenantId);
        
        return ResponseEntity.ok(stats);
    }

    // ==================== LANDLORD ENDPOINTS ====================

    /**
     * Get all applications for properties owned by landlord
     */
    @GetMapping("/landlord/applications")
    @PreAuthorize("hasRole('LANDLORD')")
    public ResponseEntity<ApplicationListResponse> getApplicationsForMyProperties(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection,
            Authentication authentication) {
        log.info("Fetching applications for landlord properties");
        
        Long landlordId = getCurrentUserId(authentication);
        ApplicationListResponse response = applicationService.getApplicationsForMyProperties(
            landlordId, page, size, sortBy, sortDirection
        );
        
        return ResponseEntity.ok(response);
    }

    /**
     * Get applications for a specific property
     */
    @GetMapping("/landlord/properties/{propertyId}/applications")
    @PreAuthorize("hasRole('LANDLORD')")
    public ResponseEntity<ApplicationListResponse> getApplicationsForProperty(
            @PathVariable Long propertyId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Authentication authentication) {
        log.info("Fetching applications for property {}", propertyId);
        
        Long landlordId = getCurrentUserId(authentication);
        ApplicationListResponse response = applicationService.getApplicationsForProperty(
            propertyId, landlordId, page, size
        );
        
        return ResponseEntity.ok(response);
    }

    /**
     * Approve an application (LANDLORD only)
     */
    @PatchMapping("/landlord/applications/{id}/approve")
    @PreAuthorize("hasRole('LANDLORD')")
    public ResponseEntity<ApplicationResponse> approveApplication(
            @PathVariable Long id,
            @RequestBody(required = false) Map<String, String> body,
            Authentication authentication) {
        log.info("Received request to approve application {}", id);
        
        Long landlordId = getCurrentUserId(authentication);
        String landlordResponse = body != null ? body.get("landlordResponse") : null;
        ApplicationResponse response = applicationService.approveApplication(id, landlordId, landlordResponse);
        
        return ResponseEntity.ok(response);
    }

    /**
     * Reject an application (LANDLORD only)
     */
    @PatchMapping("/landlord/applications/{id}/reject")
    @PreAuthorize("hasRole('LANDLORD')")
    public ResponseEntity<ApplicationResponse> rejectApplication(
            @PathVariable Long id,
            @RequestBody(required = false) Map<String, String> body,
            Authentication authentication) {
        log.info("Received request to reject application {}", id);
        
        Long landlordId = getCurrentUserId(authentication);
        String landlordResponse = body != null ? body.get("landlordResponse") : null;
        ApplicationResponse response = applicationService.rejectApplication(id, landlordId, landlordResponse);
        
        return ResponseEntity.ok(response);
    }

    /**
     * Get application statistics for landlord
     */
    @GetMapping("/landlord/applications/statistics")
    @PreAuthorize("hasRole('LANDLORD')")
    public ResponseEntity<Map<String, Long>> getLandlordStatistics(Authentication authentication) {
        log.info("Fetching application statistics for landlord");
        
        Long landlordId = getCurrentUserId(authentication);
        Map<String, Long> stats = applicationService.getLandlordStatistics(landlordId);
        
        return ResponseEntity.ok(stats);
    }

    // ==================== HELPER METHODS ====================

    /**
     * Extract user ID from authentication object
     */
    private Long getCurrentUserId(Authentication authentication) {
        User user = (com.gharsaathi.auth.model.User) authentication.getPrincipal();
        return user.getId();
    }
}
