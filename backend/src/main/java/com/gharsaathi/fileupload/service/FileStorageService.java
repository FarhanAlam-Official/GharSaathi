package com.gharsaathi.fileupload.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.gharsaathi.auth.model.User;
import com.gharsaathi.auth.repository.UserRepository;
import com.gharsaathi.fileupload.dto.FileUploadResponse;
import com.gharsaathi.fileupload.model.FileCategory;
import com.gharsaathi.fileupload.model.FileUpload;
import com.gharsaathi.fileupload.repository.FileUploadRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service for file storage and management
 * Handles file uploads, downloads, and deletions
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FileStorageService {

    private final FileUploadRepository fileUploadRepository;
    private final UserRepository userRepository;

    @Value("${file.upload.dir:./uploads}")
    private String uploadDir;

    private Path fileStorageLocation;

    @PostConstruct
    public void init() {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
            log.info("File storage directory created at: {}", this.fileStorageLocation);
        } catch (IOException ex) {
            throw new RuntimeException("Could not create upload directory: " + ex.getMessage());
        }
    }

    /**
     * Upload a file
     */
    @Transactional
    public FileUploadResponse uploadFile(MultipartFile file, Long userId, FileCategory category) {
        log.info("User {} uploading file: {} ({})", userId, file.getOriginalFilename(), category);

        // Validate file
        validateFile(file);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // Generate unique filename
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = getFileExtension(originalFileName);
        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

        try {
            // Create category subdirectory
            Path categoryPath = this.fileStorageLocation.resolve(category.name().toLowerCase());
            Files.createDirectories(categoryPath);

            // Store file
            Path targetLocation = categoryPath.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // Save metadata to database
            FileUpload fileUpload = FileUpload.builder()
                    .fileName(originalFileName)
                    .filePath(category.name().toLowerCase() + "/" + uniqueFileName)
                    .fileSize(file.getSize())
                    .contentType(file.getContentType())
                    .uploadedBy(user)
                    .fileCategory(category)
                    .build();

            FileUpload savedFile = fileUploadRepository.save(fileUpload);
            log.info("File uploaded successfully: {}", savedFile.getId());

            return mapToResponse(savedFile);

        } catch (IOException ex) {
            log.error("Failed to upload file: {}", ex.getMessage());
            throw new RuntimeException("Failed to store file: " + ex.getMessage());
        }
    }

    /**
     * Download a file
     */
    @Transactional(readOnly = true)
    public Resource downloadFile(Long fileId) {
        log.info("Downloading file with ID: {}", fileId);

        FileUpload fileUpload = fileUploadRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found with id: " + fileId));

        try {
            Path filePath = this.fileStorageLocation.resolve(fileUpload.getFilePath()).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("File not found or not readable: " + fileUpload.getFileName());
            }
        } catch (Exception ex) {
            log.error("Failed to download file: {}", ex.getMessage());
            throw new RuntimeException("Failed to download file: " + ex.getMessage());
        }
    }

    /**
     * Delete a file
     */
    @Transactional
    public void deleteFile(Long fileId, Long userId) {
        log.info("User {} deleting file {}", userId, fileId);

        FileUpload fileUpload = fileUploadRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found with id: " + fileId));

        // Verify ownership
        if (!fileUpload.getUploadedBy().getId().equals(userId)) {
            throw new RuntimeException("You do not have permission to delete this file");
        }

        try {
            // Delete physical file
            Path filePath = this.fileStorageLocation.resolve(fileUpload.getFilePath()).normalize();
            Files.deleteIfExists(filePath);

            // Delete database record
            fileUploadRepository.delete(fileUpload);
            log.info("File deleted successfully: {}", fileId);

        } catch (IOException ex) {
            log.error("Failed to delete file: {}", ex.getMessage());
            throw new RuntimeException("Failed to delete file: " + ex.getMessage());
        }
    }

    /**
     * Get file URL
     */
    @Transactional(readOnly = true)
    public String getFileUrl(Long fileId) {
        FileUpload fileUpload = fileUploadRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found with id: " + fileId));

        return "/api/files/" + fileId + "/download";
    }

    /**
     * Get user's files
     */
    @Transactional(readOnly = true)
    public List<FileUploadResponse> getUserFiles(Long userId, FileCategory category) {
        log.info("Fetching files for user {} with category: {}", userId, category);

        List<FileUpload> files = category != null
                ? fileUploadRepository.findByUploadedByIdAndFileCategory(userId, category)
                : fileUploadRepository.findByUploadedById(userId);

        return files.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get file details
     */
    @Transactional(readOnly = true)
    public FileUploadResponse getFileDetails(Long fileId) {
        log.info("Fetching file details for ID: {}", fileId);

        FileUpload fileUpload = fileUploadRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found with id: " + fileId));

        return mapToResponse(fileUpload);
    }

    /**
     * Validate file
     */
    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("Cannot upload empty file");
        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (fileName.contains("..")) {
            throw new RuntimeException("Invalid file name: " + fileName);
        }

        // Validate content type (images only for now)
        String contentType = file.getContentType();
        if (contentType == null || 
            (!contentType.startsWith("image/") && !contentType.equals("application/pdf"))) {
            throw new RuntimeException("Only image and PDF files are allowed");
        }
    }

    /**
     * Get file extension
     */
    private String getFileExtension(String fileName) {
        int lastDot = fileName.lastIndexOf('.');
        return (lastDot == -1) ? "" : fileName.substring(lastDot);
    }

    /**
     * Map FileUpload to FileUploadResponse
     */
    private FileUploadResponse mapToResponse(FileUpload fileUpload) {
        return FileUploadResponse.builder()
                .id(fileUpload.getId())
                .fileName(fileUpload.getFileName())
                .fileUrl("/api/files/" + fileUpload.getId() + "/download")
                .fileSize(fileUpload.getFileSize())
                .contentType(fileUpload.getContentType())
                .fileCategory(fileUpload.getFileCategory())
                .uploadDate(fileUpload.getUploadDate())
                .uploadedBy(fileUpload.getUploadedBy().getId())
                .build();
    }
}
