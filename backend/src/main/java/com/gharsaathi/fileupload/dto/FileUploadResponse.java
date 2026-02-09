package com.gharsaathi.fileupload.dto;

import java.time.LocalDateTime;

import com.gharsaathi.fileupload.model.FileCategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for file upload response
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadResponse {
    
    private Long id;
    private String fileName;
    private String fileUrl;
    private Long fileSize;
    private String contentType;
    private FileCategory fileCategory;
    private LocalDateTime uploadDate;
    private Long uploadedBy;
}
