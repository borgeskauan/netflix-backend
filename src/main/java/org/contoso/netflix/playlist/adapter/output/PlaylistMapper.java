package org.contoso.netflix.playlist.adapter.output;

import org.contoso.netflix.playlist.domain.entity.Playlist;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlaylistMapper {
    Playlist toResponse(PlaylistDatabase database);

    PlaylistDatabase toDatabase(Playlist newPlaylist);

    default List<String> mapMovieIdsToResponse(List<PlaylistMovieDatabase> movies) {
        if (movies == null) {
            return null;
        }

        return movies.stream()
                .map(PlaylistMovieDatabase::getId)
                .map(Object::toString)
                .toList();
    }

    default List<PlaylistMovieDatabase> mapMovieIdsToDatabase(List<String> movieIds) {
        if (movieIds == null) {
            return null;
        }
        return movieIds.stream()
                .map(PlaylistMovieDatabase::new)
                .toList();
    }
}
