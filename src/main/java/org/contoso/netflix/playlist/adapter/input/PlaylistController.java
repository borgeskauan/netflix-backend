package org.contoso.netflix.playlist.adapter.input;

import org.contoso.netflix.playlist.domain.dto.MoviePlaylistUpdateRequest;
import org.contoso.netflix.playlist.domain.dto.PlaylistResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/playlists/{userId}")
public class PlaylistController {

    // Define endpoints for playlist management here
//	- [ ] Fetch user's playlists
//  - [ ] Fetch user playlist by id
//	- [ ] List user's playlists movie is in
//  - [ ] Update movie playlists
//	- [ ] Create playlist

    @GetMapping
    public List<PlaylistResponse> getPlaylists(@PathVariable String userId) {
        return List.of();
    }

    @GetMapping("/{playlistId}")
    public PlaylistResponse getPlaylistById(@PathVariable String userId, @PathVariable String playlistId) {
        return PlaylistResponse.builder().build();
    }

    @GetMapping("/movies/{movieId}")
    public List<PlaylistResponse> getPlaylistsByMovie(@PathVariable String userId, @PathVariable String movieId) {
        return List.of();
    }

    @PutMapping("/movies/{movieId}")
    public PlaylistResponse updateMovieInPlaylist(@PathVariable String userId, @PathVariable String movieId, @RequestBody MoviePlaylistUpdateRequest request) {
        return PlaylistResponse.builder().build();
    }

    @PostMapping
    public PlaylistResponse createPlaylist(@PathVariable String userId, @RequestBody PlaylistRequest request) {
        return PlaylistResponse.builder()
                .userId(userId)
                .name(request.getName())
                .description(request.getDescription())
                .coverImageUrl(request.getCoverImageUrl())
                .build();
    }
}
