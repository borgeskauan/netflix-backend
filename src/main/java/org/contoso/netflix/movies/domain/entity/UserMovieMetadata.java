package org.contoso.netflix.movies.domain.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserMovieMetadata {
    private Boolean isWatched;
    private Boolean isFavorite;
    private Boolean isWatchLater;
}
