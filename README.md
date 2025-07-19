# Taski Backend API

This repository contains the backend API for **Taski**, a task management application built with Java and Spring Boot. Taski allows users to efficiently manage their daily activities through an intuitive and secure task-tracking system.

## Key Features

- RESTful API for full task lifecycle management (create, update, delete, complete)
- User authentication and authorization using JWT (JSON Web Tokens)
- Password encryption using BCrypt
- Email handling with Mailtrap (to be replaced with a production-ready solution)
- Organized project structure following Spring Boot best practices
- JSON-based request and response format
- Centralized database access layer with MySQL

## Technologies Used

- Java 17+
- Spring Boot
- Spring Security
- JWT
- BCrypt
- Mailtrap
- MySQL
- Maven

## Environment Configuration

Environment-specific values (e.g., JWT secrets, email credentials, database config) will be managed through a `.env` or `application.properties` file. These files are not currently included in the repository and will be configured later during deployment.

## Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/RodriguezErick/taski-backend.git
   cd taski-backend
   
2. Import the project into your favorite Java IDE (e.g., IntelliJ, Eclipse).
3. Configure your database in application.properties.
4. Build the project:
   ```bash
      mvn clean install

5. Run the application:
   ```bash
   mvn spring-boot:run

## Disclaimer

**This is a practical project developed for learning and portfolio purposes only.
The author (Eduardo Rodríguez) assumes no responsibility for any misuse, loss, or damage resulting from the use of this code.**

## License

This project is licensed under the [MIT License](LICENSE).