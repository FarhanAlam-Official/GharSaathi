package com.gharsaathi.property.review.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gharsaathi.property.review.dto.CreateReviewRequest;
import com.gharsaathi.property.review.dto.PropertyRatingStatsDTO;
import com.gharsaathi.property.review.dto.PropertyReviewDTO;
import com.gharsaathi.property.review.dto.UpdateReviewRequest;
import com.gharsaathi.property.review.service.PropertyReviewService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controller for managing property reviews and ratings
 */
@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class PropertyReviewController {

    private final PropertyReviewService reviewService;

    /**
     * Create a new review (tenant only)
     */
    @PostMapping
    @PreAuthorize("hasRole('TENANT')")
    public ResponseEntity<PropertyReviewDTO> createReview(
            @Valid @RequestBody CreateReviewRequest request,
            Authentication authentication) {
        Long tenantId = getUserIdFromAuth(authentication);
        PropertyReviewDTO review = reviewService.createReview(request, tenantId);
        return ResponseEntity.ok(review);
    }

    /**
     * Update own review (tenant only)
     */
    @PutMapping("/{reviewId}")
    @PreAuthorize("hasRole('TENANT')")
    public ResponseEntity<PropertyReviewDTO> updateReview(
            @PathVariable Long reviewId,
            @Valid @RequestBody UpdateReviewRequest request,
            Authentication authentication) {
        Long tenantId = getUserIdFromAuth(authentication);
        PropertyReviewDTO review = reviewService.updateReview(reviewId, request, tenantId);
        return ResponseEntity.ok(review);
    }

    /**
     * Delete own review (tenant only)
     */
    @DeleteMapping("/{reviewId}")
    @PreAuthorize("hasRole('TENANT')")
    public ResponseEntity<String> deleteReview(
            @PathVariable Long reviewId,
            Authentication authentication) {
        Long tenantId = getUserIdFromAuth(authentication);
        String message = reviewService.deleteReview(reviewId, tenantId);
        return ResponseEntity.ok(message);
    }

    /**
     * Get all reviews for a property (public)
     */
    @GetMapping("/property/{propertyId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<PropertyReviewDTO>> getPropertyReviews(@PathVariable Long propertyId) {
        List<PropertyReviewDTO> reviews = reviewService.getPropertyReviews(propertyId);
        return ResponseEntity.ok(reviews);
    }

    /**
     * Get verified reviews for a property (public)
     */
    @GetMapping("/property/{propertyId}/verified")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<PropertyReviewDTO>> getVerifiedPropertyReviews(@PathVariable Long propertyId) {
        List<PropertyReviewDTO> reviews = reviewService.getVerifiedPropertyReviews(propertyId);
        return ResponseEntity.ok(reviews);
    }

    /**
     * Get rating statistics for a property (public)
     */
    @GetMapping("/property/{propertyId}/stats")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PropertyRatingStatsDTO> getPropertyRatingStats(@PathVariable Long propertyId) {
        PropertyRatingStatsDTO stats = reviewService.getPropertyRatingStats(propertyId);
        return ResponseEntity.ok(stats);
    }

    /**
     * Get own reviews (tenant only)
     */
    @GetMapping("/my-reviews")
    @PreAuthorize("hasRole('TENANT')")
    public ResponseEntity<List<PropertyReviewDTO>> getMyReviews(Authentication authentication) {
        Long tenantId = getUserIdFromAuth(authentication);
        List<PropertyReviewDTO> reviews = reviewService.getTenantReviews(tenantId);
        return ResponseEntity.ok(reviews);
    }

    /**
     * Get reviews for landlord's properties (landlord only)
     */
    @GetMapping("/landlord/my-properties")
    @PreAuthorize("hasRole('LANDLORD')")
    public ResponseEntity<List<PropertyReviewDTO>> getLandlordPropertyReviews(Authentication authentication) {
        Long landlordId = getUserIdFromAuth(authentication);
        List<PropertyReviewDTO> reviews = reviewService.getLandlordPropertyReviews(landlordId);
        return ResponseEntity.ok(reviews);
    }

    /**
     * Verify a review (admin only)
     */
    @PatchMapping("/{reviewId}/verify")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PropertyReviewDTO> verifyReview(
            @PathVariable Long reviewId,
            Authentication authentication) {
        Long adminId = getUserIdFromAuth(authentication);
        PropertyReviewDTO review = reviewService.verifyReview(reviewId, adminId);
        return ResponseEntity.ok(review);
    }

    /**
     * Unverify a review (admin only)
     */
    @PatchMapping("/{reviewId}/unverify")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PropertyReviewDTO> unverifyReview(
            @PathVariable Long reviewId,
            Authentication authentication) {
        Long adminId = getUserIdFromAuth(authentication);
        PropertyReviewDTO review = reviewService.unverifyReview(reviewId, adminId);
        return ResponseEntity.ok(review);
    }

    /**
     * Delete review (admin only)
     */
    @DeleteMapping("/admin/{reviewId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> adminDeleteReview(
            @PathVariable Long reviewId,
            Authentication authentication) {
        Long adminId = getUserIdFromAuth(authentication);
        String message = reviewService.adminDeleteReview(reviewId, adminId);
        return ResponseEntity.ok(message);
    }

    /**
     * Helper method to extract user ID from authentication
     */
    private Long getUserIdFromAuth(Authentication authentication) {
        return Long.parseLong(authentication.getName());
    }
}
