package org.contoso.netflix.movies.domain.entity;

import lombok.Data;

import java.util.List;

@Data
public class MovieDetails {
    private int runtime;
    private String originalLanguage;
    private String tagline;
    private String homepage;
    private String status;
    private List<ProductionCompany> productionCompanies;
    private List<SpokenLanguage> spokenLanguages;
    private Credits credits;

    @Data
    public static class ProductionCompany {
        private int id;
        private String name;
        private String logoPath;
        private String originCountry;
    }

    @Data
    public static class SpokenLanguage {
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
        private String profilePath;
    }

    @Data
    public static class CrewMember {
        private int id;
        private String name;
        private String job;
        private String profilePath;
    }
}