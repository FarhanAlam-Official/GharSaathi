package com.gharsaathi.admin.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gharsaathi.admin.dto.AdminUserDTO;
import com.gharsaathi.admin.dto.ChangeRoleRequest;
import com.gharsaathi.admin.dto.SuspendUserRequest;
import com.gharsaathi.admin.service.AdminUserService;
import com.gharsaathi.auth.model.Role;
import com.gharsaathi.auth.model.User;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * REST Controller for admin user management
 * Provides endpoints for user suspension, role changes, and user administration
 */
@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@Slf4j
public class AdminUserController {

    private final AdminUserService adminUserService;

    /**
     * Get all users with optional role filter
     * Accessible by: ADMIN only
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AdminUserDTO>> getAllUsers(
            @AuthenticationPrincipal User currentUser,
            @RequestParam(required = false) Role role) {
        log.info("Admin {} fetching all users with role filter: {}", currentUser.getEmail(), role);
        
        List<AdminUserDTO> users = adminUserService.getAllUsers(role);
        return ResponseEntity.ok(users);
    }

    /**
     * Get suspended users
     * Accessible by: ADMIN only
     */
    @GetMapping("/suspended")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AdminUserDTO>> getSuspendedUsers(@AuthenticationPrincipal User currentUser) {
        log.info("Admin {} fetching suspended users", currentUser.getEmail());
        
        List<AdminUserDTO> users = adminUserService.getSuspendedUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Get user details by ID
     * Accessible by: ADMIN only
     */
    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminUserDTO> getUserById(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long userId) {
        log.info("Admin {} fetching user details for ID: {}", currentUser.getEmail(), userId);
        
        AdminUserDTO user = adminUserService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    /**
     * Suspend user account
     * Accessible by: ADMIN only
     */
    @PatchMapping("/{userId}/suspend")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminUserDTO> suspendUser(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long userId,
            @Valid @RequestBody SuspendUserRequest request) {
        log.info("Admin {} suspending user {}: {}", currentUser.getEmail(), userId, request.getReason());
        
        AdminUserDTO updatedUser = adminUserService.suspendUser(userId, currentUser.getId(), request.getReason());
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Unsuspend (reactivate) user account
     * Accessible by: ADMIN only
     */
    @PatchMapping("/{userId}/unsuspend")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminUserDTO> unsuspendUser(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long userId) {
        log.info("Admin {} unsuspending user {}", currentUser.getEmail(), userId);
        
        AdminUserDTO updatedUser = adminUserService.unsuspendUser(userId, currentUser.getId());
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Change user role
     * Accessible by: ADMIN only
     */
    @PatchMapping("/{userId}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminUserDTO> changeUserRole(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long userId,
            @Valid @RequestBody ChangeRoleRequest request) {
        log.info("Admin {} changing role for user {} to {}", 
                currentUser.getEmail(), userId, request.getNewRole());
        
        AdminUserDTO updatedUser = adminUserService.changeUserRole(userId, currentUser.getId(), request);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Delete user account (soft delete)
     * Accessible by: ADMIN only
     */
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteUser(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long userId) {
        log.info("Admin {} deleting user {}", currentUser.getEmail(), userId);
        
        adminUserService.deleteUser(userId, currentUser.getId());
        return ResponseEntity.ok("User deleted successfully");
    }
}
