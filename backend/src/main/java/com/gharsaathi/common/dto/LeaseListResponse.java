package com.gharsaathi.common.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for paginated lease list response
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeaseListResponse {
    
    private List<LeaseResponse> leases;
    private Long totalElements;
    private Integer totalPages;
    private Integer currentPage;
    private Integer pageSize;
    private Boolean hasNext;
    private Boolean hasPrevious;
}
