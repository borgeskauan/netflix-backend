package org.contoso.netflix.playlist.adapter.input;

import org.contoso.netflix.playlist.domain.dto.MoviePlaylistUpdateRequest;
import org.contoso.netflix.playlist.domain.entity.Playlist;
import org.contoso.netflix.playlist.port.input.PlaylistUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/playlists/{userId}")
public class PlaylistController {

    private final PlaylistUseCase playlistUseCase;

    public PlaylistController(PlaylistUseCase playlistUseCase) {
        this.playlistUseCase = playlistUseCase;
    }

    @GetMapping
    public List<Playlist> getPlaylists(@PathVariable("userId") String userId) {
        return playlistUseCase.getPlaylists(userId);
    }

    @GetMapping("/{playlistId}")
    public Playlist getPlaylistById(@PathVariable("userId") String userId, @PathVariable("playlistId") String playlistId) {
        return playlistUseCase.getPlaylistsById(userId, playlistId);
    }

    @PutMapping("/movies/{movieId}")
    public void updateMovieInPlaylists(@PathVariable String userId, @PathVariable String movieId, @RequestBody MoviePlaylistUpdateRequest request) {
        playlistUseCase.updateMovieInPlaylists(userId, movieId, request);
    }

    @PostMapping
    public Playlist createPlaylist(@PathVariable String userId, @RequestBody PlaylistRequest request) {
        return playlistUseCase.createPlaylist(userId, request);
    }
}
