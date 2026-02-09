package com.gharsaathi.fileupload.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gharsaathi.fileupload.model.FileCategory;
import com.gharsaathi.fileupload.model.FileUpload;

@Repository
public interface FileUploadRepository extends JpaRepository<FileUpload, Long> {
    
    List<FileUpload> findByUploadedById(Long userId);
    List<FileUpload> findByFileCategory(FileCategory category);
    List<FileUpload> findByUploadedByIdAndFileCategory(Long userId, FileCategory category);
}
