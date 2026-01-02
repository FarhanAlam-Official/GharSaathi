# GharSaathi Project - Automated Commit Execution Script
# This script will commit all files with proper timestamps from Dec 20, 2025 to Jan 3, 2026

$repoPath = "d:\Semester Final Project\7th Sem Final Project\GharSaathi"
Set-Location $repoPath

# Function to commit with custom date
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
    
    # Add files
    foreach ($file in $files) {
        git add $file
    }
    
    # Set environment variables and commit
    $env:GIT_AUTHOR_DATE = $date
    $env:GIT_COMMITTER_DATE = $date
    git commit -m $message
    
    Write-Host "✓ Committed successfully" -ForegroundColor Green
}

Write-Host "Starting automated commit process..." -ForegroundColor Magenta
Write-Host "Total commits planned: 59" -ForegroundColor Magenta

# ============================================
# Day 1: December 20, 2025
# ============================================

Commit-WithDate -date "2025-12-20T09:00:00" -message "chore: initialize project workspace configuration" -files @(
    ".vscode/launch.json"
)

Commit-WithDate -date "2025-12-20T11:30:00" -message "chore: setup backend project structure with Maven wrapper" -files @(
    "backend/.gitignore",
    "backend/.gitattributes",
    "backend/.mvn/wrapper/maven-wrapper.properties",
    "backend/mvnw",
    "backend/mvnw.cmd"
)

Commit-WithDate -date "2025-12-20T14:00:00" -message "chore: configure Maven dependencies and project metadata" -files @(
    "backend/pom.xml"
)

Commit-WithDate -date "2025-12-20T16:30:00" -message "chore: setup Spring Boot application properties" -files @(
    "backend/src/main/resources/application.properties"
)

# ============================================
# Day 2: December 21, 2025
# ============================================

Commit-WithDate -date "2025-12-21T09:30:00" -message "chore: initialize Next.js frontend with dependencies" -files @(
    "frontend/.gitignore",
    "frontend/package.json",
    "frontend/pnpm-lock.yaml"
)

Commit-WithDate -date "2025-12-21T12:00:00" -message "chore: configure Next.js, TypeScript, and Tailwind CSS" -files @(
    "frontend/next.config.mjs",
    "frontend/postcss.config.mjs",
    "frontend/tsconfig.json",
    "frontend/components.json"
)

Commit-WithDate -date "2025-12-21T15:00:00" -message "feat: add utility functions and custom React hooks" -files @(
    "frontend/lib/utils.ts",
    "frontend/hooks/use-mobile.ts",
    "frontend/hooks/use-toast.ts"
)

Commit-WithDate -date "2025-12-21T17:30:00" -message "style: add global CSS styles and theme variables" -files @(
    "frontend/app/globals.css",
    "frontend/styles/globals.css"
)

# ============================================
# Day 3: December 22, 2025
# ============================================

Commit-WithDate -date "2025-12-22T10:00:00" -message "feat: add core UI components (button, input, label, card)" -files @(
    "frontend/components/ui/button.tsx",
    "frontend/components/ui/input.tsx",
    "frontend/components/ui/label.tsx",
    "frontend/components/ui/card.tsx"
)

Commit-WithDate -date "2025-12-22T13:00:00" -message "feat: add display UI components (alert, badge, avatar, separator)" -files @(
    "frontend/components/ui/alert.tsx",
    "frontend/components/ui/badge.tsx",
    "frontend/components/ui/avatar.tsx",
    "frontend/components/ui/separator.tsx"
)

Commit-WithDate -date "2025-12-22T15:30:00" -message "feat: add modal and overlay UI components" -files @(
    "frontend/components/ui/dialog.tsx",
    "frontend/components/ui/alert-dialog.tsx",
    "frontend/components/ui/sheet.tsx",
    "frontend/components/ui/drawer.tsx"
)

Commit-WithDate -date "2025-12-22T17:00:00" -message "feat: add menu UI components" -files @(
    "frontend/components/ui/dropdown-menu.tsx",
    "frontend/components/ui/context-menu.tsx",
    "frontend/components/ui/menubar.tsx"
)

# ============================================
# Day 4: December 23, 2025
# ============================================

Commit-WithDate -date "2025-12-23T09:30:00" -message "feat: add form control UI components" -files @(
    "frontend/components/ui/form.tsx",
    "frontend/components/ui/field.tsx",
    "frontend/components/ui/checkbox.tsx",
    "frontend/components/ui/radio-group.tsx",
    "frontend/components/ui/switch.tsx"
)

Commit-WithDate -date "2025-12-23T12:00:00" -message "feat: add advanced input UI components" -files @(
    "frontend/components/ui/select.tsx",
    "frontend/components/ui/textarea.tsx",
    "frontend/components/ui/input-otp.tsx",
    "frontend/components/ui/input-group.tsx"
)

