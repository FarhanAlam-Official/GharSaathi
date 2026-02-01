# GharSaathi Documentation

> Comprehensive documentation for the GharSaathi Property Rental Platform

## 📚 Documentation Structure

This documentation follows industry-standard organization with clear separation of concerns. Each section focuses on a specific aspect of the platform to make it easy to find the information you need.

### Documentation Sections

| Section | Description | Key Topics |
|---------|-------------|------------|
| **[00-overview](./00-overview/)** | Project introduction and high-level concepts | Project overview, problem statement, objectives, scope, glossary |
| **[01-requirements](./01-requirements/)** | System requirements and specifications | Functional requirements, non-functional requirements, user roles, use cases |
| **[02-architecture](./02-architecture/)** | Technical architecture and design | System architecture, layered architecture, package structure, tech stack |
| **[03-api](./03-api/)** | API documentation and reference | API overview, endpoints, request/response samples, error handling |
| **[04-spring-boot](./04-spring-boot/)** | Spring Boot configuration and setup | Profiles, application properties, dependency management, monitoring |
| **[05-security](./05-security/)** | Security implementation details | Spring Security, authentication flow, authorization, JWT, CORS |
| **[06-database](./06-database/)** | Database design and implementation | Database schema, JPA entities, repositories, migrations, indexing |
| **[07-exception-logging](./07-exception-logging/)** | Error handling and logging | Global exception handling, custom exceptions, logging strategy, auditing |
| **[08-testing](./08-testing/)** | Testing strategy and test cases | Unit testing, integration testing, API testing, test data |
| **[09-devops](./09-devops/)** | Deployment and operations | Environment setup, build process, Docker, CI/CD, production deployment |
| **[10-performance](./10-performance/)** | Performance optimization | Caching, async processing, pagination, optimization notes |
| **[11-maintenance](./11-maintenance/)** | Ongoing maintenance | Versioning, changelog, known issues, future enhancements |

## 🚀 Quick Start

### For Developers
1. Start with [00-overview](./00-overview/) to understand the project
2. Review [02-architecture](./02-architecture/) to understand the system design
3. Check [09-devops](./09-devops/) for environment setup
4. Refer to [03-api](./03-api/) for API documentation

### For Testers
1. Review [08-testing](./08-testing/) for testing strategy
2. Check [03-api](./03-api/) for API endpoints
3. Use test files in the `tests/` folder for API testing

### For DevOps Engineers
1. Start with [09-devops](./09-devops/) for deployment information
2. Review [04-spring-boot](./04-spring-boot/) for configuration
3. Check [10-performance](./10-performance/) for optimization strategies

## 📋 Naming Conventions

This documentation follows consistent naming conventions:
- **Filenames**: `kebab-case.md` (e.g., `system-architecture.md`)
- **Section folders**: `00-prefix-name/` (numbered for logical ordering)
- **Headings**: Sentence case with title capitalization for main sections
- **Code blocks**: Always specify language for syntax highlighting
- **Links**: Use relative paths within documentation

## 🔄 Version Information

- **Last Updated**: January 28, 2026
- **Documentation Version**: 1.0.0
- **Application Version**: 0.0.1-SNAPSHOT
- **Backend Framework**: Spring Boot 4.0.1
- **Frontend Framework**: Next.js 14

## 📝 Contributing to Documentation

When updating documentation:
1. Follow the established structure and naming conventions
2. Keep documents focused on a single topic
3. Use clear, concise language
4. Include code examples where appropriate
5. Update the changelog in [11-maintenance](./11-maintenance/)
6. Cross-reference related documents

## 🔗 Related Resources

- [Main Project README](../README.md)
- [Backend Source Code](../backend/)
- [Frontend Source Code](../frontend/)
- [API Tests](../backend/tests/)

## 📧 Contact & Support

For questions or clarifications about this documentation:
- Project Repository: GharSaathi
- Documentation Issues: Create an issue in the project repository
- Team Contact: [Add team contact information]

---

**Note**: This documentation is actively maintained and updated as the project evolves. Last review: January 28, 2026
