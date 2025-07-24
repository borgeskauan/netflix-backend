package org.contoso.netflix.plans.domain.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlanFeatures {
    private PlaylistCreation playlistCreation;
    private Reviews reviews;
    private Search search;
    private Boolean canSeeSimilarMovies;
    private Boolean canWatchTrailers;

    @Data
    @Builder
    public static class PlaylistCreation {
        private Integer limit;
    }

    @Data
    @Builder
    public static class Reviews {
        private Boolean canView;
        private Boolean canWrite;
        private Boolean canReply;
    }

    @Data
    @Builder
    public static class Search {
        private SearchType type;
        private Boolean hasFilters;
        private Boolean hasSorting;
    }

    public enum SearchType {
        BASIC, ADVANCED
    }
}