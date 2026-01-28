package com.gharsaathi.rental.application.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for paginated list of rental applications
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationListResponse {

    private List<ApplicationResponse> applications;
    private long totalElements;
    private int totalPages;
    private int currentPage;
    private int pageSize;
    private boolean hasNext;
    private boolean hasPrevious;
}
