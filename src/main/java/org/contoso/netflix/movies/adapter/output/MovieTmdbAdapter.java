package org.contoso.netflix.movies.adapter.output;

import org.contoso.netflix.movies.adapter.output.dto.TmdbMovieDetails;
import org.contoso.netflix.movies.adapter.output.dto.TmdbMovieListing;
import org.contoso.netflix.movies.adapter.output.dto.TmdbMovieMapper;
import org.contoso.netflix.movies.adapter.output.dto.TmdbPageableResponse;
import org.contoso.netflix.movies.domain.dto.MovieResponse;
import org.contoso.netflix.movies.domain.dto.PageableResponse;
import org.contoso.netflix.movies.domain.entity.MovieDetails;
import org.contoso.netflix.movies.domain.entity.MovieListing;
import org.contoso.netflix.movies.port.output.MovieRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.function.Supplier;

@Repository
public class MovieTmdbAdapter implements MovieRepository {

    private final TmdbRestClient tmdbRestClient;
    private final TmdbMovieMapper tmdbMovieMapper;

    public MovieTmdbAdapter(TmdbRestClient tmdbRestClient, TmdbMovieMapper tmdbMovieMapper) {
        this.tmdbRestClient = tmdbRestClient;
        this.tmdbMovieMapper = tmdbMovieMapper;
    }

    @Override
    public PageableResponse<MovieResponse> findPopularMovies(Pageable pageable) {
        return fetchAndMapMovies(() -> tmdbRestClient.getPopularMovies(getPageNumber(pageable)));
    }

    @Override
    public PageableResponse<MovieResponse> searchMovies(String query, Pageable pageable) {
        return fetchAndMapMovies(() -> tmdbRestClient.searchMovies(query, getPageNumber(pageable)));
    }

    @Override
    public MovieResponse findMovieDetails(String movieId) {
        TmdbMovieDetails tmdbMovieDetails = tmdbRestClient.getMovieDetails(movieId);
        return mapDetailsToMovieResponse(tmdbMovieDetails);
    }

    @Override
    public PageableResponse<MovieResponse> findSimilarMovies(String movieId, Pageable pageable) {
        return fetchAndMapMovies(() -> tmdbRestClient.getSimilarMovies(movieId, getPageNumber(pageable)));
    }

    private int getPageNumber(Pageable pageable) {
        return pageable.getPageNumber() + 1; // TMDB API uses 1-based indexing
    }

    private PageableResponse<MovieResponse> fetchAndMapMovies(Supplier<TmdbPageableResponse<TmdbMovieListing>> fetcher) {
        TmdbPageableResponse<TmdbMovieListing> tmdbMovies = fetcher.get();
        List<MovieResponse> movieResponses = tmdbMovies.getResults().stream()
                .map((this::toMovieListing))
                .map(movieListing -> MovieResponse.builder()
                        .movieListing(movieListing)
                        .build())
                .toList();

        return PageableResponse.<MovieResponse>builder()
                .page(tmdbMovies.getPage() - 1) // TMDB API uses 1-based indexing, convert to 0-based for consistency
                .totalPages(tmdbMovies.getTotalPages())
                .totalResults(tmdbMovies.getTotalResults())
                .results(movieResponses)
                .build();
    }

    private MovieResponse mapDetailsToMovieResponse(TmdbMovieDetails tmdbMovieDetails) {
        String completePosterPath = buildCompletePosterPath("original", tmdbMovieDetails.getPosterPath());

        MovieListing movieListing = tmdbMovieMapper.toMovieListing(tmdbMovieDetails).withPosterPath(completePosterPath);
        MovieDetails movieDetails = tmdbMovieMapper.toMovieDetails(tmdbMovieDetails);
        return MovieResponse.builder()
                .movieListing(movieListing)
                .movieDetails(movieDetails)
                .build();
    }

    private MovieListing toMovieListing(TmdbMovieListing tmdbMovieListing) {
        var movieListing = tmdbMovieMapper.toMovieListing(tmdbMovieListing);

        String completePosterPath = buildCompletePosterPath("w500", tmdbMovieListing.getPosterPath());
        return movieListing.withPosterPath(completePosterPath);
    }

    private static String buildCompletePosterPath(String resolution, String partialPosterPath) {
        if (partialPosterPath == null || partialPosterPath.isEmpty()) {
            return null;
        }

        return "https://image.tmdb.org/t/p/" + resolution + partialPosterPath;
    }
}