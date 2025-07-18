package org.contoso.netflix.movies.adapter.input;

import org.contoso.netflix.movies.domain.dto.PageableResponse;
import org.contoso.netflix.movies.domain.dto.MovieResponse;
import org.contoso.netflix.movies.port.input.MovieUseCase;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieUseCase movieUseCase;

    public MovieController(MovieUseCase movieUseCase) {
        this.movieUseCase = movieUseCase;
    }

    @GetMapping("/popular")
    public PageableResponse<MovieResponse> getPopularMovies(Pageable pageable) {
        return movieUseCase.getPopularMovies(pageable);
    }

    @GetMapping("/search")
    public PageableResponse<MovieResponse> searchMovies(@RequestParam String query, Pageable pageable) {
        return movieUseCase.searchMovies(query, pageable);
    }

    @GetMapping("/{id}")
    public MovieResponse getMovieDetails(@PathVariable String id) {
        return movieUseCase.getMovieDetails(id);
    }

    @GetMapping("/{id}/similar")
    public PageableResponse<MovieResponse> getSimilarMovies(@PathVariable String id, Pageable pageable) {
        return movieUseCase.getSimilarMovies(id, pageable);
    }
}