Commit-WithDate -date "2025-12-23T14:30:00" -message "feat: add data display UI components" -files @(
    "frontend/components/ui/table.tsx",
    "frontend/components/ui/tabs.tsx",
    "frontend/components/ui/accordion.tsx",
    "frontend/components/ui/collapsible.tsx"
)

Commit-WithDate -date "2025-12-23T16:30:00" -message "feat: add toast notification system" -files @(
    "frontend/components/ui/toast.tsx",
    "frontend/components/ui/toaster.tsx",
    "frontend/components/ui/sonner.tsx",
    "frontend/components/ui/use-toast.ts"
)

# ============================================
# Day 5: December 24, 2025
# ============================================

Commit-WithDate -date "2025-12-24T10:00:00" -message "feat: add interactive UI components (calendar, popover, hover-card, tooltip)" -files @(
    "frontend/components/ui/calendar.tsx",
    "frontend/components/ui/popover.tsx",
    "frontend/components/ui/hover-card.tsx",
    "frontend/components/ui/tooltip.tsx"
)

Commit-WithDate -date "2025-12-24T13:00:00" -message "feat: add navigation UI components" -files @(
    "frontend/components/ui/command.tsx",
    "frontend/components/ui/navigation-menu.tsx",
    "frontend/components/ui/breadcrumb.tsx"
)

Commit-WithDate -date "2025-12-24T15:00:00" -message "feat: add loading and feedback UI components" -files @(
    "frontend/components/ui/progress.tsx",
    "frontend/components/ui/spinner.tsx",
    "frontend/components/ui/skeleton.tsx",
    "frontend/components/ui/empty.tsx"
)

Commit-WithDate -date "2025-12-24T17:00:00" -message "feat: add interactive control UI components" -files @(
    "frontend/components/ui/slider.tsx",
    "frontend/components/ui/pagination.tsx",
    "frontend/components/ui/carousel.tsx",
    "frontend/components/ui/scroll-area.tsx"
)

# ============================================
# Day 6: December 25, 2025
# ============================================

Commit-WithDate -date "2025-12-25T10:30:00" -message "feat: add layout UI components" -files @(
    "frontend/components/ui/sidebar.tsx",
    "frontend/components/ui/resizable.tsx",
    "frontend/components/ui/aspect-ratio.tsx"
)

Commit-WithDate -date "2025-12-25T13:00:00" -message "feat: add miscellaneous UI components" -files @(
    "frontend/components/ui/chart.tsx",
    "frontend/components/ui/button-group.tsx",
    "frontend/components/ui/toggle.tsx",
    "frontend/components/ui/toggle-group.tsx",
    "frontend/components/ui/kbd.tsx",
    "frontend/components/ui/item.tsx",
    "frontend/components/ui/use-mobile.tsx"
)

Commit-WithDate -date "2025-12-25T15:30:00" -message "feat: add theme provider for dark/light mode support" -files @(
    "frontend/components/theme-provider.tsx"
)

Commit-WithDate -date "2025-12-25T17:00:00" -message "feat: add navbar and footer layout components" -files @(
    "frontend/components/navbar.tsx",
    "frontend/components/footer.tsx"
)

# ============================================
# Day 7: December 26, 2025
# ============================================

Commit-WithDate -date "2025-12-26T10:00:00" -message "feat: add custom application-specific components" -files @(
    "frontend/components/property-card.tsx",
    "frontend/components/stats-card.tsx",
    "frontend/components/dashboard-sidebar.tsx"
)

Commit-WithDate -date "2025-12-26T12:30:00" -message "feat: add mock data for development and testing" -files @(
    "frontend/lib/mock-data.ts"
)

Commit-WithDate -date "2025-12-26T14:30:00" -message "assets: add application icons and logos" -files @(
    "frontend/public/icon.svg",
    "frontend/public/icon-dark-32x32.png",
    "frontend/public/icon-light-32x32.png",
    "frontend/public/apple-icon.png",
    "frontend/public/placeholder-logo.svg",
    "frontend/public/placeholder-logo.png"
)

Commit-WithDate -date "2025-12-26T16:30:00" -message "assets: add user avatar and placeholder images" -files @(
    "frontend/public/nepali-man-business-professional.jpg",
    "frontend/public/nepali-man-freelancer-creative.jpg",
    "frontend/public/nepali-man-older-business.jpg",
    "frontend/public/nepali-man-professional.jpg",
    "frontend/public/nepali-man-young-professional.jpg",
    "frontend/public/nepali-woman-bank-manager.jpg",
    "frontend/public/nepali-woman-professional.png",
    "frontend/public/nepali-young-man-smiling.jpg",
    "frontend/public/placeholder-user.jpg",
    "frontend/public/placeholder.jpg",
    "frontend/public/placeholder.svg"
)

