package org.contoso.netflix.playlist.adapter.output;

import org.contoso.netflix.playlist.domain.entity.Playlist;
import org.contoso.netflix.playlist.ports.output.PlaylistRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PlaylistJpaAdapter implements PlaylistRepository {

    private final PlaylistJpaClient playlistJpaClient;
    private final PlaylistMovieJpaClient playlistMovieJpaClient;
    private final PlaylistMapper playlistMapper;

    public PlaylistJpaAdapter(PlaylistJpaClient playlistJpaClient, PlaylistMovieJpaClient playlistMovieJpaClient, PlaylistMapper playlistMapper) {
        this.playlistJpaClient = playlistJpaClient;
        this.playlistMovieJpaClient = playlistMovieJpaClient;
        this.playlistMapper = playlistMapper;
    }

    @Override
    public List<Playlist> findPlaylistsByUserId(String userId) {
        return playlistJpaClient.findAllByUserId(userId).stream()
                .map(playlistMapper::toResponse)
                .toList();
    }

    @Override
    public Optional<Playlist> findPlaylistsByIdAndUserId(String playlistId, String userId) {
        return playlistJpaClient.findByIdAndUserId(UUID.fromString(playlistId), userId)
                .map(playlistMapper::toResponse);
    }

    @Override
    public List<Playlist> findPlaylistsByMovieId(String userId, String movieId) {
        return playlistJpaClient.findAllByUserIdAndMovieIdsContaining(userId, List.of(new PlaylistMovieDatabase(movieId))).stream()
                .map(playlistMapper::toResponse)
                .toList();
    }

    @Override
    public Playlist removeMovieFromPlaylist(String userId, String playlistId, String movieId) {
        PlaylistDatabase playlist = playlistJpaClient.findByIdAndUserId(UUID.fromString(playlistId), userId)
                .orElseThrow(() -> new IllegalArgumentException("Playlist not found for user: " + userId + " and playlistId: " + playlistId));

        playlist.getMovieIds().removeIf(movie -> movie.getId().equals(movieId));
        return playlistMapper.toResponse(playlistJpaClient.save(playlist));
    }

    @Override
    public Playlist addMovieToPlaylist(String userId, String playlistId, String movieId) {
        PlaylistDatabase playlist = playlistJpaClient.findByIdAndUserId(UUID.fromString(playlistId), userId)
                .orElseThrow(() -> new IllegalArgumentException("Playlist not found for user: " + userId + " and playlistId: " + playlistId));

        PlaylistMovieDatabase movie = new PlaylistMovieDatabase(movieId);
        if (playlist.getMovieIds().contains(movie)) {
            return playlistMapper.toResponse(playlist);
        }

        playlistMovieJpaClient.save(movie);

        playlist.getMovieIds().add(movie);
        return playlistMapper.toResponse(playlistJpaClient.save(playlist));
    }

    @Override
    public Playlist save(Playlist newPlaylist) {
        PlaylistDatabase playlistDatabase = playlistMapper.toDatabase(newPlaylist);

        playlistMovieJpaClient.saveAll(playlistDatabase.getMovieIds());

        playlistJpaClient.save(playlistDatabase);
        return playlistMapper.toResponse(playlistDatabase);
    }
}
