package org.contoso.netflix.movies.domain.services;

import org.contoso.netflix.movies.domain.dto.MovieResponse;
import org.contoso.netflix.movies.port.input.MovieUseCase;
import org.contoso.netflix.movies.port.output.MovieRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService implements MovieUseCase {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public List<MovieResponse> getPopularMovies(Pageable pageable) {
        return movieRepository.findPopularMovies(pageable);
    }

    @Override
    public List<MovieResponse> searchMovies(String query, Pageable pageable) {
        return movieRepository.searchMovies(query, pageable);
    }

    @Override
    public MovieResponse getMovieDetails(String movieId) {
        return movieRepository.findMovieDetails(movieId);
    }

    @Override
    public List<MovieResponse> getSimilarMovies(String movieId, Pageable pageable) {
        return movieRepository.findSimilarMovies(movieId, pageable);
    }
}
