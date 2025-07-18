package org.contoso.netflix.movies.adapter.output;

import org.contoso.netflix.movies.domain.dto.PageableResponse;
import org.contoso.netflix.movies.adapter.output.dto.TmdbMovieDetails;
import org.contoso.netflix.movies.adapter.output.dto.TmdbMovieListing;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "tmdbRestClient",
        url = "${external.tmdb-api.url}",
        configuration = TmdbFeignConfig.class
)
public interface TmdbRestClient {
    @GetMapping("/movie/popular")
    PageableResponse<TmdbMovieListing> getPopularMovies(@RequestParam("page") int page);

    @GetMapping("/search/movie")
    PageableResponse<TmdbMovieListing> searchMovies(@RequestParam("query") String query, @RequestParam("page") int page);

    @GetMapping("/movie/{movieId}")
    TmdbMovieDetails getMovieDetails(@PathVariable("movieId") String movieId);

    @GetMapping("/movie/{movieId}/recommendations")
    PageableResponse<TmdbMovieListing> getSimilarMovies(@PathVariable("movieId") String movieId, @RequestParam("page") int page);
}
