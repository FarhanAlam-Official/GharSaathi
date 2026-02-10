package com.gharsaathi.property.review.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gharsaathi.property.review.model.PropertyReview;

@Repository
public interface PropertyReviewRepository extends JpaRepository<PropertyReview, Long> {
    
    // Find all reviews for a property
    List<PropertyReview> findByPropertyIdOrderByCreatedAtDesc(Long propertyId);
    
    // Find verified reviews for a property
    List<PropertyReview> findByPropertyIdAndIsVerifiedTrueOrderByCreatedAtDesc(Long propertyId);
    
    // Find all reviews by a tenant
    List<PropertyReview> findByTenantIdOrderByCreatedAtDesc(Long tenantId);
    
    // Find review by tenant and property
    Optional<PropertyReview> findByTenantIdAndPropertyId(Long tenantId, Long propertyId);
    
    // Find review by tenant and lease
    Optional<PropertyReview> findByTenantIdAndLeaseId(Long tenantId, Long leaseId);
    
    // Count reviews for a property
    Long countByPropertyId(Long propertyId);
    
    // Count verified reviews for a property
    Long countByPropertyIdAndIsVerifiedTrue(Long propertyId);
    
    // Calculate average rating for a property
    @Query("SELECT AVG(pr.rating) FROM PropertyReview pr WHERE pr.property.id = :propertyId")
    Double findAverageRatingByPropertyId(@Param("propertyId") Long propertyId);
    
    // Calculate average verified rating for a property
    @Query("SELECT AVG(pr.rating) FROM PropertyReview pr WHERE pr.property.id = :propertyId AND pr.isVerified = true")
    Double findAverageVerifiedRatingByPropertyId(@Param("propertyId") Long propertyId);
    
    // Count reviews by rating for a property
    Long countByPropertyIdAndRating(Long propertyId, Integer rating);
    
    // Get reviews by landlord (all properties owned by landlord)
    @Query("SELECT pr FROM PropertyReview pr WHERE pr.property.landlord.id = :landlordId ORDER BY pr.createdAt DESC")
    List<PropertyReview> findByLandlordId(@Param("landlordId") Long landlordId);
    
    // Check if tenant has already reviewed property
    boolean existsByTenantIdAndPropertyId(Long tenantId, Long propertyId);
}
