package com.gharsaathi.property.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.gharsaathi.auth.model.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing a property listing in the system
 */
@Entity
@Table(name = "properties", indexes = {
    @Index(name = "idx_city", columnList = "city"),
    @Index(name = "idx_status", columnList = "status"),
    @Index(name = "idx_landlord", columnList = "landlord_id"),
    @Index(name = "idx_price", columnList = "price"),
    @Index(name = "idx_property_type", columnList = "property_type")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Property {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 255)
    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "property_type", nullable = false, length = 50)
    private PropertyType propertyType;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    @Builder.Default
    private PropertyStatus status = PropertyStatus.AVAILABLE;
    
    // Location Details
    @Column(nullable = false, length = 500)
    private String address;
    
    @Column(nullable = false, length = 100)
    private String city;
    
    @Column(length = 100)
    private String area;
    
    @Column
    private Double latitude;
    
    @Column
    private Double longitude;
    
    // Pricing
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    
    @Column(name = "security_deposit", precision = 10, scale = 2)
    private BigDecimal securityDeposit;
    
    // Property Specifications
    @Column
    private Integer bedrooms;
    
    @Column
    private Integer bathrooms;
    
    @Column(name = "property_area")
    private Double propertyArea; // in square feet
    
    @Column
    @Builder.Default
    private Boolean furnished = false;
    
    @Column(name = "parking_available")
    @Builder.Default
    private Boolean parkingAvailable = false;
    
    @Column(name = "pets_allowed")
    @Builder.Default
    private Boolean petsAllowed = false;
    
    // Amenities stored as JSON array
    @ElementCollection
    @CollectionTable(name = "property_amenities", joinColumns = @JoinColumn(name = "property_id"))
    @Column(name = "amenity")
    @Builder.Default
    private Set<String> amenities = new HashSet<>();
    
    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "landlord_id", nullable = false)
    private User landlord;
    
    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("displayOrder ASC")
    @Builder.Default
    private List<PropertyImage> images = new ArrayList<>();
    
    // Availability
    @Column(name = "available_from")
    private LocalDate availableFrom;
    
    // Audit Fields
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Helper Methods
    
    /**
     * Add an image to the property
     */
    public void addImage(PropertyImage image) {
        images.add(image);
        image.setProperty(this);
    }
    
    /**
     * Remove an image from the property
     */
    public void removeImage(PropertyImage image) {
        images.remove(image);
        image.setProperty(null);
    }
    
    /**
     * Add an amenity to the property
     */
    public void addAmenity(String amenity) {
        amenities.add(amenity);
    }
    
    /**
     * Remove an amenity from the property
     */
    public void removeAmenity(String amenity) {
        amenities.remove(amenity);
    }
    
    /**
     * Check if property is available for rent
     */
    public boolean isAvailable() {
        return this.status == PropertyStatus.AVAILABLE;
    }
    
    /**
     * Check if property is currently rented
     */
    public boolean isRented() {
        return this.status == PropertyStatus.RENTED;
    }
}
