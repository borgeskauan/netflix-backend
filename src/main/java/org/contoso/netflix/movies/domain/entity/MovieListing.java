package org.contoso.netflix.movies.domain.entity;

import lombok.Builder;
import lombok.Data;
import lombok.With;

import java.util.List;

@Data
@Builder
@With
public class MovieListing {
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