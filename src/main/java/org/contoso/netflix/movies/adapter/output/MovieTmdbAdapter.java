package org.contoso.netflix.movies.adapter.output;

import org.contoso.netflix.movies.domain.dto.MovieResponse;
import org.contoso.netflix.movies.port.output.MovieRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MovieTmdbAdapter implements MovieRepository {

    private final TmdbRestClient tmdbRestClient;

    public MovieTmdbAdapter(TmdbRestClient tmdbRestClient) {
        this.tmdbRestClient = tmdbRestClient;
    }

    @Override
    public List<MovieResponse> findPopularMovies(Pageable pageable) {
        return List.of();
    }

    @Override
    public List<MovieResponse> searchMovies(String query, Pageable pageable) {
        return List.of();
    }

    @Override
    public MovieResponse findMovieDetails(String movieId) {
        return null;
    }

    @Override
    public List<MovieResponse> findSimilarMovies(String movieId, Pageable pageable) {
        return List.of();
    }
}
