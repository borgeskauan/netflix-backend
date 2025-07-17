package org.contoso.netflix.movies.port.input;

import org.contoso.netflix.movies.domain.dto.MovieResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MovieUseCase {
    List<MovieResponse> getPopularMovies(Pageable pageable);

    List<MovieResponse> searchMovies(String query, Pageable pageable);

    MovieResponse getMovieDetails(String movieId);

    List<MovieResponse> getSimilarMovies(String movieId, Pageable pageable);
}
