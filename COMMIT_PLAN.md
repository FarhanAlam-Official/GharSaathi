# GharSaathi Project - Commit Plan (Dec 20, 2025 - Jan 3, 2026)

## Overview

- **Total Files**: 168
- **Duration**: 15 days
- **Commits per day**: 3-4
- **Total commits**: ~50 commits
- **Development Flow**: Project initialization → Frontend setup → Backend setup → Integration

---

## **Day 1: December 20, 2025** (Project Foundation)

### Commit 1 (9:00 AM)

```bash
git add .vscode/
git commit -m "chore: initialize project workspace configuration"
```

**Files**: `.vscode/launch.json`

### Commit 2 (11:30 AM)

```bash
git add backend/.gitignore backend/.gitattributes backend/.mvn/ backend/mvnw backend/mvnw.cmd
git commit -m "chore: setup backend project structure with Maven wrapper"
```

**Files**: `.gitignore`, `.gitattributes`, Maven wrapper files

### Commit 3 (2:00 PM)

```bash
git add backend/pom.xml
git commit -m "chore: configure Maven dependencies and project metadata"
```

**Files**: `pom.xml`

### Commit 4 (4:30 PM)

```bash
git add backend/src/main/resources/application.properties
git commit -m "chore: setup Spring Boot application properties"
```

**Files**: `application.properties`

---

## **Day 2: December 21, 2025** (Frontend Initialization)

### Commit 5 (9:30 AM)

```bash
git add frontend/.gitignore frontend/package.json frontend/pnpm-lock.yaml
git commit -m "chore: initialize Next.js frontend with dependencies"
```

**Files**: Frontend package files

### Commit 6 (12:00 PM)

```bash
git add frontend/next.config.mjs frontend/postcss.config.mjs frontend/tsconfig.json frontend/components.json
git commit -m "chore: configure Next.js, TypeScript, and Tailwind CSS"
```

**Files**: Config files

### Commit 7 (3:00 PM)

```bash
git add frontend/lib/utils.ts frontend/hooks/
git commit -m "feat: add utility functions and custom React hooks"
```

**Files**: `utils.ts`, `use-mobile.ts`, `use-toast.ts`

### Commit 8 (5:30 PM)

```bash
git add frontend/app/globals.css frontend/styles/globals.css
git commit -m "style: add global CSS styles and theme variables"
```

**Files**: Global CSS files

---

## **Day 3: December 22, 2025** (UI Components Foundation - Part 1)

### Commit 9 (10:00 AM)

```bash
git add frontend/components/ui/button.tsx frontend/components/ui/input.tsx frontend/components/ui/label.tsx frontend/components/ui/card.tsx
git commit -m "feat: add core UI components (button, input, label, card)"
```

**Files**: Basic UI components

### Commit 10 (1:00 PM)

```bash
git add frontend/components/ui/alert.tsx frontend/components/ui/badge.tsx frontend/components/ui/avatar.tsx frontend/components/ui/separator.tsx
git commit -m "feat: add display UI components (alert, badge, avatar, separator)"
```

**Files**: Display components

### Commit 11 (3:30 PM)

```bash
git add frontend/components/ui/dialog.tsx frontend/components/ui/alert-dialog.tsx frontend/components/ui/sheet.tsx frontend/components/ui/drawer.tsx
git commit -m "feat: add modal and overlay UI components"
```

**Files**: Modal components

### Commit 12 (5:00 PM)

```bash
git add frontend/components/ui/dropdown-menu.tsx frontend/components/ui/context-menu.tsx frontend/components/ui/menubar.tsx
git commit -m "feat: add menu UI components"
```

**Files**: Menu components

---

## **Day 4: December 23, 2025** (UI Components Foundation - Part 2)

### Commit 13 (9:30 AM)

```bash
git add frontend/components/ui/form.tsx frontend/components/ui/field.tsx frontend/components/ui/checkbox.tsx frontend/components/ui/radio-group.tsx frontend/components/ui/switch.tsx
git commit -m "feat: add form control UI components"
```

**Files**: Form components

### Commit 14 (12:00 PM)

```bash
git add frontend/components/ui/select.tsx frontend/components/ui/textarea.tsx frontend/components/ui/input-otp.tsx frontend/components/ui/input-group.tsx
git commit -m "feat: add advanced input UI components"
```

**Files**: Input components

### Commit 15 (2:30 PM)

```bash
git add frontend/components/ui/table.tsx frontend/components/ui/tabs.tsx frontend/components/ui/accordion.tsx frontend/components/ui/collapsible.tsx
git commit -m "feat: add data display UI components"
```

