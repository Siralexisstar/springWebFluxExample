package com.bolsadeideas.springboot.webflux.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bolsadeideas.springboot.webflux.app.models.dao.MovieDao;
import com.bolsadeideas.springboot.webflux.app.models.documents.Movie;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class MovieController {

    @Autowired
    private MovieDao movieDao;

    @GetMapping("/listar")
    public String listar(Model model) {

        Flux<Movie> moviesFlux = movieDao.findAll();

        // esto le pasa la vista todas las movies por atraves del modelo, y la vista se
        // encarga de iterar sobre ellas
        model.addAttribute("movies", moviesFlux);
        model.addAttribute("titulo", "Listado de Películas");

        return "listar.html"; // el nombre de la vista, que se resuelve a listar.html
    }

    @GetMapping("/movies")
    public Mono<MovieDao> getMovies() {
        // suele llamar al repository
        return Mono.just(movieDao);
    }

}
