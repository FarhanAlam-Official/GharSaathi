package com.gharsaathi.dashboard.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gharsaathi.auth.model.User;
import com.gharsaathi.dashboard.dto.AdminDashboardResponse;
import com.gharsaathi.dashboard.dto.LandlordDashboardResponse;
import com.gharsaathi.dashboard.dto.TenantDashboardResponse;
import com.gharsaathi.dashboard.service.DashboardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * REST Controller for dashboard endpoints
 * Provides role-specific dashboards for tenants, landlords, and admins
 */
@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@Slf4j
public class DashboardController {

    private final DashboardService dashboardService;

    /**
     * Get tenant dashboard with personalized statistics
     * Endpoint: GET /api/dashboard/tenant
     * Access: TENANT role only
     */
    @GetMapping("/tenant")
    @PreAuthorize("hasRole('TENANT')")
    public ResponseEntity<TenantDashboardResponse> getTenantDashboard(
            @AuthenticationPrincipal User currentUser) {
        log.info("Tenant dashboard requested by user: {}", currentUser.getEmail());
        
        TenantDashboardResponse response = dashboardService.getTenantDashboard(currentUser.getId());
        return ResponseEntity.ok(response);
    }

    /**
     * Get landlord dashboard with business metrics
     * Endpoint: GET /api/dashboard/landlord
     * Access: LANDLORD role only
     */
    @GetMapping("/landlord")
    @PreAuthorize("hasRole('LANDLORD')")
    public ResponseEntity<LandlordDashboardResponse> getLandlordDashboard(
            @AuthenticationPrincipal User currentUser) {
        log.info("Landlord dashboard requested by user: {}", currentUser.getEmail());
        
        LandlordDashboardResponse response = dashboardService.getLandlordDashboard(currentUser.getId());
        return ResponseEntity.ok(response);
    }

    /**
     * Get admin dashboard with platform-wide statistics
     * Endpoint: GET /api/dashboard/admin
     * Access: ADMIN role only
     */
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminDashboardResponse> getAdminDashboard(
            @AuthenticationPrincipal User currentUser) {
        log.info("Admin dashboard requested by user: {}", currentUser.getEmail());
        
        AdminDashboardResponse response = dashboardService.getAdminDashboard();
        return ResponseEntity.ok(response);
    }
}
