package com.gharsaathi.payment.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for list of payments with pagination info
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentListResponse {
    
    private List<PaymentResponse> payments;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean first;
    private boolean last;
}
