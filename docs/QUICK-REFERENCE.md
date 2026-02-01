# Quick Reference Guide

> Fast access to key documentation for GharSaathi

## 🚀 Getting Started (Under 5 Minutes)

### For Complete Beginners
1. **What is this?** → [Project Overview](./00-overview/project-overview.md)
2. **What problem does it solve?** → [Problem Statement](./00-overview/problem-statement.md)
3. **What can I do with it?** → [User Roles](./01-requirements/user-roles.md)

### For Developers
1. **Tech Stack** → [Tech Stack](./02-architecture/tech-stack.md)
2. **API Reference** → [API Overview](./03-api/api-overview.md)
3. **Setup Instructions** → [DevOps README](./09-devops/README.md)

### For Testers
1. **Testing Approach** → [Testing README](./08-testing/README.md)
2. **API Endpoints** → [API Overview](./03-api/api-overview.md)
3. **Test Cases** → `backend/tests/` directory

## 📚 Documentation Sections (Quick Links)

### Essential Reading
| What You Need | Where to Find It |
|---------------|------------------|
| Project intro | [00-overview/project-overview.md](./00-overview/project-overview.md) |
| User roles & permissions | [01-requirements/user-roles.md](./01-requirements/user-roles.md) |
| Technologies used | [02-architecture/tech-stack.md](./02-architecture/tech-stack.md) |
| API endpoints | [03-api/api-overview.md](./03-api/api-overview.md) |
| Naming standards | [NAMING-CONVENTIONS.md](./NAMING-CONVENTIONS.md) |

### By Role
| I am a... | Start Here |
|-----------|-----------|
| **Student/Academic** | [Project Overview](./00-overview/project-overview.md) → [Objectives](./00-overview/objectives.md) |
| **Developer** | [Tech Stack](./02-architecture/tech-stack.md) → [API Overview](./03-api/api-overview.md) |
| **Tester** | [Testing README](./08-testing/README.md) → [API Overview](./03-api/api-overview.md) |
| **DevOps Engineer** | [DevOps README](./09-devops/README.md) → [Performance](./10-performance/README.md) |
| **Evaluator/Reviewer** | [Project Overview](./00-overview/project-overview.md) → [INDEX](./INDEX.md) |

## 🔍 Find What You Need

### By Topic

#### Understanding the Project
- [What is GharSaathi?](./00-overview/project-overview.md)
- [What problems does it solve?](./00-overview/problem-statement.md)
- [What are the goals?](./00-overview/objectives.md)
- [What's included/excluded?](./00-overview/scope.md)
- [Terms and definitions](./00-overview/glossary.md)

#### Technical Architecture
- [System design](./02-architecture/README.md)
- [Technologies used](./02-architecture/tech-stack.md)
- [Spring Boot setup](./04-spring-boot/README.md)
- [Security implementation](./05-security/README.md)
- [Database design](./06-database/README.md)

#### API & Development
- [API overview and endpoints](./03-api/api-overview.md)
- [User roles and permissions](./01-requirements/user-roles.md)
- [Naming conventions](./NAMING-CONVENTIONS.md)
- [Testing strategy](./08-testing/README.md)
- [Exception handling](./07-exception-logging/README.md)

#### Operations
- [Environment setup](./09-devops/README.md)
- [Performance optimization](./10-performance/README.md)
- [Version history](./11-maintenance/changelog.md)
- [Known issues](./11-maintenance/README.md#known-issues)

## 📊 Key Statistics (At a Glance)

### Project Metrics
- **Backend**: Spring Boot 4.0.1 + Java 21
- **Frontend**: Next.js 14 + TypeScript 5.0
- **Database**: MySQL 8.0
- **API Endpoints**: 60+
- **Database Tables**: 18+
- **Completion**: 98%

### User Roles
- **TENANT**: Browse properties, submit applications
- **LANDLORD**: List properties, manage applications
- **ADMIN**: Platform oversight, user management

### Core Modules
1. Authentication (JWT + RBAC)
2. Property Management
3. Rental Applications
4. Lease Management
5. Payment Tracking
6. User Profiles
7. Admin Tools
8. File Upload
9. Dashboard Analytics

## 🎯 Common Tasks

### Setup Development Environment
```bash
# Backend
cd backend
./mvnw spring-boot:run

# Frontend
cd frontend
pnpm install
pnpm dev
```
📖 Full guide: [DevOps README](./09-devops/README.md)

### Test API Endpoints
```bash
# Login
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"user@email.com","password":"password"}'

# Use token
curl http://localhost:8080/api/v1/properties \
  -H "Authorization: Bearer <token>"
```
📖 Full guide: [API Overview](./03-api/api-overview.md)

### Understand User Roles
- TENANT: Can browse and apply
- LANDLORD: Can list properties
- ADMIN: Can manage platform
📖 Full guide: [User Roles](./01-requirements/user-roles.md)

## 📖 Documentation Structure

```
docs/
├── 00-overview/           # Project introduction (5 files) ✅
├── 01-requirements/       # System requirements (2 files)
├── 02-architecture/       # Technical design (2 files)
├── 03-api/                # API documentation (2 files)
├── 04-spring-boot/        # Spring Boot config (1 file)
├── 05-security/           # Security docs (1 file)
├── 06-database/           # Database design (1 file)
├── 07-exception-logging/  # Error handling (1 file)
├── 08-testing/            # Testing docs (1 file)
├── 09-devops/             # Deployment (1 file)
├── 10-performance/        # Optimization (1 file)
├── 11-maintenance/        # Maintenance (2 files) ✅
├── README.md              # Documentation hub ✅
├── NAMING-CONVENTIONS.md  # Coding standards ✅
├── INDEX.md               # Complete file list ✅
└── DOCUMENTATION-SUMMARY.md # Creation summary ✅
```

Total: **23 files created**, foundation structure 100% complete

## 🔗 External Resources

### Project Files
- **Main README**: `../README.md`
- **Backend Code**: `../backend/src/`
- **Frontend Code**: `../frontend/`
- **Test Cases**: `../backend/tests/`

### Technology Documentation
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Next.js](https://nextjs.org/docs)
- [MySQL](https://dev.mysql.com/doc/)
- [JWT](https://jwt.io/introduction)

## 💡 Quick Tips

### Reading the Docs
- Start with overview sections
- READMEs provide section navigation
- Use INDEX.md for complete file list
- Cross-references link related topics

### Contributing
- Follow naming conventions
- Update relevant sections
- Keep formatting consistent
- Add to changelog

### Finding Information
1. Check section README first
2. Use INDEX.md for file location
3. Search within specific files
4. Follow cross-references

## 📞 Need More Help?

### Documentation Issues
- Missing information? Check [INDEX.md](./INDEX.md) for planned files
- Unclear content? Check [DOCUMENTATION-SUMMARY.md](./DOCUMENTATION-SUMMARY.md)
- Want to contribute? Follow [NAMING-CONVENTIONS.md](./NAMING-CONVENTIONS.md)

### Technical Questions
- API usage: [API Overview](./03-api/api-overview.md)
- Setup issues: [DevOps README](./09-devops/README.md)
- Architecture questions: [Architecture README](./02-architecture/README.md)

---

**Quick Tip**: Bookmark this page for fast access to all documentation! 🔖

**Last Updated**: January 28, 2026  
**Documentation Version**: 1.0.0
