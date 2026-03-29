package com.bolsadeideas.springboot.webflux.app.controllers;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.spring6.context.webflux.ReactiveDataDriverContextVariable;

import com.bolsadeideas.springboot.webflux.app.models.dao.MovieDao;
import com.bolsadeideas.springboot.webflux.app.models.documents.Movie;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Controller
public class MovieController {

    @Autowired
    private MovieDao movieDao;

    @GetMapping("/listar-full")
    public String listar(Model model) {

        Flux<Movie> moviesFlux = movieDao.findAll()
                .map(movie -> {
                    movie.setTitle(movie.getTitle().toUpperCase());
                    return movie;
                }).repeat(5000); // esto hace que se repita el flujo 5000 veces, para simular una gran cantidad
                                 // de datos

        // esto le pasa la vista todas las movies por atraves del modelo, y la vista se
        // encarga de iterar sobre ellas
        model.addAttribute("movies", moviesFlux);
        model.addAttribute("titulo", "Listado de Películas");

        return "listar.html"; // el nombre de la vista, que se resuelve a listar.html
    }

    @GetMapping("/listar-datadriver")
    public String listarDataDriver(Model model) {

        Flux<Movie> moviesFlux = movieDao.findAll()
                .map(movie -> {
                    movie.setTitle(movie.getTitle().toUpperCase());
                    return movie;
                }).delayElements(Duration.ofSeconds(1));

        moviesFlux.subscribe(movies -> log.info(movies.getTitle()));

        // esto sirve para que la vista se vaya renderizando a medida que va recibiendo
        // los datos, en lugar de esperar a recibir todos los datos para renderizar la
        // vista, el segundo parámetro es el tamaño del buffer, es decir, el número de
        // elementos que se van a renderizar a la vez
        model.addAttribute("movies", new ReactiveDataDriverContextVariable(moviesFlux, 2));
        model.addAttribute("titulo", "Listado de Películas");

        return "listar.html";
    }

    @GetMapping("/movies")
    public Mono<MovieDao> getMovies() {
        // suele llamar al repository
        return Mono.just(movieDao);
    }

}
