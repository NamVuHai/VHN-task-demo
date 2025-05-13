# Task Manager Microservices Project

## Introduction

The **Task Manager Microservices** project is an application built using a **microservices** architecture, leveraging **Spring Boot** and related technologies within the Spring ecosystem. The project consists of multiple independent services, each responsible for a specific functionality, interconnected via **Spring Cloud Gateway** and **Eureka Discovery Server**.

Key functionalities of the project:
- **Auth Service**: Manages user authentication and authorization using **JWT (JSON Web Token)**.
- **Task Manager Service**: Handles task management with features for creating, updating, deleting, and searching tasks.
- **Discovery Server**: Uses **Eureka** to manage and discover services.
- **API Gateway**: Uses **Spring Cloud Gateway** to route requests to the appropriate services.

Data is stored in a **PostgreSQL** database, and the project employs **QueryDSL** for flexible search queries. Each service is designed with a **4-layer** architecture (Controller, API, Service, Repository) to ensure separation of concerns, enhancing independence and maintainability.

## Technologies Used

- **Programming Language**: Java 17
- **Framework**: Spring Boot, Spring Cloud
- **Discovery Server**: Eureka
- **API Gateway**: Spring Cloud Gateway
- **Authentication/Authorization**: JWT (JSON Web Token)
- **Database**: PostgreSQL
- **Data Querying**: Spring Data JPA, QueryDSL
- **Dependency Management**: Maven
- **Containerization**: Docker, Docker Compose
- **Database Migration**: Flyway
- **Testing**: JUnit 5, Mockito

## Architecture

The project adopts a **microservices** architecture with the following core components:

1. **Eureka Discovery Server**:
   - Acts as a registry for services to register and discover each other.
   - Manages service instances and supports load balancing.

2. **Spring Cloud Gateway**:
   - Routes client requests to the appropriate services.
   - Provides features like request filtering, JWT validation, and error handling.

3. **Auth Service**:
   - Manages user registration and login.
   - Generates and validates **JWT** for access control to other services.

4. **Task Manager Service**:
   - Manages tasks with CRUD operations (Create, Read, Update, Delete).
   - Uses **QueryDSL** for dynamic search queries.

### 4-Layer Architecture
Each service follows a **4-layer** design to separate logic:
- **Controller**: Handles HTTP requests from clients, forwarding to the API layer.
- **API**: Contains core business logic, separated logic for each api.
- **Service**: Interacting with the Repository.
- **Repository**: Manages data queries with the database, using Spring Data JPA and QueryDSL.

This model:
- Separates responsibilities across layers.
- Simplifies scalability and maintenance.
- Enhances the independence of business logic.

## Modules

### assignment-core Module
The `assignment-core` module is a shared library containing common components and utilities used across all microservices in the project. It centralizes reusable code to promote consistency, reduce duplication, and simplify maintenance.

#### Purpose
- Provides a single source of truth for shared functionality, such as base entities, API interfaces, and response models.
- Ensures all services adhere to the same standards and logic.

#### Key Components
- **BaseEntity**: Abstract base class for JPA entities with audit fields (e.g., `createdAt`, `updatedAt`, `createBy`).
- **CommonApi**: Generic interface for API implementations (e.g., `CreateUserApi`, `GetUserDetailApi`).
- **ResponseModel**: Wrapper for standardizing API responses.
- **Utilities**: Shared helpers for validation, exception handling, or DTO conversions.

#### Role
- Dependency for `assignment-auth`, `assignment-task-manager`, and `assignment-gateway`.
- Built first (via `mvn clean install`) to provide its artifact to other modules.
- Supports the 4-layer architecture by supplying components for API and Repository layers.

#### Usage
Included as a Maven dependency:
```xml
<dependency>
    <groupId>com.assignment</groupId>
    <artifactId>assignment-core</artifactId>
    <version>${project.version}</version>
</dependency>
```

#### Benefits
- Reusability and consistency across services.
- Simplified maintenance and scalability.

## Directory Structure

Main directory structure of the project:
```
parent-directory/
├── assignment-core/
│   ├── pom.xml
│   └── src/
├── assignment-gateway/
│   ├── pom.xml
│   ├── src/
│   └── Dockerfile
├── assignment-auth/
│   ├── pom.xml
│   └── src/
│   └── Dockerfile
├── assignment-task-manager/
│   ├── pom.xml
│   └── src/
│   └── Dockerfile
├── assignment_discovery/
│   ├── pom.xml
│   └── src/
│   └── Dockerfile
├── docker-compose.yml
```

- `assignment-core`: Contains shared components (e.g., `BaseEntity`, `CommonApi`).
- `assignment-gateway`: Spring Cloud Gateway for request routing.
- `assignment-auth`: Handles authentication and authorization with JWT.
- `assignment-task-manager`: Manages tasks, using QueryDSL for search.
- `assignment_discovery`: Eureka Discovery Server.

## System Requirements

- **Java**: JDK 17
- **Maven**: 3.8.x
- **Docker**: Docker Desktop (or Docker CLI)
- **PostgreSQL**: Version 14 or higher (can be run via Docker)
- **RAM**: Minimum 4GB (recommended 8GB)
- **Operating System**: Linux, macOS, or Windows

## Setup and Running Instructions

### 1. Clone the Repository
```bash
git clone <repository-url>
cd parent-directory
```

### 2. Run with Docker Compose
- Use `docker-compose.yml` to run all services:
  ```bash
  docker-compose up --build
  ```
- The `docker-compose.yml` will:
  - Build and run `assignment-gateway`, `auth-service`, `task-manager-service`, and `eureka-server`.
  - Initialize a PostgreSQL container.
- Access:
  - Eureka Dashboard: `http://localhost:8761`
  - Gateway: `http://localhost:8080`
  - API endpoints: Via the gateway (e.g., `http://localhost:8080/auth/login`, `http://localhost:8080/tasks`).


## API Endpoints

- Check the PostMan file for API endpoints.

## Testing

- Unit tests use **JUnit 5** and **Mockito**, covering the API layer (e.g., `CreateUserApiTest`, `GetUserDetailApiTest`, `DeleteUserApiTest`).
- Run tests:
  ```bash
  mvn test
  ```