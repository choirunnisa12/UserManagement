# User Management System

A Spring Boot REST API for user management, featuring JWT authentication, paging, sorting, validation, caching, async operations, Swagger documentation, and MySQL.

## Table of Contents

- Features
- Tech Stack
- Prerequisites
- Getting Started
- API Endpoints
- Project Structure
- Testing
- Contributing

## Features

- **Authentication:** JWT-based login and registration
- **User Management:** CRUD operations for users (create, read, update, delete)
- **Search & Paging:** Search users by name, paging, and sorting support
- **Validation:** Input validation for registration and user data
- **Rate Limiting:** Basic rate limiting endpoint example
- **Caching:** User list caching for performance
- **Async:** Asynchronous endpoints for better scalability
- **API Documentation:** Swagger UI for easy API exploration
- **Testing:** Unit and integration tests

## Tech Stack

- Java 17+ (or newer)
- Spring Boot
- Spring Security (JWT)
- Spring Data JPA
- MySQL
- Lombok
- Swagger/OpenAPI
- Maven

## Prerequisites

- Java 17 or newer
- Maven 3.6+
- MySQL 8.0+
- Git

## Getting Started

1. **Clone and Setup**
   ```bash
   git clone https://github.com/choirunnisa12/UserManagement.git
   cd UserManagementt
   ```

2. **Database Setup**
   - Create the database:
     ```sql
     CREATE DATABASE usermanagement;
     ```
   - Update `src/main/resources/application.properties`:
     ```properties
     spring.datasource.username=your_username
     spring.datasource.password=your_password
     spring.datasource.url=jdbc:mysql://localhost:3306/usermanagement
     ```

3. **Build and Run**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
   The app will run at http://localhost:8080

## API Endpoints

- **Swagger UI:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Auth

- `POST /auth/register` — Register a new user
- `POST /auth/login` — Login and get JWT token

### Users

- `GET /users` — List users (with paging/sorting)
- `GET /users/{id}` — Get user by ID
- `POST /users` — Create user
- `PUT /users/{id}` — Update user
- `DELETE /users/{id}` — Delete user
- `GET /users/search?name=...` — Search users by name

### Example: Register

**Request:**
```json
POST /auth/register
{
  "name": "Amisha",
  "email": "amisha@email.com",
  "password": "amisha123",
  "birthDate": "2000-01-01"
}
```

**Validation Error Response:**
```json
{
  "errors": [
    "Name must be between 3-50 characters",
    "Email should be valid",
    "Password must be at least 6 characters"
  ]
}
```

### Example: Login

**Request:**
```json
POST /auth/login
{
  "email": "amisha@email.com",
  "password": "amisha123"
}
```

**Response:**
```json
{
  "token": "jwt-token-string",
  "message": "Login Successful"
}
```

## Project Structure

```
src/main/java/com/example/UserManagementt
├── UserManagementtApplication.java
├── config/
│   ├── SecurityConfig.java
│   └── SwaggerConfig.java
├── controller/
│   ├── AuthController.java
│   └── UserController.java
├── dto/
│   ├── LoginRequest.java
│   ├── RegisterRequest.java
│   ├── UserDTO.java
│   ├── LoginResponse.java
│   └── UserResponseDto.java
├── entity/
│   └── User.java
├── exception/
│   ├── GlobalException.java
│   └── UserNotFoundException.java
├── repository/
│   └── UserRepository.java
├── security/
│   ├── JwtFilter.java
│   └── JwtUtil.java
└── service/
    ├── AuthService.java
    ├── UserService.java
    └── impl/
        ├── AuthServiceImpl.java
        ├── UserServiceImpl.java
        └── RateLimiterService.java
```

## Testing

Run all tests:
```bash
mvn test
```
Includes:
- Service layer unit tests
- Authentication integration tests
- Repository layer tests

## Contributing

1. Fork the repo
2. Create a feature branch
3. Make your changes (+ tests if needed)
4. Submit a pull request
