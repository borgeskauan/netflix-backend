package org.contoso.netflix.playlist.adapter.output;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistMovieDatabase {

    @Id
    private String id;
}
