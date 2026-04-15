package com.bolsadeideas.springboot.webflux.app;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.bolsadeideas.springboot.webflux.app.models.dao.CategoriaDao;
import com.bolsadeideas.springboot.webflux.app.models.documents.Category;
import com.bolsadeideas.springboot.webflux.app.models.documents.Movie;
import com.bolsadeideas.springboot.webflux.app.models.services.MovieService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@SpringBootApplication
@Slf4j
public class SpringBootWebfluxApplication implements CommandLineRunner {

	@Autowired
	private MovieService service;

	@Autowired
	private ReactiveMongoTemplate mongoTemplate;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebfluxApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Inserta los datos sin borrar la colección
		mongoTemplate.dropCollection("movies").subscribe();
		mongoTemplate.dropCollection("categories").subscribe();

		Category categoria = new Category()
				.builder()
				.name("Crime")
				.build();
		Category categoria2 = new Category()
				.builder()
				.name("Drama")
				.build();
		Category categoria3 = new Category()
				.builder()
				.name("Action")
				.build();

		Flux.just(categoria, categoria2, categoria3)
				.flatMap(service::saveCategory)
				.doOnNext(cat -> log.info("Inserted well category: " + cat.getName() + " with id: " + cat.getId()))
				.thenMany(
						Flux.just(
								Movie.builder()
										.title("PRUEBA CAMPERA")
										.genres(new ArrayList<>(Arrays.asList("Drama")))
										.runtime(142)
										.rated("R")
										.year(1994)
										.directors(new ArrayList<>(Arrays.asList("Frank Darabont")))
										.cast(new ArrayList<>(
												Arrays.asList("Tim Robbins", "Morgan Freeman", "Bob Gunton")))
										.type("movie")
										.category(categoria)
										.build(),
								Movie.builder()
										.title("The Godfather")
										.genres(new ArrayList<>(Arrays.asList("Crime", "Drama")))
										.runtime(175)
										.rated("R")
										.year(1972)
										.directors(new ArrayList<>(Arrays.asList("Francis Ford Coppola")))
										.cast(new ArrayList<>(
												Arrays.asList("Marlon Brando", "Al Pacino", "James Caan")))
										.type("movie")
										.category(categoria2)
										.build(),
								Movie.builder()
										.title("The Dark Knight")
										.genres(new ArrayList<>(Arrays.asList("Action", "Crime", "Drama")))
										.runtime(152)
										.rated("PG-13")
										.year(2008)
										.directors(new ArrayList<>(Arrays.asList("Christopher Nolan")))
										.cast(new ArrayList<>(
												Arrays.asList("Christian Bale", "Heath Ledger", "Aaron Eckhart")))
										.type("movie")
										.category(categoria3)
										.build())
								.flatMap(movie -> service.save(movie))
								.doOnNext(movie -> log.info("Inserted well movie: " + movie.getTitle() + " with id: " + movie.getId())))
				.subscribe();

	}

}
