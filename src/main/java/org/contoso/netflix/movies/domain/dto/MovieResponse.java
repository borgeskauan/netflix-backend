package org.contoso.netflix.movies.domain.dto;

import lombok.Builder;
import lombok.Data;
import org.contoso.netflix.movies.domain.entity.MovieDetails;
import org.contoso.netflix.movies.domain.entity.MovieListing;
import org.contoso.netflix.movies.domain.entity.UserMovieMetadata;

@Data
@Builder
public class MovieResponse {
    private MovieListing movieListing;
    private MovieDetails movieDetails;
    private UserMovieMetadata userMovieMetadata;
}
