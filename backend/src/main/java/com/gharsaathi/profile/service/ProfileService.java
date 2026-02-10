package com.gharsaathi.profile.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gharsaathi.auth.model.User;
import com.gharsaathi.auth.repository.UserRepository;
import com.gharsaathi.profile.dto.ChangePasswordRequest;
import com.gharsaathi.profile.dto.ProfileDTO;
import com.gharsaathi.profile.dto.UpdateProfileRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service for managing user profile operations
 * Handles profile updates, password changes, and profile picture management
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Get user profile by user ID
     */
    @Transactional(readOnly = true)
    public ProfileDTO getProfile(Long userId) {
        log.info("Fetching profile for user ID: {}", userId);
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        return mapToProfileDTO(user);
    }

    /**
     * Update user profile information (excluding password)
     */
    @Transactional
    public ProfileDTO updateProfile(Long userId, UpdateProfileRequest request) {
        log.info("Updating profile for user ID: {}", userId);
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // Update fields if provided
        if (request.getFullName() != null && !request.getFullName().trim().isEmpty()) {
            user.setFullName(request.getFullName().trim());
        }

        if (request.getEmail() != null && !request.getEmail().trim().isEmpty()) {
            // Check if email is already taken by another user
            if (!user.getEmail().equals(request.getEmail())) {
                boolean emailExists = userRepository.existsByEmail(request.getEmail());
                if (emailExists) {
                    throw new RuntimeException("Email is already in use: " + request.getEmail());
                }
                user.setEmail(request.getEmail().trim());
                user.setEmailVerified(false); // Reset email verification
            }
        }

        if (request.getPhoneNumber() != null) {
            if (request.getPhoneNumber().trim().isEmpty()) {
                user.setPhoneNumber(null);
                user.setPhoneVerified(false);
            } else if (!user.getPhoneNumber().equals(request.getPhoneNumber())) {
                user.setPhoneNumber(request.getPhoneNumber().trim());
                user.setPhoneVerified(false); // Reset phone verification
            }
        }

        if (request.getProfilePicture() != null) {
            user.setProfilePicture(request.getProfilePicture().trim().isEmpty() ? null : request.getProfilePicture().trim());
        }

        User savedUser = userRepository.save(user);
        log.info("Profile updated successfully for user ID: {}", userId);
        
        return mapToProfileDTO(savedUser);
    }

    /**
     * Change user password with validation
     */
    @Transactional
    public void changePassword(Long userId, ChangePasswordRequest request) {
        log.info("Attempting password change for user ID: {}", userId);
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // Validate current password
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            log.warn("Password change failed for user ID {}: Invalid current password", userId);
            throw new RuntimeException("Current password is incorrect");
        }

        // Validate new password matches confirmation
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            log.warn("Password change failed for user ID {}: New passwords do not match", userId);
            throw new RuntimeException("New password and confirmation do not match");
        }

        // Validate new password is different from current
        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
            log.warn("Password change failed for user ID {}: New password same as current", userId);
            throw new RuntimeException("New password must be different from current password");
        }

        // Update password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        
        log.info("Password changed successfully for user ID: {}", userId);
    }

    /**
     * Update profile picture URL
     */
    @Transactional
    public ProfileDTO updateProfilePicture(Long userId, String profilePictureUrl) {
        log.info("Updating profile picture for user ID: {}", userId);
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        user.setProfilePicture(profilePictureUrl);
        User savedUser = userRepository.save(user);
        
        log.info("Profile picture updated successfully for user ID: {}", userId);
        return mapToProfileDTO(savedUser);
    }

    /**
     * Update last login timestamp
     */
    @Transactional
    public void updateLastLogin(Long userId) {
        log.debug("Updating last login for user ID: {}", userId);
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
    }

    /**
     * Verify email (admin or verification link)
     */
    @Transactional
    public ProfileDTO verifyEmail(Long userId) {
        log.info("Verifying email for user ID: {}", userId);
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        user.setEmailVerified(true);
        User savedUser = userRepository.save(user);
        
        log.info("Email verified successfully for user ID: {}", userId);
        return mapToProfileDTO(savedUser);
    }

    /**
     * Verify phone (admin or OTP verification)
     */
    @Transactional
    public ProfileDTO verifyPhone(Long userId) {
        log.info("Verifying phone for user ID: {}", userId);
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        user.setPhoneVerified(true);
        User savedUser = userRepository.save(user);
        
        log.info("Phone verified successfully for user ID: {}", userId);
        return mapToProfileDTO(savedUser);
    }

    /**
     * Map User entity to ProfileDTO
     */
    private ProfileDTO mapToProfileDTO(User user) {
        return ProfileDTO.builder()
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
    }
}
