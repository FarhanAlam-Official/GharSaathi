package com.gharsaathi.property.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gharsaathi.property.model.PropertyImage;

/**
 * Repository interface for PropertyImage entity
 */
@Repository
public interface PropertyImageRepository extends JpaRepository<PropertyImage, Long> {
    
    /**
     * Find all images for a specific property
     */
    List<PropertyImage> findByPropertyId(Long propertyId);
    
    /**
     * Find all images for a property ordered by display order
     */
    List<PropertyImage> findByPropertyIdOrderByDisplayOrderAsc(Long propertyId);
    
    /**
     * Find the primary image for a property
     */
    Optional<PropertyImage> findByPropertyIdAndIsPrimaryTrue(Long propertyId);
    
    /**
     * Delete all images for a specific property
     */
    @Modifying
    @Query("DELETE FROM PropertyImage pi WHERE pi.property.id = :propertyId")
    void deleteByPropertyId(@Param("propertyId") Long propertyId);
    
    /**
     * Check if a property has any images
     */
    boolean existsByPropertyId(Long propertyId);
    
    /**
     * Count images for a property
     */
    long countByPropertyId(Long propertyId);
    
    /**
     * Find all images by property ID that are marked as primary
     */
    @Query("SELECT pi FROM PropertyImage pi WHERE pi.property.id = :propertyId AND pi.isPrimary = true")
    List<PropertyImage> findPrimaryImagesByPropertyId(@Param("propertyId") Long propertyId);
}
