package org.contoso.netflix.movies.port.output;

import org.contoso.netflix.movies.domain.dto.MovieResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MovieRepository {
    List<MovieResponse> findPopularMovies(Pageable pageable);

    List<MovieResponse> searchMovies(String query, Pageable pageable);

    MovieResponse findMovieDetails(String movieId);

    List<MovieResponse> findSimilarMovies(String movieId, Pageable pageable);
}
