# User Management Application

This is a User Management REST API built with Spring Boot, JWT, Caching, pagination, sorting and basic CRUD operations.  

## Features

- Register and log in users securely (JWT authentication)
- Passwords are safely hashed
- Manage users: create, read, update, and delete
- Consistent error handling with clear messages
- Interactive API documentation (Swagger)
- Ready for containerization with Docker

## Tech Stack

- Java 17+
- Spring Boot & Spring Security
- JWT (JSON Web Token)
- Maven
- H2 Database (for development/testing)
- Swagger (OpenAPI)
- Docker

## Getting Started

### Prerequisites

- Java 17 or newer
- Maven
- (Optional) Docker

### How to Run Locally

1. **Clone the repository:**
   ```bash
   git clone https://github.com/yourusername/UserManagementt.git
   cd UserManagementt
   ```

2. **Build and start the application:**
   ```bash
   ./mvnw clean install
   ./mvnw spring-boot:run
   ```

   The API will be available at [http://localhost:8080](http://localhost:8080).

### Run with Docker

1. **Build the Docker image:**
   ```bash
   docker build -t user-management-app .
   ```

2. **Run the container:**
   ```bash
   docker run -p 8080:8080 user-management-app
   ```

### API Documentation

You can explore and test the API using Swagger UI:  
[http://localhost:8080/swagger-ui/](http://localhost:8080/swagger-ui/)

### Example Endpoints

- `POST /api/auth/register` — Register a new user
- `POST /api/auth/login` — Log in and receive a JWT token
- `GET /api/users` — List all users (requires authentication)
- `GET /api/users/{id}` — Get user details by ID
- `PUT /api/users/{id}` — Update user information
- `DELETE /api/users/{id}` — Delete a user

### Running Tests

To run all tests:
```bash
./mvnw test
```

## Project Structure

```
src/main/java/com/example/UserManagementt/
  ├── config/         # Security and Swagger configuration
  ├── controller/     # REST controllers
  ├── dto/            # Data Transfer Objects
  ├── entity/         # JPA entities
  ├── exception/      # Custom exceptions and handlers
  ├── repository/     # Spring Data repositories
  ├── security/       # JWT utilities and filters
  ├── service/        # Service interfaces and implementations
```

## Deployment

This application can be easily deployed to any cloud provider that supports Java or Docker (e.g., Heroku, Railway, AWS).  
See the [Dockerfile](Dockerfile) for details.

## About Me

Hi, my name is Choirunnisa.  
I'm passionate about backend development and always eager to learn new technologies.  
Feel free to connect with me:

- [LinkedIn](https://www.linkedin.com/in/choirunnisa-choirunnisaa)
- [GitHub](https://github.com/choirunnisa12)

---

Thank you for checking out this project!
