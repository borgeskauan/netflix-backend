package org.contoso.netflix.playlist.adapter.output;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistMovieJpaClient extends JpaRepository<PlaylistMovieDatabase, String> {
}
