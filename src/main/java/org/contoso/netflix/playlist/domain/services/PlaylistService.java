package org.contoso.netflix.playlist.domain.services;

import org.contoso.netflix.movies.port.input.MovieUseCase;
import org.contoso.netflix.playlist.adapter.input.PlaylistRequest;
import org.contoso.netflix.playlist.domain.dto.MoviePlaylistUpdateRequest;
import org.contoso.netflix.playlist.domain.entity.Playlist;
import org.contoso.netflix.playlist.ports.input.PlaylistUseCase;
import org.contoso.netflix.playlist.ports.output.PlaylistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistService implements PlaylistUseCase {

    private final PlaylistRepository playlistRepository;
    private final MovieUseCase movieUseCase;

    public PlaylistService(PlaylistRepository playlistRepository, MovieUseCase movieUseCase) {
        this.playlistRepository = playlistRepository;
        this.movieUseCase = movieUseCase;
    }

    @Override
    public List<Playlist> getPlaylists(String userId) {
        return playlistRepository.findPlaylistsByUserId(userId);
    }

    @Override
    public Playlist getPlaylistsById(String userId, String playlistId) {
        return playlistRepository.findPlaylistsByIdAndUserId(playlistId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Playlist not found for user: " + userId + " and playlistId: " + playlistId));
    }

    @Override
    public List<Playlist> getPlaylistsByMovie(String userId, String movieId) {
        return playlistRepository.findPlaylistsByMovieId(userId, movieId);
    }

    @Override
    public void updateMovieInPlaylists(String userId, String movieId, MoviePlaylistUpdateRequest request) {
        for (String playlistId : request.getPlaylistsToAdd()) {
            var playlist = playlistRepository.addMovieToPlaylist(userId, playlistId, movieId);
            updatePlaylistCover(playlist, playlist.getMovieIds().getLast());
        }

        for (String playlistId : request.getPlaylistsToRemove()) {
            var playlist = playlistRepository.removeMovieFromPlaylist(userId, playlistId, movieId);

            // If the movie was the last one in the playlist, we can remove the cover image
            if (playlist.getMovieIds().isEmpty()) {
                playlist.setCoverImageUrl(null);
                playlistRepository.save(playlist);
            } else if (playlist.getMovieIds().size() == 1) {
                // If there's only one movie left, update the cover image to that movie
                updatePlaylistCover(playlist, playlist.getMovieIds().getLast());
            }
        }
    }

    @Override
    public Playlist createPlaylist(String userId, PlaylistRequest request) {
        // Validate request
        if (request.getName() == null || request.getName().isEmpty()) {
            throw new IllegalArgumentException("Playlist name cannot be empty");
        }

        String coverImageUrl = "";
        if (request.getCoverImageUrl() != null) {
            coverImageUrl = request.getCoverImageUrl();

        } else if (request.getMovieIdsToAdd() != null && !request.getMovieIdsToAdd().isEmpty()) {
            // Fetch cover image from the first movie in the list
            coverImageUrl = fetchMovieCoverImage(request.getMovieIdsToAdd().getLast());
        }

        // Create and save the playlist
        Playlist newPlaylist = Playlist.builder()
                .userId(userId)
                .movieIds(request.getMovieIdsToAdd())
                .name(request.getName())
                .description(request.getDescription())
                .coverImageUrl(coverImageUrl)
                .build();

        return playlistRepository.save(newPlaylist);
    }

    @Override
    public void createSystemPlaylistsForUser(String userId) {
        List<Playlist> systemPlaylists = buildSystemPlaylists();
        for (Playlist playlist : systemPlaylists) {
            playlist.setUserId(userId);
            playlistRepository.save(playlist);
        }
    }

    private void updatePlaylistCover(Playlist playlist, String movieId) {
        var movieCoverImage = fetchMovieCoverImage(movieId);
        if (movieCoverImage != null && !movieCoverImage.isEmpty()) {
            playlist.setCoverImageUrl(movieCoverImage);
            playlistRepository.save(playlist);
            return;
        }

        throw new IllegalArgumentException("Movie cover image not found for movieId: " + movieId);
    }

    private String fetchMovieCoverImage(String movieId) {
        var movie = movieUseCase.getMovieDetails(movieId);
        return movie.getMovieListing().getPosterPath();
    }

    private List<Playlist> buildSystemPlaylists() {
        return List.of(
                Playlist.builder()
                        .name("Favorites")
                        .description("Your favorite movies")
                        .systemPlaylistId("favorites")
                        .build(),
                Playlist.builder()
                        .name("Watch Later")
                        .description("Movies you want to watch later")
                        .systemPlaylistId("watch_later")
                        .build(),
                Playlist.builder()
                        .name("Watched")
                        .description("Movies you have watched")
                        .systemPlaylistId("watched")
                        .build()
        );
    }
}
