# 📂 Lifesync Backend - File Upload API

This is a Spring Boot project that provides a secure API endpoint for uploading files using `multipart/form-data`.  
The application uses JWT Authentication for securing endpoints and stores uploaded files (handling can be customized).

---

## 🚀 Features

- Upload files via REST API
- Secure upload endpoint using JWT token
- Proper error handling for unsupported content types
- Integrated with MySQL via Spring Data JPA
- Spring Boot 3.4.5
- Hibernate ORM (6.6.13.Final)
- HikariCP connection pooling

---

## 🛠️ Tech Stack

- Java 17
- Spring Boot
- Spring Web
- Spring Security (JWT Authentication)
- Spring Data JPA
- MySQL Database
- Maven

---

## 📦 Setup Instructions

1. **Clone the repository:**

   ```bash
   git clone https://github.com/your-username/lifesync-backend.git
   cd lifesync-backend
   ```

2. **Configure `application.properties` or `application.yml`:**

   ```properties
   server.port=8080

   spring.datasource.url=jdbc:mysql://localhost:3306/your_db_name
   spring.datasource.username=your_db_user
   spring.datasource.password=your_db_password

   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true

   # JWT secret and expiration
   jwt.secret=your_jwt_secret_key
   jwt.expirationMs=3600000
   ```

3. **Build and run the project:**

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. **Server will start at:**

   ```
   http://localhost:8080
   ```

---

## 🔐 Authentication

- All API requests must include a valid JWT Token in the header.
- Example Header:

   ```
   Authorization: Bearer <your-access-token>
   ```

---

## 📤 Upload API Documentation

| Method | URL                          | Description        | Auth Required |
|:-------|:------------------------------|:-------------------|:-------------|
| POST   | `/api/files/upload`            | Upload a file      | Yes          |

### Request Example (curl)

```bash
curl -X POST http://localhost:8080/api/files/upload \
-H "Authorization: Bearer <your-token-here>" \
-F "file=@/path/to/yourfile.png"
```

---

### Expected Responses

| Status Code | Description |
|:------------|:------------|
| `200 OK`    | File uploaded successfully |
| `400 Bad Request` | Request is not multipart/form-data |
| `401 Unauthorized` | Invalid or missing JWT token |
| `415 Unsupported Media Type` | Incorrect Content-Type |
| `500 Internal Server Error` | Server side error |

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
        // Handle file saving logic
        return ResponseEntity.ok("File uploaded successfully!");
    }
}
```

---

## 📝 Notes

- Ensure your client (curl/Postman) sends `multipart/form-data` requests.
- Always include the `Authorization: Bearer` header.
- Exception handlers should be added for better error reporting (optional).

---

## 🤝 Contributing

Feel free to open issues or submit pull requests if you'd like to contribute!

---

## 📄 License

This project is licensed under the [MIT License](LICENSE).

---
