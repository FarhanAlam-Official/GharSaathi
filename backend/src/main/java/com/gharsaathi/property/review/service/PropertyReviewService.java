package com.gharsaathi.property.review.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gharsaathi.auth.model.Role;
import com.gharsaathi.auth.model.User;
import com.gharsaathi.auth.repository.UserRepository;
import com.gharsaathi.property.review.dto.CreateReviewRequest;
import com.gharsaathi.property.review.dto.PropertyRatingStatsDTO;
import com.gharsaathi.property.review.dto.PropertyReviewDTO;
import com.gharsaathi.property.review.dto.UpdateReviewRequest;
import com.gharsaathi.property.review.model.PropertyReview;
import com.gharsaathi.property.review.repository.PropertyReviewRepository;
import com.gharsaathi.lease.model.Lease;
import com.gharsaathi.lease.repository.LeaseRepository;
import com.gharsaathi.property.model.Property;
import com.gharsaathi.property.repository.PropertyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service for managing property reviews and ratings
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PropertyReviewService {

    private final PropertyReviewRepository reviewRepository;
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final LeaseRepository leaseRepository;

    /**
     * Create a new review for a property
     */
    public PropertyReviewDTO createReview(CreateReviewRequest request, Long tenantId) {
        log.info("Creating review for property {} by tenant {}", request.getPropertyId(), tenantId);
        
        // Check if property exists
        Property property = propertyRepository.findById(request.getPropertyId())
                .orElseThrow(() -> new RuntimeException("Property not found with id: " + request.getPropertyId()));
        
        // Check if tenant exists
        User tenant = userRepository.findById(tenantId)
                .orElseThrow(() -> new RuntimeException("Tenant not found with id: " + tenantId));
        
        // Verify tenant role
        if (tenant.getRole() != Role.TENANT) {
            throw new RuntimeException("Only tenants can create reviews");
        }
        
        // Check if tenant has already reviewed this property
        if (reviewRepository.existsByTenantIdAndPropertyId(tenantId, request.getPropertyId())) {
            throw new RuntimeException("You have already reviewed this property");
        }
        
        // Verify lease if provided
        Lease lease = null;
        if (request.getLeaseId() != null) {
            lease = leaseRepository.findById(request.getLeaseId())
                    .orElseThrow(() -> new RuntimeException("Lease not found with id: " + request.getLeaseId()));
            
            // Verify lease belongs to tenant and property
            if (!lease.getTenant().getId().equals(tenantId)) {
                throw new RuntimeException("Lease does not belong to you");
            }
            if (!lease.getProperty().getId().equals(request.getPropertyId())) {
                throw new RuntimeException("Lease is not for this property");
            }
        }
        
        // Create review
        PropertyReview review = PropertyReview.builder()
                .property(property)
                .tenant(tenant)
                .lease(lease)
                .rating(request.getRating())
                .comment(request.getComment())
                .build();
        
        review = reviewRepository.save(review);
        log.info("Review created with id: {}", review.getId());
        
        return mapToDTO(review);
    }

    /**
     * Update an existing review
     */
    public PropertyReviewDTO updateReview(Long reviewId, UpdateReviewRequest request, Long tenantId) {
        log.info("Updating review {} by tenant {}", reviewId, tenantId);
        
        PropertyReview review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + reviewId));
        
        // Verify ownership
        if (!review.getTenant().getId().equals(tenantId)) {
            throw new RuntimeException("You don't have permission to update this review");
        }
        
        // Update fields if provided
        if (request.getRating() != null) {
            review.setRating(request.getRating());
        }
        if (request.getComment() != null) {
            review.setComment(request.getComment());
        }
        
        review.setUpdatedAt(LocalDateTime.now());
        review = reviewRepository.save(review);
        
        log.info("Review {} updated successfully", reviewId);
        return mapToDTO(review);
    }

    /**
     * Delete a review
     */
    public String deleteReview(Long reviewId, Long tenantId) {
        log.info("Deleting review {} by tenant {}", reviewId, tenantId);
        
        PropertyReview review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + reviewId));
        
        // Verify ownership
        if (!review.getTenant().getId().equals(tenantId)) {
            throw new RuntimeException("You don't have permission to delete this review");
        }
        
        reviewRepository.delete(review);
        log.info("Review {} deleted successfully", reviewId);
        
        return "Review deleted successfully";
    }

    /**
     * Get all reviews for a property
     */
    public List<PropertyReviewDTO> getPropertyReviews(Long propertyId) {
        log.info("Fetching all reviews for property: {}", propertyId);
        
        List<PropertyReview> reviews = reviewRepository.findByPropertyIdOrderByCreatedAtDesc(propertyId);
        return reviews.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get verified reviews for a property
     */
    public List<PropertyReviewDTO> getVerifiedPropertyReviews(Long propertyId) {
        log.info("Fetching verified reviews for property: {}", propertyId);
        
        List<PropertyReview> reviews = reviewRepository.findByPropertyIdAndIsVerifiedTrueOrderByCreatedAtDesc(propertyId);
        return reviews.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get all reviews by a tenant
     */
    public List<PropertyReviewDTO> getTenantReviews(Long tenantId) {
        log.info("Fetching all reviews by tenant: {}", tenantId);
        
        List<PropertyReview> reviews = reviewRepository.findByTenantIdOrderByCreatedAtDesc(tenantId);
        return reviews.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get all reviews for properties owned by landlord
     */
    public List<PropertyReviewDTO> getLandlordPropertyReviews(Long landlordId) {
        log.info("Fetching all reviews for landlord's properties: {}", landlordId);
        
        List<PropertyReview> reviews = reviewRepository.findByLandlordId(landlordId);
        return reviews.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get rating statistics for a property
     */
    public PropertyRatingStatsDTO getPropertyRatingStats(Long propertyId) {
        log.info("Calculating rating statistics for property: {}", propertyId);
        
        // Verify property exists
        propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with id: " + propertyId));
        
        Double averageRating = reviewRepository.findAverageRatingByPropertyId(propertyId);
        Long totalReviews = reviewRepository.countByPropertyId(propertyId);
        Long verifiedReviews = reviewRepository.countByPropertyIdAndIsVerifiedTrue(propertyId);
        
        return PropertyRatingStatsDTO.builder()
                .propertyId(propertyId)
                .averageRating(averageRating != null ? Math.round(averageRating * 10.0) / 10.0 : 0.0)
                .totalReviews(totalReviews)
                .verifiedReviews(verifiedReviews)
                .fiveStarCount(reviewRepository.countByPropertyIdAndRating(propertyId, 5))
                .fourStarCount(reviewRepository.countByPropertyIdAndRating(propertyId, 4))
                .threeStarCount(reviewRepository.countByPropertyIdAndRating(propertyId, 3))
                .twoStarCount(reviewRepository.countByPropertyIdAndRating(propertyId, 2))
                .oneStarCount(reviewRepository.countByPropertyIdAndRating(propertyId, 1))
                .build();
    }

    /**
     * Verify a review (admin only)
     */
    public PropertyReviewDTO verifyReview(Long reviewId, Long adminId) {
        log.info("Admin {} verifying review: {}", adminId, reviewId);
        
        PropertyReview review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + reviewId));
        
        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        
        if (review.getIsVerified()) {
            throw new RuntimeException("Review is already verified");
        }
        
        review.setIsVerified(true);
        review.setVerifiedAt(LocalDateTime.now());
        review.setVerifiedBy(admin);
        
        review = reviewRepository.save(review);
        log.info("Review {} verified successfully", reviewId);
        
        return mapToDTO(review);
    }

    /**
     * Unverify a review (admin only)
     */
    public PropertyReviewDTO unverifyReview(Long reviewId, Long adminId) {
        log.info("Admin {} unverifying review: {}", adminId, reviewId);
        
        PropertyReview review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + reviewId));
        
        if (!review.getIsVerified()) {
            throw new RuntimeException("Review is not verified");
        }
        
        review.setIsVerified(false);
        review.setVerifiedAt(null);
        review.setVerifiedBy(null);
        
        review = reviewRepository.save(review);
        log.info("Review {} unverified successfully", reviewId);
        
        return mapToDTO(review);
    }

    /**
     * Delete review (admin only)
     */
    public String adminDeleteReview(Long reviewId, Long adminId) {
        log.info("Admin {} deleting review: {}", adminId, reviewId);
        
        PropertyReview review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + reviewId));
        
        reviewRepository.delete(review);
        log.info("Review {} deleted by admin", reviewId);
        
        return "Review deleted successfully";
    }

    /**
     * Map PropertyReview entity to DTO
     */
    private PropertyReviewDTO mapToDTO(PropertyReview review) {
        return PropertyReviewDTO.builder()
                .id(review.getId())
                .propertyId(review.getProperty().getId())
                .propertyTitle(review.getProperty().getTitle())
                .tenantId(review.getTenant().getId())
                .tenantName(review.getTenant().getFullName())
                .leaseId(review.getLease() != null ? review.getLease().getId() : null)
                .rating(review.getRating())
                .comment(review.getComment())
                .isVerified(review.getIsVerified())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .build();
    }
}
