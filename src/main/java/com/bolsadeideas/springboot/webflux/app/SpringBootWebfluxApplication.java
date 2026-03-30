package com.bolsadeideas.springboot.webflux.app;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.bolsadeideas.springboot.webflux.app.models.dao.MovieDao;
import com.bolsadeideas.springboot.webflux.app.models.documents.Movie;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@SpringBootApplication
@Slf4j
public class SpringBootWebfluxApplication implements CommandLineRunner {

	@Autowired
	private MovieDao movieDao;

	@Autowired
	private ReactiveMongoTemplate mongoTemplate;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebfluxApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Inserta los datos sin borrar la colección
		mongoTemplate.dropCollection("movies").subscribe();
		Flux.just(
				Movie.builder()
						.id("1")
						.title("PRUEBA CAMPERA")
						.genres(new ArrayList<>(Arrays.asList("Drama")))
						.runtime(142)
						.rated("R")
						.year(1994)
						.directors(new ArrayList<>(Arrays.asList("Frank Darabont")))
						.cast(new ArrayList<>(Arrays.asList("Tim Robbins", "Morgan Freeman", "Bob Gunton")))
						.type("movie")
						.build(),
				Movie.builder()
						.id("2")
						.title("The Godfather")
						.genres(new ArrayList<>(Arrays.asList("Crime", "Drama")))
						.runtime(175)
						.rated("R")
						.year(1972)
						.directors(new ArrayList<>(Arrays.asList("Francis Ford Coppola")))
						.cast(new ArrayList<>(Arrays.asList("Marlon Brando", "Al Pacino", "James Caan")))
						.type("movie")
						.build(),
				Movie.builder()
						.id("3")
						.title("The Dark Knight")
						.genres(new ArrayList<>(Arrays.asList("Action", "Crime", "Drama")))
						.runtime(152)
						.rated("PG-13")
						.year(2008)
						.directors(new ArrayList<>(Arrays.asList("Christopher Nolan")))
						.cast(new ArrayList<>(Arrays.asList("Christian Bale", "Heath Ledger", "Aaron Eckhart")))
						.type("movie")
						.build())
				.flatMap(movie -> movieDao.save(movie))
				.subscribe(movie -> log.info("Inserted well movie: " + movie.getTitle()));
	}

}
