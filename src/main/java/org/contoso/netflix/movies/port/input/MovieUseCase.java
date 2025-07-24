package org.contoso.netflix.movies.port.input;

import org.contoso.netflix.movies.domain.dto.MovieResponse;
import org.contoso.netflix.movies.domain.dto.PageableResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MovieUseCase {
    PageableResponse<MovieResponse> getPopularMovies(String userId, Pageable pageable);

    PageableResponse<MovieResponse> searchMovies(String userId, String query, Pageable pageable);

    MovieResponse getMovieDetails(String userId, String movieId);

    PageableResponse<MovieResponse> getSimilarMovies(String userId, String movieId, Pageable pageable);
}
