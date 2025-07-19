package org.contoso.netflix.movies.adapter.output.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class TmdbMovieDetails extends TmdbMovieListing {
    private int runtime;

    @JsonProperty("original_language")
    private String originalLanguage;

    private String tagline;
    private String homepage;
    private String status;

    @JsonProperty("production_companies")
    private List<ProductionCompany> productionCompanies;

    @JsonProperty("spoken_languages")
    private List<SpokenLanguage> spokenLanguages;

    private Credits credits;

    @Data
    public static class ProductionCompany {
        private int id;
        private String name;

        @JsonProperty("logo_path")
        private String logoPath;

        @JsonProperty("origin_country")
        private String originCountry;
    }

    @Data
    public static class SpokenLanguage {
        @JsonProperty("iso_639_1")
        private String iso6391;

        private String name;
    }

    @Data
    public static class Credits {
        private List<CastMember> cast;
        private List<CrewMember> crew;
    }

    @Data
    public static class CastMember {
        private int id;
        private String name;
        private String character;

        @JsonProperty("profile_path")
        private String profilePath;
    }

    @Data
    public static class CrewMember {
        private int id;
        private String name;
        private String job;

        @JsonProperty("profile_path")
        private String profilePath;
    }
}