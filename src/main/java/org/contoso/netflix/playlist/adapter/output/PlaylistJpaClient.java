package org.contoso.netflix.playlist.adapter.output;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlaylistJpaClient extends JpaRepository<PlaylistDatabase, String> {
    List<PlaylistDatabase> findAllByUserId(String userId);

    Optional<PlaylistDatabase> findByIdAndUserId(UUID id, String userId);

    List<PlaylistDatabase> findAllByUserIdAndMovieIdsContaining(String userId, List<PlaylistMovieDatabase> movieIds);

    List<PlaylistDatabase> findAllByUserIdAndSystemPlaylistIdNotNull(String userId);
}