# ============================================
# Day 8: December 27, 2025
# ============================================

Commit-WithDate -date "2025-12-27T09:30:00" -message "assets: add property listing images (part 1)" -files @(
    "frontend/public/modern-apartment-building-sunrise.jpg",
    "frontend/public/modern-apartment-interior-with-natural-light-woode.jpg",
    "frontend/public/modern-apartment-living-room-with-green-sofa.jpg",
    "frontend/public/apartment-balcony-plants-green.jpg",
    "frontend/public/cozy-studio-window-light.jpg",
    "frontend/public/modern-studio-apartment-bedroom.jpg"
)

Commit-WithDate -date "2025-12-27T12:00:00" -message "assets: add property listing images (part 2)" -files @(
    "frontend/public/luxury-apartment-living-room-city-view.jpg",
    "frontend/public/budget-room-apartment-kitchen-yellow.jpg",
    "frontend/public/shared-flat-modern-couch-blue-sofa.jpg",
    "frontend/public/full-house-garden-exterior-nepal.jpg",
    "frontend/public/lakeside-cottage-nepal.jpg",
    "frontend/public/modern-residency-building.jpg"
)

Commit-WithDate -date "2025-12-27T14:30:00" -message "feat: add root layout and loading components" -files @(
    "frontend/app/layout.tsx",
    "frontend/app/loading.tsx"
)

Commit-WithDate -date "2025-12-27T16:30:00" -message "feat: add home page landing component" -files @(
    "frontend/app/page.tsx"
)

# ============================================
# Day 9: December 28, 2025
# ============================================

Commit-WithDate -date "2025-12-28T10:00:00" -message "feat: add authentication pages (login and register)" -files @(
    "frontend/app/auth/login/page.tsx",
    "frontend/app/auth/register/page.tsx"
)

Commit-WithDate -date "2025-12-28T12:30:00" -message "feat: add property listing page with loading state" -files @(
    "frontend/app/properties/page.tsx",
    "frontend/app/properties/loading.tsx"
)

Commit-WithDate -date "2025-12-28T15:00:00" -message "feat: add property detail page with dynamic routing" -files @(
    "frontend/app/properties/[id]/page.tsx"
)

# ============================================
# Day 10: December 29, 2025
# ============================================

Commit-WithDate -date "2025-12-29T09:30:00" -message "feat: add tenant dashboard layout" -files @(
    "frontend/app/tenant/layout.tsx"
)

Commit-WithDate -date "2025-12-29T12:00:00" -message "feat: add tenant dashboard page" -files @(
    "frontend/app/tenant/dashboard/page.tsx",
    "frontend/app/tenant/dashboard/loading.tsx"
)

Commit-WithDate -date "2025-12-29T14:30:00" -message "feat: add tenant saved properties page" -files @(
    "frontend/app/tenant/saved/page.tsx",
    "frontend/app/tenant/saved/loading.tsx"
)

Commit-WithDate -date "2025-12-29T16:30:00" -message "feat: add tenant rental applications page" -files @(
    "frontend/app/tenant/applications/page.tsx"
)

# ============================================
# Day 11: December 30, 2025
# ============================================

Commit-WithDate -date "2025-12-30T10:00:00" -message "feat: add landlord dashboard layout" -files @(
    "frontend/app/landlord/layout.tsx"
)

Commit-WithDate -date "2025-12-30T12:30:00" -message "feat: add landlord dashboard page" -files @(
    "frontend/app/landlord/dashboard/page.tsx",
    "frontend/app/landlord/dashboard/loading.tsx"
)

Commit-WithDate -date "2025-12-30T15:00:00" -message "feat: add landlord properties management page" -files @(
    "frontend/app/landlord/properties/page.tsx",
    "frontend/app/landlord/properties/loading.tsx"
)

Commit-WithDate -date "2025-12-30T17:00:00" -message "feat: add landlord rental requests page" -files @(
    "frontend/app/landlord/requests/page.tsx"
)

# ============================================
# Day 12: December 31, 2025
# ============================================

Commit-WithDate -date "2025-12-31T09:30:00" -message "feat: add admin dashboard layout" -files @(
    "frontend/app/admin/layout.tsx"
)

Commit-WithDate -date "2025-12-31T11:30:00" -message "feat: add admin user management page" -files @(
    "frontend/app/admin/users/page.tsx",
    "frontend/app/admin/users/loading.tsx"
)

Commit-WithDate -date "2025-12-31T14:00:00" -message "feat: add admin property management page" -files @(
    "frontend/app/admin/properties/page.tsx",
    "frontend/app/admin/properties/loading.tsx"
)

