package org.contoso.netflix.movies.adapter.output;

import org.contoso.netflix.movies.adapter.output.dto.TmdbMovieDetails;
import org.contoso.netflix.movies.adapter.output.dto.TmdbMovieListing;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(
        name = "tmdbRestClient",
        url = "${external.tmdb-api.url}"
)
public interface TmdbRestClient {
    @GetMapping("/movie/popular")
    List<TmdbMovieListing> getPopularMovies(@RequestParam("page") int page);

    @GetMapping("/search/movie")
    List<TmdbMovieListing> searchMovies(@RequestParam("query") String query, @RequestParam("page") int page);

    @GetMapping("/movie/{movieId}")
    TmdbMovieDetails getMovieDetails(@PathVariable("movieId") String movieId);

    @GetMapping("/movie/{movieId}/recommendations")
    List<TmdbMovieListing> getSimilarMovies(@PathVariable("movieId") String movieId, @RequestParam("page") int page);
}
