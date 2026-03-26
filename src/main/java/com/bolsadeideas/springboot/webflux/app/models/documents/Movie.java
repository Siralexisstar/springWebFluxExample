package com.bolsadeideas.springboot.webflux.app.models.documents;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "movies")
public class Movie {

    @Id
    private String id;

    private String title;

    private ArrayList<String> genres;

    private Integer runtime;

    private String rated;

    private Integer year;

    private ArrayList<String> directors;

    private ArrayList<String> cast;

    private String type;
}
