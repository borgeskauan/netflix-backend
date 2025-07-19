package org.contoso.netflix.playlist.adapter.input;

import lombok.Data;

@Data
public class PlaylistRequest {
    private String name;
    private String description;
    private String coverImageUrl;

    private String movieIdToAdd;
}
