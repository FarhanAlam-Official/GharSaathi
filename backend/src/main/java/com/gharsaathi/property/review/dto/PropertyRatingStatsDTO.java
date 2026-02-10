package com.gharsaathi.property.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for property rating statistics
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyRatingStatsDTO {
    private Long propertyId;
    private Double averageRating;
    private Long totalReviews;
    private Long verifiedReviews;
    private Long fiveStarCount;
    private Long fourStarCount;
    private Long threeStarCount;
    private Long twoStarCount;
    private Long oneStarCount;
}
