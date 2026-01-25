package com.gharsaathi.property.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gharsaathi.auth.model.User;
import com.gharsaathi.auth.repository.UserRepository;
import com.gharsaathi.common.dto.CreatePropertyRequest;
import com.gharsaathi.common.dto.PropertyDetailResponse;
import com.gharsaathi.common.dto.PropertyListResponse;
import com.gharsaathi.common.dto.PropertyResponse;
import com.gharsaathi.common.dto.PropertySearchCriteria;
import com.gharsaathi.common.dto.UpdatePropertyRequest;
import com.gharsaathi.common.exception.PropertyAccessDeniedException;
import com.gharsaathi.common.exception.PropertyAlreadyRentedException;
import com.gharsaathi.common.exception.PropertyNotFoundException;
import com.gharsaathi.common.exception.ResourceNotFoundException;
import com.gharsaathi.property.model.Property;
import com.gharsaathi.property.model.PropertyImage;
import com.gharsaathi.property.model.PropertyStatus;
import com.gharsaathi.property.repository.PropertyImageRepository;
import com.gharsaathi.property.repository.PropertyRepository;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service class for managing property operations
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PropertyService {
    
    private final PropertyRepository propertyRepository;
    private final PropertyImageRepository propertyImageRepository;
    private final UserRepository userRepository;
    
    /**
     * Create a new property listing
     */
    @Transactional
    public PropertyDetailResponse createProperty(CreatePropertyRequest request, Long landlordId) {
        log.info("Creating new property for landlord: {}", landlordId);
        
        // Verify landlord exists
        User landlord = userRepository.findById(landlordId)
            .orElseThrow(() -> new ResourceNotFoundException("Landlord not found with id: " + landlordId));
        
        // Build property entity
        Property property = Property.builder()
            .title(request.getTitle())
            .description(request.getDescription())
            .propertyType(request.getPropertyType())
            .status(PropertyStatus.AVAILABLE)
            .address(request.getAddress())
            .city(request.getCity())
            .area(request.getArea())
            .latitude(request.getLatitude())
            .longitude(request.getLongitude())
            .price(request.getPrice())
            .securityDeposit(request.getSecurityDeposit())
            .bedrooms(request.getBedrooms())
            .bathrooms(request.getBathrooms())
            .propertyArea(request.getPropertyArea())
            .furnished(request.getFurnished() != null ? request.getFurnished() : false)
            .parkingAvailable(request.getParkingAvailable() != null ? request.getParkingAvailable() : false)
            .petsAllowed(request.getPetsAllowed() != null ? request.getPetsAllowed() : false)
            .amenities(request.getAmenities() != null ? request.getAmenities() : new HashSet<>())
            .availableFrom(request.getAvailableFrom())
            .landlord(landlord)
            .build();
        
        // Save property
        Property savedProperty = propertyRepository.save(property);
        log.info("Property created successfully with id: {}", savedProperty.getId());
        
        return mapToDetailResponse(savedProperty);
    }
    
    /**
     * Update an existing property
     */
    @Transactional
    public PropertyDetailResponse updateProperty(Long propertyId, UpdatePropertyRequest request, Long landlordId) {
        log.info("Updating property: {} by landlord: {}", propertyId, landlordId);
        
        // Verify ownership
        Property property = getPropertyAndVerifyOwnership(propertyId, landlordId);
        
        // Update fields if provided
        if (request.getTitle() != null) {
            property.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            property.setDescription(request.getDescription());
        }
        if (request.getPropertyType() != null) {
            property.setPropertyType(request.getPropertyType());
        }
        if (request.getStatus() != null) {
            property.setStatus(request.getStatus());
        }
        if (request.getAddress() != null) {
            property.setAddress(request.getAddress());
        }
        if (request.getCity() != null) {
            property.setCity(request.getCity());
        }
        if (request.getArea() != null) {
            property.setArea(request.getArea());
        }
        if (request.getLatitude() != null) {
            property.setLatitude(request.getLatitude());
        }
        if (request.getLongitude() != null) {
            property.setLongitude(request.getLongitude());
        }
        if (request.getPrice() != null) {
            property.setPrice(request.getPrice());
        }
        if (request.getSecurityDeposit() != null) {
            property.setSecurityDeposit(request.getSecurityDeposit());
        }
        if (request.getBedrooms() != null) {
            property.setBedrooms(request.getBedrooms());
        }
        if (request.getBathrooms() != null) {
            property.setBathrooms(request.getBathrooms());
        }
        if (request.getPropertyArea() != null) {
            property.setPropertyArea(request.getPropertyArea());
        }
        if (request.getFurnished() != null) {
            property.setFurnished(request.getFurnished());
        }
        if (request.getParkingAvailable() != null) {
            property.setParkingAvailable(request.getParkingAvailable());
        }
        if (request.getPetsAllowed() != null) {
            property.setPetsAllowed(request.getPetsAllowed());
        }
        if (request.getAmenities() != null) {
            property.setAmenities(request.getAmenities());
        }
        if (request.getAvailableFrom() != null) {
            property.setAvailableFrom(request.getAvailableFrom());
        }
        
        Property updatedProperty = propertyRepository.save(property);
        log.info("Property updated successfully: {}", propertyId);
        
        return mapToDetailResponse(updatedProperty);
    }
    
    /**
     * Delete a property (soft delete by changing status)
     */
    @Transactional
    public void deleteProperty(Long propertyId, Long landlordId) {
        log.info("Deleting property: {} by landlord: {}", propertyId, landlordId);
        
        // Verify ownership
        Property property = getPropertyAndVerifyOwnership(propertyId, landlordId);
        
        // Check if property is rented
        if (property.getStatus() == PropertyStatus.RENTED) {
            throw new PropertyAlreadyRentedException(propertyId);
        }
        
        // Soft delete - set status to UNAVAILABLE
        property.setStatus(PropertyStatus.UNAVAILABLE);
        propertyRepository.save(property);
        
        log.info("Property deleted successfully: {}", propertyId);
    }
    
    /**
     * Get property details by ID
     */
    @Transactional(readOnly = true)
    public PropertyDetailResponse getPropertyById(Long propertyId) {
        log.info("Fetching property details: {}", propertyId);
        
        Property property = propertyRepository.findByIdWithAllRelations(propertyId)
            .orElseThrow(() -> new PropertyNotFoundException(propertyId));
        
        return mapToDetailResponse(property);
    }
    
    /**
     * Get all properties with pagination
     */
    @Transactional(readOnly = true)
    public PropertyListResponse getAllProperties(int page, int size, String sortBy, String sortDirection) {
        log.info("Fetching all available properties - page: {}, size: {}", page, size);
        
        Sort sort = sortDirection.equalsIgnoreCase("ASC") 
            ? Sort.by(sortBy).ascending() 
            : Sort.by(sortBy).descending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Property> propertyPage = propertyRepository.findByStatus(PropertyStatus.AVAILABLE, pageable);
        
        return mapToListResponse(propertyPage);
    }
    
    /**
     * Get properties by landlord
     */
    @Transactional(readOnly = true)
    public PropertyListResponse getLandlordProperties(Long landlordId, int page, int size) {
        log.info("Fetching properties for landlord: {}", landlordId);
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Property> propertyPage = propertyRepository.findByLandlordId(landlordId, pageable);
        
        return mapToListResponse(propertyPage);
    }
    
    /**
     * Search properties with filters
     */
    @Transactional(readOnly = true)
    public PropertyListResponse searchProperties(PropertySearchCriteria criteria) {
        log.info("Searching properties with criteria: {}", criteria);
        
        Specification<Property> spec = buildPropertySpecification(criteria);
        
        Sort sort = criteria.getSortDirection().equalsIgnoreCase("ASC")
            ? Sort.by(criteria.getSortBy()).ascending()
            : Sort.by(criteria.getSortBy()).descending();
        
        Pageable pageable = PageRequest.of(criteria.getPage(), criteria.getSize(), sort);
        Page<Property> propertyPage = propertyRepository.findAll(spec, pageable);
        
        return mapToListResponse(propertyPage);
    }
    
    /**
     * Update property status
     */
    @Transactional
    public PropertyDetailResponse updatePropertyStatus(Long propertyId, PropertyStatus status, Long landlordId) {
        log.info("Updating property status: {} to {}", propertyId, status);
        
        Property property = getPropertyAndVerifyOwnership(propertyId, landlordId);
        property.setStatus(status);
        Property updatedProperty = propertyRepository.save(property);
        
        return mapToDetailResponse(updatedProperty);
    }
    
    // Helper Methods
    
    /**
     * Get property and verify ownership
     */
    private Property getPropertyAndVerifyOwnership(Long propertyId, Long landlordId) {
        Property property = propertyRepository.findByIdWithAllRelations(propertyId)
            .orElseThrow(() -> new PropertyNotFoundException(propertyId));
        
        if (!property.getLandlord().getId().equals(landlordId)) {
            throw new PropertyAccessDeniedException(propertyId, landlordId);
        }
        
        return property;
    }
    
    /**
     * Build dynamic specification for property search
     */
    private Specification<Property> buildPropertySpecification(PropertySearchCriteria criteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            // Default: only show available properties unless landlordId is specified
            if (criteria.getLandlordId() == null) {
                if (criteria.getStatus() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("status"), criteria.getStatus()));
                } else {
                    predicates.add(criteriaBuilder.equal(root.get("status"), PropertyStatus.AVAILABLE));
                }
            }
            
            // Landlord filter
            if (criteria.getLandlordId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("landlord").get("id"), criteria.getLandlordId()));
            }
            
            // City filter
            if (criteria.getCity() != null && !criteria.getCity().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("city")), 
                    "%" + criteria.getCity().toLowerCase() + "%"
                ));
            }
            
            // Area filter
            if (criteria.getArea() != null && !criteria.getArea().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("area")), 
                    "%" + criteria.getArea().toLowerCase() + "%"
                ));
            }
            
            // Property type filter
            if (criteria.getPropertyType() != null) {
                predicates.add(criteriaBuilder.equal(root.get("propertyType"), criteria.getPropertyType()));
            }
            
            // Price range
            if (criteria.getMinPrice() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), criteria.getMinPrice()));
            }
            if (criteria.getMaxPrice() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), criteria.getMaxPrice()));
            }
            
            // Bedroom range
            if (criteria.getMinBedrooms() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("bedrooms"), criteria.getMinBedrooms()));
            }
            if (criteria.getMaxBedrooms() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("bedrooms"), criteria.getMaxBedrooms()));
            }
            
            // Bathroom range
            if (criteria.getMinBathrooms() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("bathrooms"), criteria.getMinBathrooms()));
            }
            if (criteria.getMaxBathrooms() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("bathrooms"), criteria.getMaxBathrooms()));
            }
            
            // Property area range
            if (criteria.getMinPropertyArea() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("propertyArea"), criteria.getMinPropertyArea()));
            }
            if (criteria.getMaxPropertyArea() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("propertyArea"), criteria.getMaxPropertyArea()));
            }
            
            // Boolean filters
            if (criteria.getFurnished() != null) {
                predicates.add(criteriaBuilder.equal(root.get("furnished"), criteria.getFurnished()));
            }
            if (criteria.getParkingAvailable() != null) {
                predicates.add(criteriaBuilder.equal(root.get("parkingAvailable"), criteria.getParkingAvailable()));
            }
            if (criteria.getPetsAllowed() != null) {
                predicates.add(criteriaBuilder.equal(root.get("petsAllowed"), criteria.getPetsAllowed()));
            }
            
            // Keyword search (in title and description)
            if (criteria.getKeyword() != null && !criteria.getKeyword().isEmpty()) {
                String keyword = "%" + criteria.getKeyword().toLowerCase() + "%";
                Predicate titleMatch = criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), keyword);
                Predicate descMatch = criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), keyword);
                predicates.add(criteriaBuilder.or(titleMatch, descMatch));
            }
            
            // Available from date
            if (criteria.getAvailableFrom() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("availableFrom"), criteria.getAvailableFrom()));
            }
            
            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        };
    }
    
    /**
     * Map Property entity to PropertyDetailResponse DTO
     */
    private PropertyDetailResponse mapToDetailResponse(Property property) {
        List<PropertyDetailResponse.PropertyImageResponse> images = property.getImages().stream()
            .map(img -> PropertyDetailResponse.PropertyImageResponse.builder()
                .id(img.getId())
                .imageUrl(img.getImageUrl())
                .isPrimary(img.getIsPrimary())
                .displayOrder(img.getDisplayOrder())
                .build())
            .collect(Collectors.toList());
        
        PropertyDetailResponse.LandlordInfo landlordInfo = PropertyDetailResponse.LandlordInfo.builder()
            .id(property.getLandlord().getId())
            .fullName(property.getLandlord().getFullName())
            .email(property.getLandlord().getEmail())
            .phoneNumber(property.getLandlord().getPhoneNumber())
            .build();
        
        return PropertyDetailResponse.builder()
            .id(property.getId())
            .title(property.getTitle())
            .description(property.getDescription())
            .propertyType(property.getPropertyType())
            .status(property.getStatus())
            .address(property.getAddress())
            .city(property.getCity())
            .area(property.getArea())
            .latitude(property.getLatitude())
            .longitude(property.getLongitude())
            .price(property.getPrice())
            .securityDeposit(property.getSecurityDeposit())
            .bedrooms(property.getBedrooms())
            .bathrooms(property.getBathrooms())
            .propertyArea(property.getPropertyArea())
            .furnished(property.getFurnished())
            .parkingAvailable(property.getParkingAvailable())
            .petsAllowed(property.getPetsAllowed())
            .amenities(property.getAmenities())
            .images(images)
            .landlord(landlordInfo)
            .availableFrom(property.getAvailableFrom())
            .createdAt(property.getCreatedAt())
            .updatedAt(property.getUpdatedAt())
            .build();
    }
    
    /**
     * Map Property entity to PropertyResponse DTO
     */
    private PropertyResponse mapToResponse(Property property) {
        String primaryImageUrl = property.getImages().stream()
            .filter(PropertyImage::getIsPrimary)
            .findFirst()
            .map(PropertyImage::getImageUrl)
            .orElse(property.getImages().isEmpty() ? null : property.getImages().get(0).getImageUrl());
        
        return PropertyResponse.builder()
            .id(property.getId())
            .title(property.getTitle())
            .propertyType(property.getPropertyType())
            .status(property.getStatus())
            .city(property.getCity())
            .area(property.getArea())
            .price(property.getPrice())
            .bedrooms(property.getBedrooms())
            .bathrooms(property.getBathrooms())
            .propertyArea(property.getPropertyArea())
            .furnished(property.getFurnished())
            .primaryImageUrl(primaryImageUrl)
            .landlordId(property.getLandlord().getId())
            .landlordName(property.getLandlord().getFullName())
            .landlordEmail(property.getLandlord().getEmail())
            .build();
    }
    
    /**
     * Map Page<Property> to PropertyListResponse
     */
    private PropertyListResponse mapToListResponse(Page<Property> propertyPage) {
        List<PropertyResponse> properties = propertyPage.getContent().stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
        
        return PropertyListResponse.builder()
            .properties(properties)
            .totalElements(propertyPage.getTotalElements())
            .totalPages(propertyPage.getTotalPages())
            .currentPage(propertyPage.getNumber())
            .pageSize(propertyPage.getSize())
            .hasNext(propertyPage.hasNext())
            .hasPrevious(propertyPage.hasPrevious())
            .build();
    }
}
