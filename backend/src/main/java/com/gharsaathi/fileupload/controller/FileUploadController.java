package com.gharsaathi.fileupload.controller;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gharsaathi.auth.model.User;
import com.gharsaathi.fileupload.dto.FileUploadResponse;
import com.gharsaathi.fileupload.model.FileCategory;
import com.gharsaathi.fileupload.service.FileStorageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * REST Controller for file upload management
 * Provides endpoints for uploading, downloading, and managing files
 */
@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
@Slf4j
public class FileUploadController {

    private final FileStorageService fileStorageService;

    /**
     * Upload a file
     * Accessible by: ALL authenticated users
     */
    @PostMapping("/upload")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<FileUploadResponse> uploadFile(
            @AuthenticationPrincipal User currentUser,
            @RequestParam("file") MultipartFile file,
            @RequestParam("category") FileCategory category) {
        log.info("User {} uploading file: {}", currentUser.getEmail(), file.getOriginalFilename());
        
        FileUploadResponse response = fileStorageService.uploadFile(file, currentUser.getId(), category);
        return ResponseEntity.ok(response);
    }

    /**
     * Download a file
     * Accessible by: ALL authenticated users
     */
    @GetMapping("/{fileId}/download")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Resource> downloadFile(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long fileId) {
        log.info("User {} downloading file {}", currentUser.getEmail(), fileId);
        
        Resource resource = fileStorageService.downloadFile(fileId);
        
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, 
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    /**
     * Delete a file
     * Accessible by: File owner only
     */
    @DeleteMapping("/{fileId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> deleteFile(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long fileId) {
        log.info("User {} deleting file {}", currentUser.getEmail(), fileId);
        
        fileStorageService.deleteFile(fileId, currentUser.getId());
        return ResponseEntity.ok("File deleted successfully");
    }

    /**
     * Get file details
     * Accessible by: ALL authenticated users
     */
    @GetMapping("/{fileId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<FileUploadResponse> getFileDetails(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long fileId) {
        log.info("User {} fetching file details for {}", currentUser.getEmail(), fileId);
        
        FileUploadResponse response = fileStorageService.getFileDetails(fileId);
        return ResponseEntity.ok(response);
    }

    /**
     * Get current user's uploaded files
     * Accessible by: ALL authenticated users
     */
    @GetMapping("/my-files")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<FileUploadResponse>> getMyFiles(
            @AuthenticationPrincipal User currentUser,
            @RequestParam(required = false) FileCategory category) {
        log.info("User {} fetching their files with category: {}", currentUser.getEmail(), category);
        
        List<FileUploadResponse> files = fileStorageService.getUserFiles(currentUser.getId(), category);
        return ResponseEntity.ok(files);
    }
}
