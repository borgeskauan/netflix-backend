package org.contoso.netflix.movies.port.input;

import org.contoso.netflix.movies.domain.dto.MovieResponse;
import org.contoso.netflix.movies.domain.dto.PageableResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MovieUseCase {
    PageableResponse<MovieResponse> getPopularMovies(Pageable pageable);

    PageableResponse<MovieResponse> searchMovies(String query, Pageable pageable);

    MovieResponse getMovieDetails(String movieId);

    PageableResponse<MovieResponse> getSimilarMovies(String movieId, Pageable pageable);
}
