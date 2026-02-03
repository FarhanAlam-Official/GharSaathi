# Architecture Documentation

> System architecture and design documentation for GharSaathi

## 📋 Contents

This section contains technical architecture documentation:

### [System Architecture](./system-architecture.md)
High-level system design showing how frontend, backend, and database components interact.

### [Layered Architecture](./layered-architecture.md)
Detailed explanation of the three-tier architecture pattern used in GharSaathi.

### [Package Structure](./package-structure.md)
Complete breakdown of the codebase organization for both backend and frontend.

### [Tech Stack](./tech-stack.md)
Comprehensive list of all technologies, frameworks, and tools used in the project.

### [Deployment Diagram](./deployment-diagram.md)
Visual representation of the deployment architecture and infrastructure.

### [Sequence Diagrams](./sequence-diagrams.md)
Flow diagrams showing interactions for key use cases like authentication, property booking, and payment.

## 🏗️ Architecture Overview

GharSaathi follows a **modern three-tier architecture**:

```
┌─────────────────────┐
│   Frontend Layer    │  Next.js 14 + TypeScript
│   (Presentation)    │  
└──────────┬──────────┘
           │ REST API (JSON)
┌──────────┴──────────┐
│   Backend Layer     │  Spring Boot 4.0.1 + Java 21
│  (Business Logic)   │  
└──────────┬──────────┘
           │ JDBC
┌──────────┴──────────┐
│   Database Layer    │  MySQL 8.0
│   (Data Storage)    │  
└─────────────────────┘
```

## 🎯 Design Principles

- **Separation of Concerns**: Clear layer boundaries
- **Loose Coupling**: Components interact through well-defined interfaces
- **High Cohesion**: Related functionality grouped together
- **Scalability**: Stateless design enables horizontal scaling
- **Maintainability**: Consistent patterns and naming conventions

## 📦 Module Organization

Backend modules are organized by domain:
- `com.gharsaathi.auth` - Authentication & Authorization
- `com.gharsaathi.property` - Property Management
- `com.gharsaathi.rental.application` - Rental Applications
- `com.gharsaathi.lease` - Lease Management
- `com.gharsaathi.payment` - Payment System
- `com.gharsaathi.user` - User Management
- `com.gharsaathi.file` - File Upload
- `com.gharsaathi.dashboard` - Analytics
- `com.gharsaathi.common` - Shared Components

## 🔗 Quick Links

- [Tech Stack Details](./tech-stack.md) - Technologies used
- [API Documentation](../03-api/) - RESTful API reference
- [Database Design](../06-database/) - Database schema

---

**Last Updated**: January 28, 2026  
**Architecture**: Three-Tier, Layered, RESTful
