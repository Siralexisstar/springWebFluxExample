

# Spring Boot WebFlux Example

This project is a sample application to study and demonstrate the use of Spring WebFlux, the reactive stack of the Spring Framework. It includes examples of reactive REST APIs, MongoDB integration, and Thymeleaf views for managing movies.



## Features

- Reactive programming with Spring WebFlux
- Reactive integration with MongoDB
- REST endpoints for movie management
- Thymeleaf forms and views to create and list movies
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
   - Make sure MongoDB is running on `localhost:27017` (or update the configuration in `src/main/resources/application.yml`).
3. **Build the project:**
   ```bash
   ./mvnw clean package
   ```
4. **Run the application:**
   ```bash
   ./mvnw spring-boot:run
   ```

The application will be available at `http://localhost:8080`.

## Main Endpoints

- `/listar` — List all movies (Thymeleaf view)
- `/form` — Form to create or edit a movie
- `/api/movies/listar` — REST endpoint to get all movies (JSON)

## Using the Form

The form allows you to create or edit movies. The fields for directors, genres, and cast should be entered as comma-separated values. Example:

```
Directors: Director 1, Director 2
Genres: Action, Comedy
Cast: Actor 1, Actor 2, Actor 3
```

The category is selected from a dropdown list loaded from the database.


## Purpose

This project is intended as a learning resource for:
- Understanding the basics of reactive programming in Spring
- Building non-blocking REST APIs
- Integrating MongoDB reactively
- Using Thymeleaf with reactive data

Feel free to modify and experiment with the code to deepen your understanding of Spring WebFlux.



## License

This project is for educational purposes only.
