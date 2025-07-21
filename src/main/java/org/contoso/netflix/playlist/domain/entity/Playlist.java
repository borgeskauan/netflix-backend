package org.contoso.netflix.playlist.domain.entity;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class Playlist {

    private UUID id;
    private String userId;

    @Builder.Default
    private List<String> movieIds = new ArrayList<>();

    private String name;
    private String description;

    private String coverImageUrl;

    private String systemPlaylistId;
}
