package org.contoso.netflix.movies.port.output;

import org.contoso.netflix.movies.domain.dto.MovieResponse;
import org.contoso.netflix.movies.domain.dto.PageableResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MovieRepository {
    PageableResponse<MovieResponse> findPopularMovies(Pageable pageable);

    PageableResponse<MovieResponse> searchMovies(String query, Pageable pageable);

    MovieResponse findMovieDetails(String movieId);

    PageableResponse<MovieResponse> findSimilarMovies(String movieId, Pageable pageable);
}