**Files**: Data display components

### Commit 16 (4:30 PM)

```bash
git add frontend/components/ui/toast.tsx frontend/components/ui/toaster.tsx frontend/components/ui/sonner.tsx frontend/components/ui/use-toast.ts
git commit -m "feat: add toast notification system"
```

**Files**: Toast components

---

## **Day 5: December 24, 2025** (UI Components Foundation - Part 3)

### Commit 17 (10:00 AM)

```bash
git add frontend/components/ui/calendar.tsx frontend/components/ui/popover.tsx frontend/components/ui/hover-card.tsx frontend/components/ui/tooltip.tsx
git commit -m "feat: add interactive UI components (calendar, popover, hover-card, tooltip)"
```

**Files**: Interactive components

### Commit 18 (1:00 PM)

```bash
git add frontend/components/ui/command.tsx frontend/components/ui/navigation-menu.tsx frontend/components/ui/breadcrumb.tsx
git commit -m "feat: add navigation UI components"
```

**Files**: Navigation components

### Commit 19 (3:00 PM)

```bash
git add frontend/components/ui/progress.tsx frontend/components/ui/spinner.tsx frontend/components/ui/skeleton.tsx frontend/components/ui/empty.tsx
git commit -m "feat: add loading and feedback UI components"
```

**Files**: Feedback components

### Commit 20 (5:00 PM)

```bash
git add frontend/components/ui/slider.tsx frontend/components/ui/pagination.tsx frontend/components/ui/carousel.tsx frontend/components/ui/scroll-area.tsx
git commit -m "feat: add interactive control UI components"
```

**Files**: Control components

---

## **Day 6: December 25, 2025** (Remaining UI & Layout Components)

### Commit 21 (10:30 AM)

```bash
git add frontend/components/ui/sidebar.tsx frontend/components/ui/resizable.tsx frontend/components/ui/aspect-ratio.tsx
git commit -m "feat: add layout UI components"
```

**Files**: Layout components

### Commit 22 (1:00 PM)

```bash
git add frontend/components/ui/chart.tsx frontend/components/ui/button-group.tsx frontend/components/ui/toggle.tsx frontend/components/ui/toggle-group.tsx frontend/components/ui/kbd.tsx frontend/components/ui/item.tsx frontend/components/ui/use-mobile.tsx
git commit -m "feat: add miscellaneous UI components"
```

**Files**: Misc components

### Commit 23 (3:30 PM)

```bash
git add frontend/components/theme-provider.tsx
git commit -m "feat: add theme provider for dark/light mode support"
```

**Files**: `theme-provider.tsx`

### Commit 24 (5:00 PM)

```bash
git add frontend/components/navbar.tsx frontend/components/footer.tsx
git commit -m "feat: add navbar and footer layout components"
```

**Files**: Layout components

---

## **Day 7: December 26, 2025** (Custom Components & Assets)

### Commit 25 (10:00 AM)

```bash
git add frontend/components/property-card.tsx frontend/components/stats-card.tsx frontend/components/dashboard-sidebar.tsx
git commit -m "feat: add custom application-specific components"
```

**Files**: App-specific components

### Commit 26 (12:30 PM)

```bash
git add frontend/lib/mock-data.ts
git commit -m "feat: add mock data for development and testing"
```

**Files**: `mock-data.ts`

### Commit 27 (2:30 PM)

```bash
git add frontend/public/*.svg frontend/public/icon*.png frontend/public/apple-icon.png
git commit -m "assets: add application icons and logos"
```

**Files**: Icons and logos

### Commit 28 (4:30 PM)

```bash
git add frontend/public/nepali-*.jpg frontend/public/nepali-*.png frontend/public/placeholder*.jpg frontend/public/placeholder*.svg frontend/public/placeholder*.png
git commit -m "assets: add user avatar and placeholder images"
```

**Files**: User images

---

## **Day 8: December 27, 2025** (Property Assets & Main Layout)

### Commit 29 (9:30 AM)

```bash
git add "frontend/public/modern-apartment-*.jpg" "frontend/public/apartment-*.jpg" "frontend/public/cozy-*.jpg" "frontend/public/modern-studio-*.jpg"
git commit -m "assets: add property listing images (part 1)"
```

**Files**: Property images batch 1

### Commit 30 (12:00 PM)

```bash
git add "frontend/public/luxury-*.jpg" "frontend/public/budget-*.jpg" "frontend/public/shared-*.jpg" "frontend/public/full-house-*.jpg" "frontend/public/lakeside-*.jpg" "frontend/public/modern-residency-*.jpg"
git commit -m "assets: add property listing images (part 2)"
```

