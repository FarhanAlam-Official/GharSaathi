# Tech Stack - GharSaathi

> Comprehensive overview of technologies, frameworks, and tools

## 🏗️ Architecture Overview

GharSaathi follows a **modern three-tier architecture**:
- **Frontend**: Next.js + TypeScript
- **Backend**: Spring Boot + Java
- **Database**: MySQL

---

## 🎨 Frontend Stack

### Core Framework

#### Next.js 14
- **Purpose**: React framework for production
- **Version**: 14.0+
- **Key Features**:
  - Server-side rendering (SSR)
  - Static site generation (SSG)
  - File-based routing
  - API routes
  - Image optimization
  - Built-in TypeScript support

#### React 18
- **Purpose**: UI library
- **Version**: 18.0+
- **Key Features**:
  - Component-based architecture
  - Hooks for state management
  - Virtual DOM
  - Concurrent rendering
  - Automatic batching

### Language

#### TypeScript 5.0
- **Purpose**: Type-safe JavaScript
- **Benefits**:
  - Static type checking
  - Better IDE support
  - Improved code documentation
  - Reduced runtime errors
  - Enhanced refactoring

### Styling

#### Tailwind CSS 3.x
- **Purpose**: Utility-first CSS framework
- **Benefits**:
  - Rapid UI development
  - Consistent design system
  - Responsive design utilities
  - Dark mode support
  - Small production bundle

### UI Components

#### shadcn/ui
- **Purpose**: Re-usable component library
- **Based On**: Radix UI primitives
- **Components Used**:
  - Button, Input, Select
  - Dialog, Dropdown Menu
  - Card, Badge, Avatar
  - Form components
  - Table, Pagination
  - Toast notifications

#### Radix UI
- **Purpose**: Unstyled, accessible component primitives
- **Benefits**:
  - WAI-ARIA compliant
  - Keyboard navigation
  - Focus management
  - Fully customizable

### State Management

#### React Context API
- **Purpose**: Global state management
- **Use Cases**:
  - User authentication state
  - Theme (dark/light mode)
  - User preferences

### Form Handling

#### React Hook Form
- **Purpose**: Form state management and validation
- **Benefits**:
  - Performance-optimized
  - Easy validation
  - Minimal re-renders
  - TypeScript support

### HTTP Client

#### Fetch API / Axios
- **Purpose**: API communication
- **Features**:
  - Promise-based requests
  - Request/response interceptors
  - Automatic JSON transformation
  - Error handling

### Package Manager

#### pnpm
- **Purpose**: Fast, disk space efficient package manager
- **Benefits**:
  - Faster installations
  - Disk space savings
  - Strict dependency resolution

---

## ⚙️ Backend Stack

### Core Framework

#### Spring Boot 4.0.1
- **Purpose**: Java application framework
- **Key Features**:
  - Auto-configuration
  - Embedded server (Tomcat)
  - Production-ready features
  - Simplified dependency management
  - Opinionated defaults

### Language

#### Java 21
- **Purpose**: Programming language
- **Features Used**:
  - Records (for DTOs)
  - Pattern matching
  - Text blocks
  - Enhanced switch expressions
  - Sealed classes

### Spring Ecosystem

#### Spring Security 6.x
- **Purpose**: Authentication and authorization
- **Features**:
  - JWT token-based auth
  - Role-based access control
  - Method-level security
  - CORS configuration
  - Password encryption (BCrypt)

#### Spring Data JPA
- **Purpose**: Data access layer
- **Features**:
  - Repository pattern
  - Query derivation
  - Pagination and sorting
  - Transaction management
  - Auditing support

#### Spring Web MVC
- **Purpose**: Web layer and REST API
- **Features**:
  - RESTful endpoints
  - Request/response handling
  - Exception handling
  - Input validation
  - Content negotiation

### ORM

#### Hibernate 6.x
- **Purpose**: Object-relational mapping
- **Features**:
  - Entity management
  - Lazy/eager loading
  - Caching (first-level, second-level)
  - HQL (Hibernate Query Language)
  - Criteria API

### Security

#### JWT (JSON Web Tokens)
- **Library**: io.jsonwebtoken (jjwt) 0.12.6
- **Components**:
  - jjwt-api: Core API
  - jjwt-impl: Implementation
  - jjwt-jackson: JSON processing
- **Features**:
  - Token generation
  - Token parsing and validation
  - Claims management
  - Signature verification

#### BCrypt
- **Purpose**: Password hashing
- **Strength**: 12 rounds
- **Included In**: Spring Security

### Build Tool

#### Apache Maven 3.x
- **Purpose**: Dependency management and build automation
- **Files**:
  - `pom.xml`: Project configuration
  - `mvnw`: Maven wrapper for consistent builds
- **Features**:
  - Dependency resolution
  - Build lifecycle management
  - Plugin ecosystem
  - Multi-module support

---

## 💾 Database Stack

### Database System

#### MySQL 8.0+
- **Purpose**: Relational database management system
- **Features Used**:
  - InnoDB storage engine
  - ACID compliance
  - Foreign key constraints
  - Indexes for performance
  - Transaction support

### Database Driver

#### MySQL Connector/J
- **Purpose**: JDBC driver for MySQL
- **Included In**: Spring Boot
- **Features**:
  - Connection pooling
  - Prepared statements
  - Batch operations

### Connection Pooling

#### HikariCP
- **Purpose**: High-performance JDBC connection pool
- **Default In**: Spring Boot
- **Configuration**:
  ```properties
  spring.datasource.hikari.maximum-pool-size=10
  spring.datasource.hikari.minimum-idle=5
  ```

---

## 🔧 Development Tools

### Code Editor

