# 📂 Lifesync Backend - File Upload API

A modern Spring Boot application offering a secure and efficient API for file uploads using `multipart/form-data`.  
The backend is fortified with JWT Authentication and supports customizable file storage options.

---

## 🚀 Features

- Seamless file uploads via REST API
- JWT-secured endpoints for enhanced security
- Robust error handling for invalid requests
- MySQL integration with Spring Data JPA
- Built with the latest Spring Boot (3.4.5)
- High-performance Hibernate ORM (6.6.13.Final)
- Optimized database connections using HikariCP

---

## 🛠️ Tech Stack

- **Language:** Java 17
- **Framework:** Spring Boot
- **Modules:** Spring Web, Spring Security (JWT), Spring Data JPA
- **Database:** MySQL
- **Build Tool:** Maven

---

## 📦 Setup Instructions

1. **Clone the repository:**

   ```bash
   git clone https://github.com/your-username/lifesync-backend.git
   cd lifesync-backend
   ```

2. **Configure application properties:**

   Update `src/main/resources/application.properties` or `application.yml`:

   ```properties
   server.port=8080

   spring.datasource.url=jdbc:mysql://localhost:3306/your_db_name
   spring.datasource.username=your_db_user
   spring.datasource.password=your_db_password

   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true

   # JWT Configuration
   jwt.secret=your_jwt_secret_key
   jwt.expirationMs=3600000
   ```

3. **Build and run the project:**

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. **Access the server:**

   ```
   http://localhost:8080
   ```

---

## 🔐 Authentication

- All API requests require a valid JWT token in the `Authorization` header.
- Example Header:

   ```
   Authorization: Bearer <your-access-token>
   ```

---

## 📤 Upload API Documentation

| **Method** | **Endpoint**               | **Description**   | **Auth Required** |
|:-----------|:---------------------------|:------------------|:------------------|
| `POST`     | `/api/files/upload`        | Upload a file     | ✅ Yes            |

### Request Example (curl)

```bash
curl -X POST http://localhost:8080/api/files/upload \
-H "Authorization: Bearer <your-token-here>" \
-F "file=@/path/to/yourfile.png"
```

---

### Expected Responses

| **Status Code** | **Description**                     |
|:----------------|:------------------------------------|
| `200 OK`        | File uploaded successfully          |
| `400 Bad Request` | Invalid or missing request format |
| `401 Unauthorized` | Missing or invalid JWT token     |
| `415 Unsupported Media Type` | Unsupported file type |
| `500 Internal Server Error` | Unexpected server error |

---

## ⚡ Controller Example

```java
@RestController
@RequestMapping("/api/files")
public class FileController {

    @PostMapping(
        value = "/upload",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        // TODO: Implement file saving logic
        return ResponseEntity.ok("File uploaded successfully!");
    }
}
```

---

## 📝 Notes

- Ensure your client (e.g., curl, Postman) sends requests with `multipart/form-data`.
- Always include the `Authorization: Bearer` header in API requests.
- Consider adding global exception handlers for better error reporting.

---

## 🤝 Contributing

We welcome contributions! Feel free to open issues or submit pull requests to improve this project.

---

## 📄 License

This project is licensed under the [MIT License](LICENSE).

---

## 🌟 What's Next?

- Add support for file type validation and size limits.
- Implement cloud storage integration (e.g., AWS S3, Azure Blob).
- Enhance API documentation with Swagger/OpenAPI.
- Introduce unit and integration tests for better reliability.

---
