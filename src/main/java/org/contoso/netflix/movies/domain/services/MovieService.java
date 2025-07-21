package org.contoso.netflix.movies.domain.services;

import org.contoso.netflix.movies.domain.dto.MovieResponse;
import org.contoso.netflix.movies.domain.dto.PageableResponse;
import org.contoso.netflix.movies.domain.entity.UserMovieMetadata;
import org.contoso.netflix.movies.port.input.MovieUseCase;
import org.contoso.netflix.movies.port.output.MovieRepository;
import org.contoso.netflix.playlist.domain.entity.Playlist;
import org.contoso.netflix.playlist.domain.entity.SystemPlaylist;
import org.contoso.netflix.playlist.ports.output.PlaylistRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MovieService implements MovieUseCase {

    private final MovieRepository movieRepository;
    private final PlaylistRepository playlistRepository;

    public MovieService(MovieRepository movieRepository, PlaylistRepository playlistRepository) {
        this.movieRepository = movieRepository;
        this.playlistRepository = playlistRepository;
    }

    @Override
    public PageableResponse<MovieResponse> getPopularMovies(String userId, Pageable pageable) {
        var movies = movieRepository.findPopularMovies(pageable);
        return enrichMoviesWithUserMetadata(userId, movies);
    }

    @Override
    public PageableResponse<MovieResponse> searchMovies(String query, String userId, Pageable pageable) {
        var movies = movieRepository.searchMovies(query, pageable);
        return enrichMoviesWithUserMetadata(userId, movies);
    }

    @Override
    public MovieResponse getMovieDetails(String userId, String movieId) {
        var movie = movieRepository.findMovieDetails(movieId);
        return enrichMovieWithUserMetadata(userId, movie);
    }

    @Override
    public PageableResponse<MovieResponse> getSimilarMovies(String userId, String movieId, Pageable pageable) {
        var movies = movieRepository.findSimilarMovies(movieId, pageable);
        return enrichMoviesWithUserMetadata(userId, movies);
    }

    private PageableResponse<MovieResponse> enrichMoviesWithUserMetadata(String userId, PageableResponse<MovieResponse> movies) {
        var userMetadataMap = buildUserMetadataMap(userId);
        return movies.map(movieResponse -> enrichMovieWithMetadata(movieResponse, userMetadataMap));
    }

    private MovieResponse enrichMovieWithUserMetadata(String userId, MovieResponse movie) {
        var userMetadataMap = buildUserMetadataMap(userId);
        return enrichMovieWithMetadata(movie, userMetadataMap);
    }

    private Map<String, UserMovieMetadata> buildUserMetadataMap(String userId) {
        var systemPlaylists = playlistRepository.findSystemPlaylistsByUserId(userId);

        var favoriteIds = getMovieIds(systemPlaylists, SystemPlaylist.FAVORITES);
        var watchedIds = getMovieIds(systemPlaylists, SystemPlaylist.WATCHED);
        var watchLaterIds = getMovieIds(systemPlaylists, SystemPlaylist.WATCH_LATER);

        return systemPlaylists.stream()
                .flatMap(playlist -> playlist.getMovieIds().stream())
                .distinct()
                .collect(Collectors.toMap(
                        movieId -> movieId,
                        movieId -> UserMovieMetadata.builder()
                                .isFavorite(favoriteIds.contains(movieId))
                                .isWatched(watchedIds.contains(movieId))
                                .isWatchLater(watchLaterIds.contains(movieId))
                                .build()
                ));
    }

    private MovieResponse enrichMovieWithMetadata(MovieResponse movie, Map<String, UserMovieMetadata> userMetadataMap) {
        var metadata = userMetadataMap.getOrDefault(movie.getMovieListing().getId(), UserMovieMetadata.builder().build());
        return movie.withUserMovieMetadata(metadata);
    }

    private static List<String> getMovieIds(List<Playlist> systemPlaylists, SystemPlaylist playlistId) {
        return systemPlaylists.stream()
                .filter(playlist -> playlist.getSystemPlaylistId().equals(playlistId.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("System playlist with ID: " + playlistId + " not found."))
                .getMovieIds();
    }
}