**Files**: Property images batch 2

### Commit 31 (2:30 PM)

```bash
git add frontend/app/layout.tsx frontend/app/loading.tsx
git commit -m "feat: add root layout and loading components"
```

**Files**: Root layout

### Commit 32 (4:30 PM)

```bash
git add frontend/app/page.tsx
git commit -m "feat: add home page landing component"
```

**Files**: Home page

---

## **Day 9: December 28, 2025** (Frontend Auth & Property Pages)

### Commit 33 (10:00 AM)

```bash
git add frontend/app/auth/login/page.tsx frontend/app/auth/register/page.tsx
git commit -m "feat: add authentication pages (login and register)"
```

**Files**: Auth pages

### Commit 34 (12:30 PM)

```bash
git add frontend/app/properties/page.tsx frontend/app/properties/loading.tsx
git commit -m "feat: add property listing page with loading state"
```

**Files**: Property listing

### Commit 35 (3:00 PM)

```bash
git add "frontend/app/properties/[id]/page.tsx"
git commit -m "feat: add property detail page with dynamic routing"
```

**Files**: Property detail

---

## **Day 10: December 29, 2025** (Tenant Dashboard)

### Commit 36 (9:30 AM)

```bash
git add frontend/app/tenant/layout.tsx
git commit -m "feat: add tenant dashboard layout"
```

**Files**: Tenant layout

### Commit 37 (12:00 PM)

```bash
git add frontend/app/tenant/dashboard/page.tsx frontend/app/tenant/dashboard/loading.tsx
git commit -m "feat: add tenant dashboard page"
```

**Files**: Tenant dashboard

### Commit 38 (2:30 PM)

```bash
git add frontend/app/tenant/saved/page.tsx frontend/app/tenant/saved/loading.tsx
git commit -m "feat: add tenant saved properties page"
```

**Files**: Saved properties

### Commit 39 (4:30 PM)

```bash
git add frontend/app/tenant/applications/page.tsx
git commit -m "feat: add tenant rental applications page"
```

**Files**: Applications page

---

## **Day 11: December 30, 2025** (Landlord Dashboard)

### Commit 40 (10:00 AM)

```bash
git add frontend/app/landlord/layout.tsx
git commit -m "feat: add landlord dashboard layout"
```

**Files**: Landlord layout

### Commit 41 (12:30 PM)

```bash
git add frontend/app/landlord/dashboard/page.tsx frontend/app/landlord/dashboard/loading.tsx
git commit -m "feat: add landlord dashboard page"
```

**Files**: Landlord dashboard

### Commit 42 (3:00 PM)

```bash
git add frontend/app/landlord/properties/page.tsx frontend/app/landlord/properties/loading.tsx
git commit -m "feat: add landlord properties management page"
```

**Files**: Landlord properties

### Commit 43 (5:00 PM)

```bash
git add frontend/app/landlord/requests/page.tsx
git commit -m "feat: add landlord rental requests page"
```

**Files**: Rental requests

---

## **Day 12: December 31, 2025** (Admin Dashboard & Backend Core)

### Commit 44 (9:30 AM)

```bash
git add frontend/app/admin/layout.tsx
git commit -m "feat: add admin dashboard layout"
```

**Files**: Admin layout

### Commit 45 (11:30 AM)

```bash
git add frontend/app/admin/users/page.tsx frontend/app/admin/users/loading.tsx
git commit -m "feat: add admin user management page"
```

**Files**: User management

### Commit 46 (2:00 PM)

```bash
git add frontend/app/admin/properties/page.tsx frontend/app/admin/properties/loading.tsx
git commit -m "feat: add admin property management page"
```

**Files**: Property management

### Commit 47 (4:30 PM)

```bash
git add backend/src/main/java/com/gharsaathi/GharSaathiApplication.java backend/src/test/java/com/gharsaathi/backend/BackendApplicationTests.java
git commit -m "feat: initialize Spring Boot application with main class and tests"
```

**Files**: Main application class

---

## **Day 13: January 1, 2026** (Backend Auth Models & Repositories)

### Commit 48 (10:00 AM)

```bash
git add backend/src/main/java/com/gharsaathi/auth/model/Role.java backend/src/main/java/com/gharsaathi/auth/model/User.java
git commit -m "feat: add user and role models for authentication"
```

**Files**: User and Role models

### Commit 49 (12:30 PM)

