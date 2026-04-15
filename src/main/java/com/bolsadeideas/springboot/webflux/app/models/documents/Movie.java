package com.bolsadeideas.springboot.webflux.app.models.documents;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "movies")
public class Movie {

    @Id
    private String id;

    @NotBlank(message = "El título no puede estar vacío")
    private String title;

    @NotEmpty(message = "Debe especificar al menos un género")
    private ArrayList<String> genres;

    private Integer runtime;

    @NotBlank(message = "La calificación no puede estar vacía")
    private String rated;

    @NotNull(message = "El año no puede ser nulo")
    @Min(value = 1888, message = "El año debe ser mayor o igual a 1888")
    private Integer year;

    @NotEmpty(message = "Debe especificar al menos un director")
    private ArrayList<String> directors;

    @NotEmpty(message = "Debe especificar al menos un actor")
    private ArrayList<String> cast;

    private String type;

    private Category category;


}
