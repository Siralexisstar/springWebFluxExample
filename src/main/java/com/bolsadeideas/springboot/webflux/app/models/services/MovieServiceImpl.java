package com.bolsadeideas.springboot.webflux.app.models.services;

import org.springframework.stereotype.Service;

import com.bolsadeideas.springboot.webflux.app.models.dao.MovieDao;
import com.bolsadeideas.springboot.webflux.app.models.documents.Movie;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieDao movieDao;

    @Override
    public Flux<Movie> findAll() {

        return movieDao.findAll();

    }

    @Override
    public Mono<Movie> findById(String id) {

        return movieDao.findById(id);
    }

    @Override
    public Mono<Movie> save(Movie movie) {

        return movieDao.save(movie);
    }

    @Override
    public Mono<Void> delete(Movie movie) {

        return movieDao.delete(movie);
    }

}
