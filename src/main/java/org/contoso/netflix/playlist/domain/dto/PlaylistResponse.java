package org.contoso.netflix.playlist.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class PlaylistResponse {

    private UUID id;
    private String userId;

    private List<String> movieIds;

    private String name;
    private String description;

    private String coverImageUrl;
    private String isSystemPlaylist;
}
