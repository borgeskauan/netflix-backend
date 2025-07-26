package org.contoso.netflix.playlist.port.input;

import org.contoso.netflix.playlist.domain.dto.MoviePlaylistUpdateRequest;
import org.contoso.netflix.playlist.domain.dto.PlaylistRequest;
import org.contoso.netflix.playlist.domain.entity.Playlist;

import java.util.List;

public interface PlaylistUseCase {
    List<Playlist> getPlaylists(String userId);

    Playlist getPlaylistsById(String userId, String playlistId);

    void updateMovieInPlaylists(String userId, String movieId, MoviePlaylistUpdateRequest request);

    Playlist createPlaylist(String userId, PlaylistRequest request);

    void createSystemPlaylistsForUser(String userId);
}