#### Visual Studio Code
- **Extensions**:
  - Extension Pack for Java
  - Spring Boot Extension Pack
  - ESLint
  - Prettier
  - Tailwind CSS IntelliSense
  - TypeScript and JavaScript Language Features

### Version Control

#### Git
- **Platform**: GitHub / GitLab / Local
- **Branching**: Feature branches
- **Commit Convention**: Conventional Commits

### API Testing

#### Postman
- **Purpose**: API development and testing
- **Features**:
  - Request collections
  - Environment variables
  - Automated testing
  - Documentation generation

### Database Tools

#### MySQL Workbench
- **Purpose**: Database design and management
- **Features**:
  - Visual schema design
  - Query editor
  - Data import/export
  - Server administration

#### DBeaver
- **Purpose**: Universal database tool
- **Features**:
  - Multi-database support
  - ER diagrams
  - SQL editor
  - Data visualization

---

## 📦 Key Dependencies

### Backend Dependencies

```xml
<!-- Spring Boot Starters -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webmvc</artifactId>
</dependency>

<!-- Database -->
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
</dependency>

<!-- JWT -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.12.6</version>
</dependency>

<!-- Lombok -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
</dependency>

<!-- DevTools -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
</dependency>
```

### Frontend Dependencies

```json
{
  "dependencies": {
    "next": "^14.0.0",
    "react": "^18.0.0",
    "react-dom": "^18.0.0",
    "typescript": "^5.0.0",
    "tailwindcss": "^3.0.0",
    "@radix-ui/react-*": "latest",
    "class-variance-authority": "^0.7.0",
    "clsx": "^2.0.0",
    "tailwind-merge": "^2.0.0",
    "lucide-react": "^0.294.0"
  },
  "devDependencies": {
    "@types/node": "^20.0.0",
    "@types/react": "^18.0.0",
    "@types/react-dom": "^18.0.0",
    "eslint": "^8.0.0",
    "eslint-config-next": "^14.0.0",
    "postcss": "^8.0.0",
    "autoprefixer": "^10.0.0"
  }
}
```

---

## 🚀 Why These Technologies?

### Spring Boot
✅ **Industry Standard**: Widely used in enterprise applications  
✅ **Comprehensive**: Complete ecosystem for Java development  
✅ **Production-Ready**: Built-in monitoring, security, and configuration  
✅ **Community**: Large community and extensive documentation  
✅ **Learning Value**: Highly valued skill in job market

### Next.js + React
✅ **Modern**: Latest web development practices  
✅ **Performance**: Optimized rendering and bundling  
✅ **Developer Experience**: Hot reload, TypeScript, file-based routing  
✅ **SEO-Friendly**: Server-side rendering support  
✅ **Popular**: Large ecosystem of libraries and components

### TypeScript
✅ **Type Safety**: Catch errors during development  
✅ **Better Tooling**: Superior IDE support and auto-completion  
✅ **Maintainability**: Self-documenting code  
✅ **Industry Trend**: Increasingly preferred over JavaScript

### MySQL
✅ **Proven**: Battle-tested relational database  
✅ **Free & Open Source**: No licensing costs  
✅ **Well-Documented**: Extensive resources available  
✅ **Performance**: Optimized for read-heavy workloads  
✅ **Academic Familiarity**: Commonly taught in courses

### Tailwind CSS
✅ **Rapid Development**: Build UIs quickly  
✅ **Consistency**: Design system out of the box  
✅ **Customizable**: Fully configurable  
✅ **Performance**: Small production bundles  
✅ **Popular**: Strong community and resources

---

## 📊 Technology Comparison

### Why Not Alternatives?

#### Backend Alternatives
| Alternative | Why Not Chosen |
|-------------|----------------|
| **Node.js + Express** | Spring Boot offers better structure for larger applications and is more aligned with academic curriculum |
| **Django (Python)** | Java/Spring Boot has stronger industry demand and academic support |
| **ASP.NET Core** | Requires Windows/Visual Studio ecosystem, less cross-platform friendly |

#### Frontend Alternatives
| Alternative | Why Not Chosen |
|-------------|----------------|
| **Vue.js** | React has larger ecosystem and job market demand |
| **Angular** | Steeper learning curve, more opinionated |
| **Svelte** | Smaller ecosystem, less industry adoption |
| **Plain React (CRA)** | Next.js provides better structure and performance |

#### Database Alternatives
| Alternative | Why Not Chosen |
|-------------|----------------|
| **PostgreSQL** | MySQL is more widely taught and sufficient for our needs |
| **MongoDB** | Relational data model better suits our domain |
| **SQLite** | Not suitable for production deployment |
| **Oracle/SQL Server** | Licensing costs, complexity |

---

## 🔄 Version Compatibility Matrix

| Component | Version | Compatibility |
|-----------|---------|---------------|
| Java | 21 | Spring Boot 4.0.1 ✅ |
| Spring Boot | 4.0.1 | Java 17+ ✅ |
| Node.js | 18+ | Next.js 14 ✅ |
| npm/pnpm | Latest | Next.js 14 ✅ |
| MySQL | 8.0+ | MySQL Connector/J ✅ |
| TypeScript | 5.0+ | Next.js 14 ✅ |

---

## 🔗 Related Documents

- [System Architecture](./system-architecture.md) - How components interact
- [Package Structure](./package-structure.md) - Code organization
- [Application Properties](../04-spring-boot/application-properties.md) - Configuration
- [Dependency Management](../04-spring-boot/dependency-management.md) - Managing dependencies

---

**Last Updated**: January 28, 2026  
**Version**: 1.0.0  
**Stack**: Spring Boot 4.0.1 + Next.js 14 + MySQL 8.0
