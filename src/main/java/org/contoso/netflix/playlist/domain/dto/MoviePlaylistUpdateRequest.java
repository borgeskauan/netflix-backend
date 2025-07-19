package org.contoso.netflix.playlist.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class MoviePlaylistUpdateRequest {
    private List<String> playlistsToRemove;
    private List<String> playlistsToAdd;
}
