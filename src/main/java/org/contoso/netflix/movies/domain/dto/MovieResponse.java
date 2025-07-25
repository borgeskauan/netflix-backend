package org.contoso.netflix.movies.domain.dto;

import lombok.Builder;
import lombok.Data;
import lombok.With;
import org.contoso.netflix.movies.domain.entity.MovieDetails;
import org.contoso.netflix.movies.domain.entity.MovieListing;
import org.contoso.netflix.movies.domain.entity.UserMovieMetadata;

@Data
@With
@Builder
public class MovieResponse {
    private MovieListing movieListing;
    private MovieDetails movieDetails;

    @Builder.Default
    private UserMovieMetadata userMovieMetadata = UserMovieMetadata.builder().build();
}
