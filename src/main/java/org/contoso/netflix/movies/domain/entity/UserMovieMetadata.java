package org.contoso.netflix.movies.domain.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserMovieMetadata {

    @Builder.Default
    private Boolean isWatched = false;

    @Builder.Default
    private Boolean isFavorite = false;

    @Builder.Default
    private Boolean isWatchLater = false;
}
