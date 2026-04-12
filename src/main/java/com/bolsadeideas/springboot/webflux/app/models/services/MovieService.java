package com.bolsadeideas.springboot.webflux.app.models.services;

import com.bolsadeideas.springboot.webflux.app.models.documents.Category;
import com.bolsadeideas.springboot.webflux.app.models.documents.Movie;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovieService {

    // here we can add more methods to the service layer, for example, to find
    // movies by genre, by director, etc.

    Flux<Movie> findAll();

    Flux<Movie> findAllByTitleUpperCase();

    Mono<Movie> findById(String id);

    Mono<Movie> save(Movie movie);

    Mono<Void> delete(Movie movie);

    Flux<Category> findAllCategories();

    Mono<Category> findCategoryById(String id);

    Mono<Category> saveCategory(Category category);

}
