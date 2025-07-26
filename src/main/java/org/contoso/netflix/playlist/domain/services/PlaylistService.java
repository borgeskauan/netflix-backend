package org.contoso.netflix.playlist.domain.services;

import lombok.extern.slf4j.Slf4j;
import org.contoso.netflix.movies.port.output.MovieRepository;
import org.contoso.netflix.plans.domain.exception.PlanFeatureNotAvailableException;
import org.contoso.netflix.plans.domain.services.PlanService;
import org.contoso.netflix.playlist.adapter.input.PlaylistRequest;
import org.contoso.netflix.playlist.domain.dto.MoviePlaylistUpdateRequest;
import org.contoso.netflix.playlist.domain.entity.Playlist;
import org.contoso.netflix.playlist.domain.entity.SystemPlaylist;
import org.contoso.netflix.playlist.domain.exception.InvalidPlaylistRequestException;
import org.contoso.netflix.playlist.domain.exception.PlaylistNotFoundException;
import org.contoso.netflix.playlist.port.input.PlaylistUseCase;
import org.contoso.netflix.playlist.port.output.PlaylistRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PlaylistService implements PlaylistUseCase {

    private final PlaylistRepository playlistRepository;
    private final MovieRepository movieRepository;
    private final PlanService planService;

    public PlaylistService(PlaylistRepository playlistRepository, MovieRepository movieRepository, PlanService planService) {
        this.playlistRepository = playlistRepository;
        this.movieRepository = movieRepository;
        this.planService = planService;
    }

    @Override
    public List<Playlist> getPlaylists(String userId) {
        return playlistRepository.findPlaylistsByUserId(userId);
    }

    @Override
    public Playlist getPlaylistsById(String userId, String playlistId) {
        return playlistRepository.findPlaylistsByUserIdAndId(userId, playlistId)
                .orElseThrow(() -> new PlaylistNotFoundException("Playlist not found for user: " + userId + " and playlistId: " + playlistId));
    }

    @Override
    public void updateMovieInPlaylists(String userId, String movieId, MoviePlaylistUpdateRequest request) {
        Map<String, String> systemPlaylistsMap = buildSystemPlaylistsMap(userId);

        for (String playlistId : request.getPlaylistsToAdd()) {
            if (systemPlaylistsMap.containsKey(playlistId)) {
                playlistId = systemPlaylistsMap.get(playlistId);
            }

            var playlist = playlistRepository.addMovieToPlaylist(userId, playlistId, movieId);
            updatePlaylistCover(playlist);
        }

        for (String playlistId : request.getPlaylistsToRemove()) {
            if (systemPlaylistsMap.containsKey(playlistId)) {
                playlistId = systemPlaylistsMap.get(playlistId);
            }

            var playlist = playlistRepository.removeMovieFromPlaylist(userId, playlistId, movieId);
            updatePlaylistCover(playlist);
        }
    }

    @Override
    public Playlist createPlaylist(String userId, PlaylistRequest request) {
        validatePermissions(userId);

        // Validate request
        if (request.getName() == null || request.getName().isEmpty()) {
            throw new InvalidPlaylistRequestException("Playlist name cannot be empty");
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

    private void validatePermissions(String userId) {
        var currentCustomPlaylists = playlistRepository.findPlaylistsByUserId(userId).stream()
                .filter(playlist -> playlist.getSystemPlaylistId() == null)
                .toList();

        var currentLimit = planService.getUserPlanDetails(userId).getFeatures().getPlaylistCreation().getLimit();

        if (currentCustomPlaylists.size() >= currentLimit) {
            throw new PlanFeatureNotAvailableException("Your plan does not allow creating more than " + currentLimit + " playlists");
        }
    }

    @Override
    public void createSystemPlaylistsForUser(String userId) {
        List<Playlist> systemPlaylists = buildSystemPlaylists();
        for (Playlist playlist : systemPlaylists) {
            playlist.setUserId(userId);
            playlistRepository.save(playlist);
        }
    }

    private Map<String, String> buildSystemPlaylistsMap(String userId) {
        var systemPlaylists = playlistRepository.findSystemPlaylistsByUserId(userId);

        return systemPlaylists.stream()
                .collect(Collectors.toMap(
                        Playlist::getSystemPlaylistId,
                        playlist -> playlist.getId().toString()
                ));
    }

    private void updatePlaylistCover(Playlist playlist) {
        if (playlist.getMovieIds().isEmpty()) {
            playlist.setCoverImageUrl(null);
            playlistRepository.save(playlist);
            return;
        }

        var movieId = playlist.getMovieIds().getLast();
        var movieCoverImage = fetchMovieCoverImage(movieId);
        if (movieCoverImage != null && !movieCoverImage.isEmpty()) {
            playlist.setCoverImageUrl(movieCoverImage);
            playlistRepository.save(playlist);
            return;
        }

        log.warn("Cover image for movie {} not found, setting cover to default", movieId);
        playlist.setCoverImageUrl(null);
        playlistRepository.save(playlist);
    }

    private String fetchMovieCoverImage(String movieId) {
        var movie = movieRepository.findMovieDetails(movieId);
        return movie.getMovieListing().getPosterPath();
    }

    private List<Playlist> buildSystemPlaylists() {
        return List.of(
                Playlist.builder()
                        .name("Favorites")
                        .description("Your favorite movies")
                        .systemPlaylistId(SystemPlaylist.FAVORITES.getId())
                        .build(),
                Playlist.builder()
                        .name("Watch Later")
                        .description("Movies you want to watch later")
                        .systemPlaylistId(SystemPlaylist.WATCH_LATER.getId())
                        .build(),
                Playlist.builder()
                        .name("Watched")
                        .description("Movies you have watched")
                        .systemPlaylistId(SystemPlaylist.WATCHED.getId())
                        .build()
        );
    }
}
