package com.bolsadeideas.springboot.webflux.app.models.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Component;

import com.bolsadeideas.springboot.webflux.app.models.documents.Movie;

@Component
public interface MovieDao extends ReactiveMongoRepository<Movie, String> {

}