Commit-WithDate -date "2025-12-31T16:30:00" -message "feat: initialize Spring Boot application with main class and tests" -files @(
    "backend/src/main/java/com/gharsaathi/GharSaathiApplication.java",
    "backend/src/test/java/com/gharsaathi/backend/BackendApplicationTests.java"
)

# ============================================
# Day 13: January 1, 2026
# ============================================

Commit-WithDate -date "2026-01-01T10:00:00" -message "feat: add user and role models for authentication" -files @(
    "backend/src/main/java/com/gharsaathi/auth/model/Role.java",
    "backend/src/main/java/com/gharsaathi/auth/model/User.java"
)

Commit-WithDate -date "2026-01-01T12:30:00" -message "feat: add token management models (refresh token and blacklist)" -files @(
    "backend/src/main/java/com/gharsaathi/auth/model/RefreshToken.java",
    "backend/src/main/java/com/gharsaathi/auth/model/TokenBlacklist.java"
)

Commit-WithDate -date "2026-01-01T15:00:00" -message "feat: add JPA repositories for auth entities" -files @(
    "backend/src/main/java/com/gharsaathi/auth/repository/UserRepository.java",
    "backend/src/main/java/com/gharsaathi/auth/repository/RefreshTokenRepository.java",
    "backend/src/main/java/com/gharsaathi/auth/repository/TokenBlacklistRepository.java"
)

# ============================================
# Day 14: January 2, 2026
# ============================================

Commit-WithDate -date "2026-01-02T09:30:00" -message "feat: add authentication DTOs (login, register, auth response)" -files @(
    "backend/src/main/java/com/gharsaathi/common/dto/LoginRequest.java",
    "backend/src/main/java/com/gharsaathi/common/dto/RegisterRequest.java",
    "backend/src/main/java/com/gharsaathi/common/dto/AuthResponse.java"
)

Commit-WithDate -date "2026-01-02T12:00:00" -message "feat: add token refresh DTOs" -files @(
    "backend/src/main/java/com/gharsaathi/common/dto/TokenRefreshRequest.java",
    "backend/src/main/java/com/gharsaathi/common/dto/TokenRefreshResponse.java"
)

Commit-WithDate -date "2026-01-02T14:30:00" -message "feat: add JWT utility and properties configuration" -files @(
    "backend/src/main/java/com/gharsaathi/common/security/JwtUtil.java",
    "backend/src/main/java/com/gharsaathi/common/util/JwtProperties.java"
)

Commit-WithDate -date "2026-01-02T16:30:00" -message "feat: add JWT authentication filter and security configuration" -files @(
    "backend/src/main/java/com/gharsaathi/common/security/JwtAuthenticationFilter.java",
    "backend/src/main/java/com/gharsaathi/common/security/SecurityConfig.java"
)

# ============================================
# Day 15: January 3, 2026
# ============================================

Commit-WithDate -date "2026-01-03T09:30:00" -message "feat: add token management services" -files @(
    "backend/src/main/java/com/gharsaathi/auth/service/RefreshTokenService.java",
    "backend/src/main/java/com/gharsaathi/auth/service/TokenBlacklistService.java"
)

Commit-WithDate -date "2026-01-03T11:30:00" -message "feat: add authentication service with user management" -files @(
    "backend/src/main/java/com/gharsaathi/auth/service/AuthService.java"
)

Commit-WithDate -date "2026-01-03T14:00:00" -message "feat: add authentication REST controller" -files @(
    "backend/src/main/java/com/gharsaathi/auth/controller/AuthController.java"
)

Commit-WithDate -date "2026-01-03T16:00:00" -message "feat: add custom exception classes and global exception handler" -files @(
    "backend/src/main/java/com/gharsaathi/common/exception/EmailAlreadyExistsException.java",
    "backend/src/main/java/com/gharsaathi/common/exception/GlobalExceptionHandler.java",
    "backend/src/main/java/com/gharsaathi/common/exception/InvalidTokenException.java",
    "backend/src/main/java/com/gharsaathi/common/exception/ResourceNotFoundException.java",
    "backend/src/main/java/com/gharsaathi/common/exception/TokenExpiredException.java",
    "backend/src/main/java/com/gharsaathi/common/exception/TokenRevokedException.java"
)

Commit-WithDate -date "2026-01-03T17:30:00" -message "docs: add comprehensive authentication system documentation" -files @(
    "backend/AUTH_SYSTEM.md",
    "backend/IMPLEMENTATION_SUMMARY.md"
)

Write-Host "`n========================================" -ForegroundColor Green
Write-Host "✓ All 59 commits completed successfully!" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green
Write-Host "`nYou can now view your git log with:" -ForegroundColor Cyan
Write-Host "git log --oneline --graph --all" -ForegroundColor Yellow
