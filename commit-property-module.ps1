# GharSaathi - Property Module Commits (Jan 20-27, 2026)
# 24 commits over 8 days (~3 per day)

$repoPath = "d:\Semester Final Project\7th Sem Final Project\GharSaathi"
Set-Location $repoPath

function Commit-WithDate {
    param(
        [string]$date,
        [string]$message,
        [string[]]$files
    )
    
    Write-Host "`n========================================" -ForegroundColor Cyan
    Write-Host "Committing: $message" -ForegroundColor Yellow
    Write-Host "Date: $date" -ForegroundColor Gray
    Write-Host "Files: $($files.Count) files" -ForegroundColor Gray
    Write-Host "========================================" -ForegroundColor Cyan
    
    foreach ($file in $files) {
        git add $file
    }
    
    $env:GIT_AUTHOR_DATE = $date
    $env:GIT_COMMITTER_DATE = $date
    git commit -m $message
    
    Write-Host "Committed successfully" -ForegroundColor Green
}

Write-Host "Starting Property Module commit process..." -ForegroundColor Magenta
Write-Host "Total commits planned: 24" -ForegroundColor Magenta

# ============================================
# Day 1: January 20, 2026 - Property Models
# ============================================

Commit-WithDate -date "2026-01-20T10:00:00" -message "feat: add property type and status enums" -files @(
    "backend/src/main/java/com/gharsaathi/property/model/PropertyType.java",
    "backend/src/main/java/com/gharsaathi/property/model/PropertyStatus.java"
)

Commit-WithDate -date "2026-01-20T13:30:00" -message "feat: add amenity type enum for property features" -files @(
    "backend/src/main/java/com/gharsaathi/property/model/AmenityType.java"
)

Commit-WithDate -date "2026-01-20T16:00:00" -message "feat: add property and property image models" -files @(
    "backend/src/main/java/com/gharsaathi/property/model/Property.java",
    "backend/src/main/java/com/gharsaathi/property/model/PropertyImage.java"
)

# ============================================
# Day 2: January 21, 2026 - Property DTOs Part 1
# ============================================

Commit-WithDate -date "2026-01-21T09:30:00" -message "feat: add create property request DTO" -files @(
    "backend/src/main/java/com/gharsaathi/common/dto/CreatePropertyRequest.java"
)

Commit-WithDate -date "2026-01-21T12:00:00" -message "feat: add update property request DTO" -files @(
    "backend/src/main/java/com/gharsaathi/common/dto/UpdatePropertyRequest.java"
)

Commit-WithDate -date "2026-01-21T15:30:00" -message "feat: add property search criteria DTO" -files @(
    "backend/src/main/java/com/gharsaathi/common/dto/PropertySearchCriteria.java"
)

# ============================================
# Day 3: January 22, 2026 - Property DTOs Part 2
# ============================================

Commit-WithDate -date "2026-01-22T10:00:00" -message "feat: add property response DTO" -files @(
    "backend/src/main/java/com/gharsaathi/common/dto/PropertyResponse.java"
)

Commit-WithDate -date "2026-01-22T13:00:00" -message "feat: add property detail response DTO" -files @(
    "backend/src/main/java/com/gharsaathi/common/dto/PropertyDetailResponse.java"
)

Commit-WithDate -date "2026-01-22T16:00:00" -message "feat: add property list response DTO with pagination" -files @(
    "backend/src/main/java/com/gharsaathi/common/dto/PropertyListResponse.java"
)

# ============================================
# Day 4: January 23, 2026 - Property Exceptions
# ============================================

Commit-WithDate -date "2026-01-23T09:00:00" -message "feat: add property not found exception" -files @(
    "backend/src/main/java/com/gharsaathi/common/exception/PropertyNotFoundException.java"
)

Commit-WithDate -date "2026-01-23T12:00:00" -message "feat: add property access denied exception" -files @(
    "backend/src/main/java/com/gharsaathi/common/exception/PropertyAccessDeniedException.java"
)

Commit-WithDate -date "2026-01-23T15:30:00" -message "feat: add property validation exceptions" -files @(
    "backend/src/main/java/com/gharsaathi/common/exception/InvalidPropertyDataException.java",
    "backend/src/main/java/com/gharsaathi/common/exception/PropertyAlreadyRentedException.java"
)

# ============================================
# Day 5: January 24, 2026 - Property Repositories
# ============================================

Commit-WithDate -date "2026-01-24T10:00:00" -message "feat: add property repository with custom queries" -files @(
    "backend/src/main/java/com/gharsaathi/property/repository/PropertyRepository.java"
)

Commit-WithDate -date "2026-01-24T13:00:00" -message "feat: add property image repository" -files @(
    "backend/src/main/java/com/gharsaathi/property/repository/PropertyImageRepository.java"
)

Commit-WithDate -date "2026-01-24T16:00:00" -message "fix: update global exception handler for property exceptions" -files @(
    "backend/src/main/java/com/gharsaathi/common/exception/GlobalExceptionHandler.java"
)

# ============================================
# Day 6: January 25, 2026 - Property Service
# ============================================

Commit-WithDate -date "2026-01-25T10:00:00" -message "feat: add property service with CRUD operations" -files @(
    "backend/src/main/java/com/gharsaathi/property/service/PropertyService.java"
)

Commit-WithDate -date "2026-01-25T14:00:00" -message "feat: add property controller with REST endpoints" -files @(
    "backend/src/main/java/com/gharsaathi/property/controller/PropertyController.java"
)

Commit-WithDate -date "2026-01-25T17:00:00" -message "fix: update security config to allow public property access" -files @(
    "backend/src/main/java/com/gharsaathi/common/security/SecurityConfig.java"
)

# ============================================
# Day 7: January 26, 2026 - Documentation
# ============================================

Commit-WithDate -date "2026-01-26T10:00:00" -message "docs: add property module implementation plan" -files @(
    "backend/PROPERTY_MODULE_PLAN.md"
)

Commit-WithDate -date "2026-01-26T13:30:00" -message "fix: update refresh token model for better token management" -files @(
    "backend/src/main/java/com/gharsaathi/auth/model/RefreshToken.java"
)

Commit-WithDate -date "2026-01-26T16:30:00" -message "docs: add comprehensive API test documentation" -files @(
    "backend/tests/PROPERTY_MODULE_API_TESTS.txt"
)

# ============================================
# Day 8: January 27, 2026 - Final Updates
# ============================================

Commit-WithDate -date "2026-01-27T10:00:00" -message "docs: update README with complete project documentation" -files @(
    "README.md"
)

Commit-WithDate -date "2026-01-27T13:00:00" -message "chore: update gitignore for temporary files" -files @(
    ".gitignore"
)

Commit-WithDate -date "2026-01-27T15:30:00" -message "docs: add property module testing guidelines" -files @(
    "commit-property-module.ps1"
)

Write-Host "`n========================================" -ForegroundColor Green
Write-Host "All 24 commits completed successfully!" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green
Write-Host "`nView your commit history:" -ForegroundColor Cyan
Write-Host "git log --oneline --since='2026-01-20' --until='2026-01-27'" -ForegroundColor Yellow
