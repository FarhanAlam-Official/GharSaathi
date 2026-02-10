package com.gharsaathi.property.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gharsaathi.property.model.Property;
import com.gharsaathi.property.model.PropertyStatus;
import com.gharsaathi.property.model.PropertyType;

/**
 * Repository interface for Property entity
 * Extends JpaSpecificationExecutor for dynamic query building
 */
@Repository
public interface PropertyRepository extends JpaRepository<Property, Long>, JpaSpecificationExecutor<Property> {
    
    /**
     * Find all properties by landlord ID
     */
    Page<Property> findByLandlordId(Long landlordId, Pageable pageable);
    
    /**
     * Find all properties by status
     */
    Page<Property> findByStatus(PropertyStatus status, Pageable pageable);
    
    /**
     * Find all properties in a specific city
     */
    Page<Property> findByCity(String city, Pageable pageable);
    
    /**
     * Find all properties by city and status
     */
    Page<Property> findByCityAndStatus(String city, PropertyStatus status, Pageable pageable);
    
    /**
     * Find all properties by property type
     */
    Page<Property> findByPropertyType(PropertyType propertyType, Pageable pageable);
    
    /**
     * Find property by ID with landlord loaded
     */
    @Query("SELECT p FROM Property p LEFT JOIN FETCH p.landlord WHERE p.id = :id")
    Optional<Property> findByIdWithLandlord(@Param("id") Long id);
    
    /**
     * Find property by ID with images loaded
     */
    @Query("SELECT p FROM Property p LEFT JOIN FETCH p.images WHERE p.id = :id")
    Optional<Property> findByIdWithImages(@Param("id") Long id);
    
    /**
     * Find property by ID with all relationships loaded
     */
    @Query("SELECT DISTINCT p FROM Property p " +
           "LEFT JOIN FETCH p.landlord " +
           "LEFT JOIN FETCH p.images " +
           "WHERE p.id = :id")
    Optional<Property> findByIdWithAllRelations(@Param("id") Long id);
    
    /**
     * Check if a property exists with given ID and landlord ID (for ownership verification)
     */
    boolean existsByIdAndLandlordId(Long id, Long landlordId);
    
    /**
     * Find all properties by landlord ID and status
     */
    Page<Property> findByLandlordIdAndStatus(Long landlordId, PropertyStatus status, Pageable pageable);
    
    /**
     * Count properties by landlord ID
     */
    long countByLandlordId(Long landlordId);
    
    /**
     * Count properties by status
     */
    long countByStatus(PropertyStatus status);
    
    /**
     * Count properties by landlord ID and status
     */
    long countByLandlordIdAndStatus(Long landlordId, PropertyStatus status);
    
    /**
     * Search properties by keyword in title or description
     */
    @Query("SELECT p FROM Property p WHERE " +
           "LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Property> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
    
    /**
     * Find available properties in a city
     */
    @Query("SELECT p FROM Property p WHERE p.city = :city AND p.status = 'AVAILABLE'")
    Page<Property> findAvailablePropertiesInCity(@Param("city") String city, Pageable pageable);
}
