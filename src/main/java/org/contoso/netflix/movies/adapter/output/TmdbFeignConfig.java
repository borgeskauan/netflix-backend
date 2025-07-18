package org.contoso.netflix.movies.adapter.output;

import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.contoso.netflix.movies.domain.exception.MovieNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TmdbFeignConfig {

    @Value("${external.tmdb-api.api-key}")
    private String authToken;

    @Bean
    public RequestInterceptor authInterceptor() {
        return template -> template.header("Authorization", authToken);
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return (methodKey, response) -> {
            if (response.status() == 404) {
                return new MovieNotFoundException("Movie not found");
            }
            return new ErrorDecoder.Default().decode(methodKey, response);
        };
    }
}
