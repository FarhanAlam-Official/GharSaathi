package com.gharsaathi.admin.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gharsaathi.admin.dto.AdminUserDTO;
import com.gharsaathi.admin.dto.ChangeRoleRequest;
import com.gharsaathi.auth.model.Role;
import com.gharsaathi.auth.model.User;
import com.gharsaathi.auth.repository.UserRepository;
import com.gharsaathi.lease.model.LeaseStatus;
import com.gharsaathi.lease.repository.LeaseRepository;
import com.gharsaathi.property.repository.PropertyRepository;
import com.gharsaathi.rental.application.repository.RentalApplicationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service for admin user management operations
 * Handles user suspension, role changes, and user details retrieval
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AdminUserService {

    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;
    private final RentalApplicationRepository rentalApplicationRepository;
    private final LeaseRepository leaseRepository;

    /**
     * Get all users with optional role filter
     */
    @Transactional(readOnly = true)
    public List<AdminUserDTO> getAllUsers(Role roleFilter) {
        log.info("Fetching all users with role filter: {}", roleFilter);
        
        List<User> users = roleFilter != null 
            ? userRepository.findByRole(roleFilter)
            : userRepository.findAll();

        return users.stream()
                .map(this::mapToAdminUserDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get suspended users
     */
    @Transactional(readOnly = true)
    public List<AdminUserDTO> getSuspendedUsers() {
        log.info("Fetching suspended users");
        
        List<User> users = userRepository.findByEnabled(false);
        return users.stream()
                .map(this::mapToAdminUserDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get user details by ID
     */
    @Transactional(readOnly = true)
    public AdminUserDTO getUserById(Long userId) {
        log.info("Fetching user details for ID: {}", userId);
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        return mapToAdminUserDTO(user);
    }

    /**
     * Suspend user account
     */
    @Transactional
    public AdminUserDTO suspendUser(Long userId, Long adminId, String reason) {
        log.info("Admin {} suspending user {}: {}", adminId, userId, reason);
        
        // Prevent self-suspension
        if (userId.equals(adminId)) {
            throw new RuntimeException("Admins cannot suspend themselves");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        if (!user.getEnabled()) {
            log.warn("User {} is already suspended", userId);
            throw new RuntimeException("User is already suspended");
        }

        user.setEnabled(false);
        User savedUser = userRepository.save(user);
        
        log.info("User {} suspended successfully", userId);
        return mapToAdminUserDTO(savedUser);
    }

    /**
     * Unsuspend (reactivate) user account
     */
    @Transactional
    public AdminUserDTO unsuspendUser(Long userId, Long adminId) {
        log.info("Admin {} unsuspending user {}", adminId, userId);
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        if (user.getEnabled()) {
            log.warn("User {} is already active", userId);
            throw new RuntimeException("User is already active");
        }

        user.setEnabled(true);
        User savedUser = userRepository.save(user);
        
        log.info("User {} unsuspended successfully", userId);
        return mapToAdminUserDTO(savedUser);
    }

    /**
     * Change user role
     */
    @Transactional
    public AdminUserDTO changeUserRole(Long userId, Long adminId, ChangeRoleRequest request) {
        log.info("Admin {} changing role for user {} to {}: {}", 
                adminId, userId, request.getNewRole(), request.getReason());
        
        // Prevent changing own role
        if (userId.equals(adminId)) {
            throw new RuntimeException("Admins cannot change their own role");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Role oldRole = user.getRole();
        if (oldRole == request.getNewRole()) {
            throw new RuntimeException("User already has role: " + request.getNewRole());
        }

        user.setRole(request.getNewRole());
        User savedUser = userRepository.save(user);
        
        log.info("User {} role changed from {} to {}", userId, oldRole, request.getNewRole());
        return mapToAdminUserDTO(savedUser);
    }

    /**
     * Delete user account (soft delete - just suspend)
     */
    @Transactional
    public void deleteUser(Long userId, Long adminId) {
        log.info("Admin {} deleting user {}", adminId, userId);
        
        // Prevent self-deletion
        if (userId.equals(adminId)) {
            throw new RuntimeException("Admins cannot delete themselves");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // Soft delete - just suspend the account
        user.setEnabled(false);
        userRepository.save(user);
        
        log.info("User {} deleted (suspended) successfully", userId);
    }

    /**
     * Get user statistics by role
     */
    @Transactional(readOnly = true)
    public long getUserCountByRole(Role role) {
        return userRepository.countByRole(role);
    }

    /**
     * Map User entity to AdminUserDTO with aggregations
     */
    private AdminUserDTO mapToAdminUserDTO(User user) {
        AdminUserDTO dto = AdminUserDTO.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .enabled(user.getEnabled())
                .profilePicture(user.getProfilePicture())
                .emailVerified(user.getEmailVerified())
                .phoneVerified(user.getPhoneVerified())
                .lastLogin(user.getLastLogin())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();

        // Add role-specific aggregations
        switch (user.getRole()) {
            case LANDLORD:
                dto.setPropertyCount(propertyRepository.countByLandlordId(user.getId()));
                dto.setActiveLeaseCount(leaseRepository.countByLandlordIdAndStatus(
                        user.getId(), LeaseStatus.ACTIVE));
                break;
            case TENANT:
                dto.setApplicationCount(rentalApplicationRepository.countByTenantId(user.getId()));
                dto.setActiveLeaseCount(leaseRepository.countByTenantIdAndStatus(
                        user.getId(), LeaseStatus.ACTIVE));
                break;
            case ADMIN:
                // No aggregations for admin users
                break;
        }

        return dto;
    }
}
