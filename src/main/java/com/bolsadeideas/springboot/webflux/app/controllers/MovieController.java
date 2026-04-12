package com.bolsadeideas.springboot.webflux.app.controllers;

import java.time.Duration;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.thymeleaf.spring6.context.webflux.ReactiveDataDriverContextVariable;

import com.bolsadeideas.springboot.webflux.app.models.documents.Movie;
import com.bolsadeideas.springboot.webflux.app.models.services.MovieServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@SessionAttributes("movie") // esto es para que el objeto movie se mantenga en la sesión, y no se pierda al
                            // hacer un redirect, por ejemplo, al guardar una película, después de
                            // guardarla, se hace un redirect a listar, y si no se mantiene en la sesión, se
                            // pierde el objeto movie, y no se puede mostrar un mensaje de éxito o error en
                            // la vista listar, porque el objeto movie ya no está disponible
@Controller
@RequiredArgsConstructor
public class MovieController {

    private final MovieServiceImpl movieSerImpl;

    // Este endpoint me puede ayudar para la parte de front end de Adjustments-UI
    @GetMapping("/listar-full")
    public String listarFull(Model model) {

        Flux<Movie> moviesFlux = movieSerImpl
                .findAllByTitleUpperCase()
                .repeat(5000); // esto hace que se repita el flujo 5000 veces, para simular una gran cantidad
                               // de datos

        // esto le pasa la vista todas las movies por atraves del modelo, y la vista se
        // encarga de iterar sobre ellas
        model.addAttribute("movies", moviesFlux);
        model.addAttribute("titulo", "Listado de Películas");

        return "listar.html"; // el nombre de la vista, que se resuelve a listar.html
    }

    @GetMapping("/listar-chunksize")
    public String listarChunkSize(Model model) {

        Flux<Movie> moviesFlux = movieSerImpl
                .findAllByTitleUpperCase()
                .repeat(5000); // esto hace que se repita el flujo 5000 veces, para simular una gran cantidad
                               // de datos

        // esto le pasa la vista todas las movies por atraves del modelo, y la vista se
        // encarga de iterar sobre ellas
        model.addAttribute("movies", moviesFlux);
        model.addAttribute("titulo", "Listado de Películas");

        return "listar-chunked.html"; // el nombre de la vista, que se resuelve a listar.html
    }

    @GetMapping("/listar-datadriver")
    public String listarDataDriver(Model model) {

        Flux<Movie> moviesFlux = movieSerImpl
                .findAllByTitleUpperCase()
                .delayElements(Duration.ofSeconds(1));

        moviesFlux.subscribe(movies -> log.info(movies.getTitle()));

        // esto sirve para que la vista se vaya renderizando a medida que va recibiendo
        // los datos, en lugar de esperar a recibir todos los datos para renderizar la
        // vista, el segundo parámetro es el tamaño del buffer, es decir, el número de
        // elementos que se van a renderizar a la vez
        model.addAttribute("movies", new ReactiveDataDriverContextVariable(moviesFlux, 2));
        model.addAttribute("titulo", "Listado de Películas");

        return "listar.html";
    }

    @GetMapping("/listar")
    public String listar(Model model) {
        Flux<Movie> moviesFlux = movieSerImpl
                .findAll();

        model.addAttribute("movies", moviesFlux);
        model.addAttribute("titulo", "Listado de Películas");

        return "listar.html";
    }

    // Crear nuevo registro pero con un endpoint REST REACTIVO
    @GetMapping("/crear")
    public Mono<String> ceateMovie(Model model) {

        // Creamos formulario de pelicula
        model.addAttribute("movie", new Movie());
        model.addAttribute("title", "Crear Pelicula");

        return Mono.just("form.html");
    }

    @GetMapping("form/{id}")
    public Mono<String> findById(@PathVariable String id, Model model) {
        return movieSerImpl.findById(id)
                .doOnNext(movie -> log.info("Movie found: " + movie.getTitle() + " with id: " + movie.getId()))
                .flatMap(movie -> {
                    model.addAttribute("movie", movie);
                    model.addAttribute("title", "Editar Pelicula");
                    return Mono.just("form.html");
                })
                .switchIfEmpty(Mono.just("redirect:/listar?error=this+movie+actually+does+not+exist"))
                .onErrorResume(ex -> {
                    log.error("Error occurred while finding movie with id: " + id, ex);
                    return Mono.just("redirect:/listar?error=FATAL+ERROR+OCCURRED+WHILE+FINDING+MOVIE");
                });
    }

    @PostMapping("/form")
    public Mono<String> saveMovie(@Valid @ModelAttribute Movie movie, BindingResult result, SessionStatus status,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("movie", movie);
            model.addAttribute("title", "Errores en el formulario");
            log.error("Validation errors occurred while saving movie: " + result.getAllErrors());
            return Mono.just("form.html");
        } else {
            status.setComplete();
        }

        return movieSerImpl.save(movie)
                .doOnNext(data -> {
                    log.info("Movie saved: " + data.getTitle() + " with id: " + data.getId());
                })
                .thenReturn("redirect:/listar?success=Movie+saved+successfully");
    }   

    @GetMapping("/eliminar/{id}")
    public Mono<String> deleteMovie(@PathVariable String id) {
        return movieSerImpl.findById(id)
                .flatMap(p -> {
                    return movieSerImpl.delete(p)
                            .then(Mono.just("redirect:/listar?success=Movie+deleted+successfully"));
                });
    }

    

}
