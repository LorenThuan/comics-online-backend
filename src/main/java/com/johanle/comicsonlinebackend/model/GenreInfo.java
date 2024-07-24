package com.johanle.comicsonlinebackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GenreInfo {
    private int genreId;
    private String genre;

    public GenreInfo(int genreId, String genre) {
        this.genreId = genreId;
        this.genre = genre;
    }
}