package org.contoso.netflix.movies.adapter.output.dto;

import lombok.Data;

import java.util.List;

@Data
public class TmdbMovieListing {
    private String id;
    private String title;
    private String posterPath;
    private String overview;
    private String releaseDate;
    private double voteAverage;
    private List<Genre> genres;

    @Data
    public static class Genre {
        private int id;
        private String name;
    }
}