package com.gharsaathi.property.review.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for property review responses
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyReviewDTO {
    private Long id;
    private Long propertyId;
    private String propertyTitle;
    private Long tenantId;
    private String tenantName;
    private Long leaseId;
    private Integer rating;
    private String comment;
    private Boolean isVerified;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
