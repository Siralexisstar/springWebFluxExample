package com.bolsadeideas.springboot.webflux.app.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bolsadeideas.springboot.webflux.app.models.dao.MovieDao;
import com.bolsadeideas.springboot.webflux.app.models.documents.Movie;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieRestController {

    private final MovieDao movieDao;

    // Esto es un endpoint REST REACTIVO
    @GetMapping("/listar")
    public Flux<Movie> findAllMovies() {

        Flux<Movie> moviesFlux = movieDao.findAll()
                .map(movie -> {
                    movie.setTitle(movie.getTitle().toUpperCase());
                    return movie;
                })
                .doOnNext(data -> log.info("Esta pelicula tiene el titulo: " + data.getTitle()));

        return moviesFlux;
    }

    @GetMapping("/{id}")
    public Mono<Movie> findMovieById(@PathVariable String id) {

        Mono<Movie> monoByFlux = movieDao.findAll()
                .filter(allMovies -> allMovies.getId().equals(id))
                .next();

        return monoByFlux;
    }

}
