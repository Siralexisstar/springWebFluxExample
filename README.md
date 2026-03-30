# Spring Boot WebFlux Example

This project is an example application to study and demonstrate how to use and manage Spring WebFlux, the reactive stack of the Spring Framework. It is designed for learning purposes and showcases the basics of building a reactive REST API and web application with Spring Boot and MongoDB.

## Features

- Reactive programming with Spring WebFlux
- Reactive MongoDB integration
- Example REST endpoints for managing movies
- Thymeleaf template for listing movies
- Project structure following best practices

## Technologies Used

- Java 17
- Spring Boot 4.x
- Spring WebFlux
- Spring Data MongoDB Reactive
- Thymeleaf
- Maven
- MongoDB

## Getting Started

1. **Clone the repository:**
   ```bash
   git clone https://github.com/Siralexisstar/springWebFluxExample.git
   ```
2. **Configure MongoDB:**
   - Ensure MongoDB is running on `localhost:27017` (or update the configuration in `application.yml`).
3. **Build the project:**
   ```bash
   ./mvnw clean package
   ```
4. **Run the application:**
   ```bash
   ./mvnw spring-boot:run
   ```
   
## Purpose

This project is intended as a learning resource for:
- Understanding the basics of reactive programming in Spring
- Building non-blocking REST APIs
- Integrating with MongoDB reactively
- Using Thymeleaf with reactive data

Feel free to fork, modify, and experiment with the code to deepen your understanding of Spring WebFlux!

## License

This project is for educational purposes.
