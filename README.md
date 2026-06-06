# 📚 Library Management — Spring Boot Backend

![Java-17-orange](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
![SpringBoot-4.x-brightgreen](https://img.shields.io/badge/SpringBoot-4.x-brightgreen?style=for-the-badge&logo=springboot)
![PostgreSQL-blue](https://img.shields.io/badge/PostgreSQL-blue?style=for-the-badge&logo=postgresql)
![Maven-Build-red](https://img.shields.io/badge/Maven-Build-red?style=for-the-badge&logo=apachemaven)

## 🚀 Overview

Simple library management backend implemented with Spring Boot, JPA/Hibernate and a relational database. This project supports:
- Managing books (title, author, isbn, category, copies)
- Managing members and their active status
- Borrow/transfer flow that creates a `TransferBook` (borrow record) and decrements available copies
- Basic validation, custom exception handling and transactional service methods

The real package base in this repository is `com.librarymanagement.application` (check `src/main/java`). README content below reflects the actual code structure and endpoints.

## 🛠 Tech Stack

- Java 17
- Spring Boot 4.x (WebMVC, Data JPA)
- Hibernate / JPA (Jakarta packages used in entities)
- PostgreSQL (recommended) or H2 for quick testing
- Maven

## ✅ Features (implemented)

- Add and query books (by author/title)
- Create borrow/transfer records (TransferBook) for members
- Global exception handling via `CustomException` & `GlobalExceptionHandler`
- Transactional service method for the borrow flow to maintain data consistency

## 📂 Project structure (actual)

```
src
├── main
│   ├── java
│   │   └── com.librarymanagement.application
│   │       ├── controller        # REST controllers (LibController)
│   │       ├── entity            # JPA entities (Book, Member, TransferBook)
│   │       ├── exception         # CustomException, GlobalExceptionHandler
│   │       ├── libEnum           # Enums (Category)
│   │       ├── model             # DTOs (BookRequest)
│   │       ├── repository        # Spring Data repositories
│   │       └── service           # Business logic (LibService)
│   └── resources
│       └── application.properties
└── test
    └── java
```

## 🔗 Key components and important fields

- `Book` (entity): `bookTitle` (unique), `isbn`, `author`, `category` (enum), `availableCopies` (Integer)
- `Member` (entity): `memberId` (long), `memberShipType`, `email`, `activeStatus` (boolean)
- `TransferBook` (entity): links `Book` and `Member`, fields: `holdings` (Integer), `overDew` (Integer), `isbn`, `pendingOverDew` (boolean)
- `LibService`: contains business logic; critical method `transfer(String memberId, String bookTitle)` coordinates validations and writes
- `LibController` (base path `/api`) exposes endpoints used by clients

## 🗂 Config package

There is a `config` package at `src/main/java/com/librarymanagement/application/config` containing application configuration classes:

- `WebSecurityConfig.java` — defines a `SecurityFilterChain` bean. It whitelists Swagger endpoints (`/swagger-ui/**`, `/v3/api-docs/**`) and currently permits `/api/trades/**` and requires authentication for other requests. CSRF is disabled in that bean for simplicity. Review the whitelisted paths and change them to match your real endpoints (for example `/api/books/**` or `/api/transactions/**`) if needed.
- `swaggerConfig.java` — provides an `OpenAPI` bean (springdoc) with basic API metadata (title, version, contact). This enables SpringDoc/OpenAPI UI if the `springdoc-openapi` dependency is present.

Notes / recommendations:
- The config classes are already present; they may have slightly different naming (e.g. `swaggerConfig` lower-cased). Consider renaming it to `SwaggerConfig` (class names should start with uppercase) for readability and consistency.
- `WebSecurityConfig` currently permits `/swagger-ui/**` and `/v3/api-docs/**` so Swagger UI and API docs are accessible without authentication.
- `WebSecurityConfig` also whitelists `/api/**` or remove it if you want those endpoints secured.

## 📡 API Endpoints (from `LibController`)

Base path: `/api`

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET    | `/api` | Health check - returns "Backend working" |
| GET    | `/api/books/search/{name}` | Get books by author name (path variable `name`) |
| POST   | `/api/books` | Add a new book — accepts `BookRequest` JSON in request body |
| POST   | `/api/transactions/borrow` | Borrow/transfer a book — requires `memberId` and `bookTitle` as request parameters |

Example: borrow via curl

```
curl -X POST "http://localhost:8080/api/transactions/borrow?memberId=123&bookTitle=MyBookTitle"
```

Check `LibController.java` for exact request/response shapes.

## 🧭 Transactional guidance (practical)

- The borrow/transfer operation modifies multiple entities (decrements `Book.availableCopies` and creates a `TransferBook`). This method must be executed in a single transaction so either all changes succeed or none do.
- Use `@Transactional` from `org.springframework.transaction.annotation.Transactional` on the service method (`LibService.transfer(...)`) for Spring-managed transactions.
- `CustomException` extends `RuntimeException`, which will trigger rollback by default. If you need to roll back on checked exceptions, configure `rollbackFor` on `@Transactional`.

## ⚙ Configuration (example)

Set DB credentials in `src/main/resources/application.properties`:

```
# PostgreSQL example
spring.datasource.url=jdbc:postgresql://localhost:5432/librarydb
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

For quick local tests you can use H2 by adding a profile and switching the datasource URL.

## 🧪 Run & Test (Windows)

From project root (Windows Command Prompt):

```
.\mvnw.cmd clean package
.\mvnw.cmd spring-boot:run
```

Run unit tests:

```
.\mvnw.cmd test
```

Then exercise endpoints via Postman/Insomnia/curl.

## 🧩 Next steps / TODOs

- Add unit + integration tests for `LibService.transfer(...)` asserting rollback behavior.
- Add request validation for `BookRequest` and better error responses in `GlobalExceptionHandler`.
- Add paging & filters for book search and members.
- Consider adding authentication (JWT) and role-based access (admin vs member).
---

## 👨‍💻 Author

**Bala Shanmugam R**
**| Java Backend Developer**

⭐ **Star this repo if you find it helpful!** 🚀

