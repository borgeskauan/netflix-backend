package org.contoso.netflix.playlist.adapter.input;

import lombok.Data;

import java.util.List;

@Data
public class PlaylistRequest {
    private String name;
    private String description;
    private String coverImageUrl;

    private List<String> movieIdsToAdd;
}
