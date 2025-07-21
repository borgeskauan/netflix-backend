package org.contoso.netflix.playlist.adapter.output;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
public class PlaylistDatabase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String userId;

    @ManyToMany
    private List<PlaylistMovieDatabase> movieIds;

    private String name;
    private String description;

    private String coverImageUrl;
    private String systemPlaylistId;
}
