package com.gharsaathi.profile.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gharsaathi.auth.model.User;
import com.gharsaathi.profile.dto.ChangePasswordRequest;
import com.gharsaathi.profile.dto.ProfileDTO;
import com.gharsaathi.profile.dto.UpdateProfileRequest;
import com.gharsaathi.profile.service.ProfileService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * REST Controller for user profile management
 * Provides endpoints for profile viewing, updating, and password changes
 */
@RestController
@RequestMapping("/api/users/profile")
@RequiredArgsConstructor
@Slf4j
public class ProfileController {

    private final ProfileService profileService;

    /**
     * Get current user's profile
     * Accessible by: ALL authenticated users
     */
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ProfileDTO> getProfile(@AuthenticationPrincipal User currentUser) {
        log.info("Fetching profile for user: {}", currentUser.getEmail());
        
        ProfileDTO profile = profileService.getProfile(currentUser.getId());
        return ResponseEntity.ok(profile);
    }

    /**
     * Update current user's profile information
     * Accessible by: ALL authenticated users
     */
    @PutMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ProfileDTO> updateProfile(
            @AuthenticationPrincipal User currentUser,
            @Valid @RequestBody UpdateProfileRequest request) {
        log.info("Updating profile for user: {}", currentUser.getEmail());
        
        ProfileDTO updatedProfile = profileService.updateProfile(currentUser.getId(), request);
        return ResponseEntity.ok(updatedProfile);
    }

    /**
     * Change current user's password
     * Accessible by: ALL authenticated users
     */
    @PatchMapping("/password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> changePassword(
            @AuthenticationPrincipal User currentUser,
            @Valid @RequestBody ChangePasswordRequest request) {
        log.info("Password change requested for user: {}", currentUser.getEmail());
        
        profileService.changePassword(currentUser.getId(), request);
        return ResponseEntity.ok("Password changed successfully");
    }

    /**
     * Verify email (typically called after email verification link)
     * Accessible by: ALL authenticated users
     */
    @PatchMapping("/verify-email")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ProfileDTO> verifyEmail(@AuthenticationPrincipal User currentUser) {
        log.info("Email verification requested for user: {}", currentUser.getEmail());
        
        ProfileDTO updatedProfile = profileService.verifyEmail(currentUser.getId());
        return ResponseEntity.ok(updatedProfile);
    }

    /**
     * Verify phone (typically called after OTP verification)
     * Accessible by: ALL authenticated users
     */
    @PatchMapping("/verify-phone")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ProfileDTO> verifyPhone(@AuthenticationPrincipal User currentUser) {
        log.info("Phone verification requested for user: {}", currentUser.getEmail());
        
        ProfileDTO updatedProfile = profileService.verifyPhone(currentUser.getId());
        return ResponseEntity.ok(updatedProfile);
    }
}
