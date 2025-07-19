package org.contoso.netflix.movies.adapter.output.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TmdbMovieListing {
    private String id;
    private String title;

    @JsonProperty("poster_path")
    private String posterPath;

    private String overview;

    @JsonProperty("release_date")
    private String releaseDate;

    @JsonProperty("vote_average")
    private double voteAverage;

    private List<Genre> genres;

    @Data
    public static class Genre {
        private int id;
        private String name;
    }
}