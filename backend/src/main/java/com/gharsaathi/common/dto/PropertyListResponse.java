package com.gharsaathi.common.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for paginated property list response
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyListResponse {
    
    private List<PropertyResponse> properties;
    private long totalElements;
    private int totalPages;
    private int currentPage;
    private int pageSize;
    private boolean hasNext;
    private boolean hasPrevious;
}
