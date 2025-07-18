package org.contoso.netflix.movies.adapter.output.dto;

import org.contoso.netflix.movies.domain.entity.MovieDetails;
import org.contoso.netflix.movies.domain.entity.MovieListing;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TmdbMovieMapper {

    MovieDetails toMovieDetails(TmdbMovieDetails tmdbMovieDetails);
    MovieListing toMovieListing(TmdbMovieListing tmdbMovieListing);

}
