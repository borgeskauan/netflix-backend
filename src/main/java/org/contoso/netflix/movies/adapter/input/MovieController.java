package org.contoso.netflix.movies.adapter.input;

import org.contoso.netflix.movies.domain.dto.MovieResponse;
import org.contoso.netflix.movies.domain.dto.PageableResponse;
import org.contoso.netflix.movies.port.input.MovieUseCase;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies/{userId}")
public class MovieController {

    private final MovieUseCase movieUseCase;

    public MovieController(MovieUseCase movieUseCase) {
        this.movieUseCase = movieUseCase;
    }

    @GetMapping("/popular")
    public PageableResponse<MovieResponse> getPopularMovies(@PathVariable String userId, Pageable pageable) {
        return movieUseCase.getPopularMovies(userId, pageable);
    }

    @GetMapping("/search")
    public PageableResponse<MovieResponse> searchMovies(@PathVariable String userId, @RequestParam("query") String query, Pageable pageable) {
        return movieUseCase.searchMovies(userId, query, pageable);
    }

    @GetMapping("/{id}")
    public MovieResponse getMovieDetails(@PathVariable String userId, @PathVariable String id) {
        return movieUseCase.getMovieDetails(userId, id);
    }

    @GetMapping("/{id}/similar")
    public PageableResponse<MovieResponse> getSimilarMovies(@PathVariable String userId, @PathVariable String id, Pageable pageable) {
        return movieUseCase.getSimilarMovies(userId, id, pageable);
    }
}