```bash
git add backend/src/main/java/com/gharsaathi/auth/model/RefreshToken.java backend/src/main/java/com/gharsaathi/auth/model/TokenBlacklist.java
git commit -m "feat: add token management models (refresh token and blacklist)"
```

**Files**: Token models

### Commit 50 (3:00 PM)

```bash
git add backend/src/main/java/com/gharsaathi/auth/repository/UserRepository.java backend/src/main/java/com/gharsaathi/auth/repository/RefreshTokenRepository.java backend/src/main/java/com/gharsaathi/auth/repository/TokenBlacklistRepository.java
git commit -m "feat: add JPA repositories for auth entities"
```

**Files**: Repositories

---

## **Day 14: January 2, 2026** (Backend DTOs & Security Config)

### Commit 51 (9:30 AM)

```bash
git add backend/src/main/java/com/gharsaathi/common/dto/LoginRequest.java backend/src/main/java/com/gharsaathi/common/dto/RegisterRequest.java backend/src/main/java/com/gharsaathi/common/dto/AuthResponse.java
git commit -m "feat: add authentication DTOs (login, register, auth response)"
```

**Files**: Auth DTOs

### Commit 52 (12:00 PM)

```bash
git add backend/src/main/java/com/gharsaathi/common/dto/TokenRefreshRequest.java backend/src/main/java/com/gharsaathi/common/dto/TokenRefreshResponse.java
git commit -m "feat: add token refresh DTOs"
```

**Files**: Token DTOs

### Commit 53 (2:30 PM)

```bash
git add backend/src/main/java/com/gharsaathi/common/security/JwtUtil.java backend/src/main/java/com/gharsaathi/common/util/JwtProperties.java
git commit -m "feat: add JWT utility and properties configuration"
```

**Files**: JWT utilities

### Commit 54 (4:30 PM)

```bash
git add backend/src/main/java/com/gharsaathi/common/security/JwtAuthenticationFilter.java backend/src/main/java/com/gharsaathi/common/security/SecurityConfig.java
git commit -m "feat: add JWT authentication filter and security configuration"
```

**Files**: Security config

---

## **Day 15: January 3, 2026** (Backend Services, Controllers & Exception Handling)

### Commit 55 (9:30 AM)

```bash
git add backend/src/main/java/com/gharsaathi/auth/service/RefreshTokenService.java backend/src/main/java/com/gharsaathi/auth/service/TokenBlacklistService.java
git commit -m "feat: add token management services"
```

**Files**: Token services

### Commit 56 (11:30 AM)

```bash
git add backend/src/main/java/com/gharsaathi/auth/service/AuthService.java
git commit -m "feat: add authentication service with user management"
```

**Files**: Auth service

### Commit 57 (2:00 PM)

```bash
git add backend/src/main/java/com/gharsaathi/auth/controller/AuthController.java
git commit -m "feat: add authentication REST controller"
```

**Files**: Auth controller

### Commit 58 (4:00 PM)

```bash
git add backend/src/main/java/com/gharsaathi/common/exception/
git commit -m "feat: add custom exception classes and global exception handler"
```

**Files**: All exception classes

### Commit 59 (5:30 PM)

```bash
git add backend/AUTH_SYSTEM.md backend/IMPLEMENTATION_SUMMARY.md
git commit -m "docs: add comprehensive authentication system documentation"
```

**Files**: Documentation

---

## Execution Instructions

### Using Git with Custom Dates

```bash
# Set custom date for commits
GIT_AUTHOR_DATE="2025-12-20T09:00:00" GIT_COMMITTER_DATE="2025-12-20T09:00:00" git commit -m "message"
```

### PowerShell Script for Windows

```powershell
# Set environment variables in PowerShell
$env:GIT_AUTHOR_DATE="2025-12-20T09:00:00"
$env:GIT_COMMITTER_DATE="2025-12-20T09:00:00"
git commit -m "message"
```

### Batch Execution Script

I'll create a PowerShell script to automate all commits with proper timestamps.

---

## Summary Statistics

- **Total Commits**: 59
- **Average per day**: 3.9 commits
- **Distribution**:
  - Day 1-2: Project setup (8 commits)
  - Day 3-6: UI Components (16 commits)
  - Day 7-8: Assets & Layouts (12 commits)
  - Day 9-12: Frontend Pages (17 commits)
  - Day 13-15: Backend Implementation (12 commits)

**Commit Types Breakdown:**

- `feat:` 43 commits (73%)
- `chore:` 9 commits (15%)
- `assets:` 4 commits (7%)
- `docs:` 2 commits (3%)
- `style:` 1 commit (2%)
