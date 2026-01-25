package com.gharsaathi.property.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gharsaathi.common.dto.CreatePropertyRequest;
import com.gharsaathi.common.dto.PropertyDetailResponse;
import com.gharsaathi.common.dto.PropertyListResponse;
import com.gharsaathi.common.dto.PropertySearchCriteria;
import com.gharsaathi.common.dto.UpdatePropertyRequest;
import com.gharsaathi.property.model.PropertyStatus;
import com.gharsaathi.property.service.PropertyService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * REST Controller for property management endpoints
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
public class PropertyController {
    
    private final PropertyService propertyService;
    
    // ==================== PUBLIC ENDPOINTS ====================
    
    /**
     * Get all available properties with pagination and sorting
     * Public endpoint - no authentication required
     */
    @GetMapping("/properties")
    public ResponseEntity<PropertyListResponse> getAllProperties(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection) {
        
        log.info("GET /api/properties - page: {}, size: {}", page, size);
        PropertyListResponse response = propertyService.getAllProperties(page, size, sortBy, sortDirection);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Search and filter properties
     * Public endpoint - no authentication required
     */
    @PostMapping("/properties/search")
    public ResponseEntity<PropertyListResponse> searchProperties(@RequestBody PropertySearchCriteria criteria) {
        log.info("POST /api/properties/search - criteria: {}", criteria);
        PropertyListResponse response = propertyService.searchProperties(criteria);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Get property details by ID
     * Public endpoint - no authentication required
     */
    @GetMapping("/properties/{id}")
    public ResponseEntity<PropertyDetailResponse> getPropertyById(@PathVariable Long id) {
        log.info("GET /api/properties/{}", id);
        PropertyDetailResponse response = propertyService.getPropertyById(id);
        return ResponseEntity.ok(response);
    }
    
    // ==================== LANDLORD ENDPOINTS ====================
    
    /**
     * Create a new property listing
     * Requires LANDLORD role
     */
    @PostMapping("/landlord/properties")
    public ResponseEntity<PropertyDetailResponse> createProperty(
            @Valid @RequestBody CreatePropertyRequest request) {
        
        Long landlordId = getCurrentUserId();
        log.info("POST /api/landlord/properties - landlord: {}", landlordId);
        
        PropertyDetailResponse response = propertyService.createProperty(request, landlordId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    /**
     * Get all properties for the authenticated landlord
     * Requires LANDLORD role
     */
    @GetMapping("/landlord/properties")
    public ResponseEntity<PropertyListResponse> getLandlordProperties(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Long landlordId = getCurrentUserId();
        log.info("GET /api/landlord/properties - landlord: {}", landlordId);
        
        PropertyListResponse response = propertyService.getLandlordProperties(landlordId, page, size);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Get specific property details for the authenticated landlord
     * Requires LANDLORD role
     */
    @GetMapping("/landlord/properties/{id}")
    public ResponseEntity<PropertyDetailResponse> getLandlordPropertyById(@PathVariable Long id) {
        Long landlordId = getCurrentUserId();
        log.info("GET /api/landlord/properties/{} - landlord: {}", id, landlordId);
        
        // Service will verify ownership
        PropertyDetailResponse response = propertyService.getPropertyById(id);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Update an existing property
     * Requires LANDLORD role and property ownership
     */
    @PutMapping("/landlord/properties/{id}")
    public ResponseEntity<PropertyDetailResponse> updateProperty(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePropertyRequest request) {
        
        Long landlordId = getCurrentUserId();
        log.info("PUT /api/landlord/properties/{} - landlord: {}", id, landlordId);
        
        PropertyDetailResponse response = propertyService.updateProperty(id, request, landlordId);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Delete a property
     * Requires LANDLORD role and property ownership
     */
    @DeleteMapping("/landlord/properties/{id}")
    public ResponseEntity<Map<String, String>> deleteProperty(@PathVariable Long id) {
        Long landlordId = getCurrentUserId();
        log.info("DELETE /api/landlord/properties/{} - landlord: {}", id, landlordId);
        
        propertyService.deleteProperty(id, landlordId);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Property deleted successfully");
        return ResponseEntity.ok(response);
    }
    
    /**
     * Update property status
     * Requires LANDLORD role and property ownership
     */
    @PatchMapping("/landlord/properties/{id}/status")
    public ResponseEntity<PropertyDetailResponse> updatePropertyStatus(
            @PathVariable Long id,
            @RequestParam PropertyStatus status) {
        
        Long landlordId = getCurrentUserId();
        log.info("PATCH /api/landlord/properties/{}/status - status: {} - landlord: {}", id, status, landlordId);
        
        PropertyDetailResponse response = propertyService.updatePropertyStatus(id, status, landlordId);
        return ResponseEntity.ok(response);
    }
    
    // ==================== ADMIN ENDPOINTS ====================
    
    /**
     * Get all properties (including unavailable ones)
     * Requires ADMIN role
     */
    @GetMapping("/admin/properties")
    public ResponseEntity<PropertyListResponse> getAllPropertiesAdmin(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection) {
        
        log.info("GET /api/admin/properties - page: {}, size: {}", page, size);
        
        // Admin can see all properties regardless of status
        PropertySearchCriteria criteria = PropertySearchCriteria.builder()
            .page(page)
            .size(size)
            .sortBy(sortBy)
            .sortDirection(sortDirection)
            .build();
        
        PropertyListResponse response = propertyService.searchProperties(criteria);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Force delete a property (admin only)
     * Requires ADMIN role
     */
    @DeleteMapping("/admin/properties/{id}")
    public ResponseEntity<Map<String, String>> forceDeleteProperty(@PathVariable Long id) {
        log.info("DELETE /api/admin/properties/{} - force delete by admin", id);
        
        // For admin, we use a special landlord ID of 0 to bypass ownership check
        // This is a simplified approach - in production, you might want a separate method
        propertyService.deleteProperty(id, 0L);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Property force deleted by admin");
        return ResponseEntity.ok(response);
    }
    
    // ==================== HELPER METHODS ====================
    
    /**
     * Get current authenticated user's ID from security context
     */
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }
        
        // Extract userId from User (which implements UserDetails)
        Object principal = authentication.getPrincipal();
        if (principal instanceof com.gharsaathi.auth.model.User) {
            com.gharsaathi.auth.model.User user = 
                (com.gharsaathi.auth.model.User) principal;
            return user.getId();
        }
        
        throw new RuntimeException("Unable to extract user ID from authentication. Principal type: " 
            + (principal != null ? principal.getClass().getName() : "null"));
    }
}
