package org.contoso.netflix.playlist.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class PlaylistRequest {
    private String name;
    private String description;
    private String coverImageUrl;

    private List<String> movieIdsToAdd;
}
