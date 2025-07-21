package org.contoso.netflix.movies.domain.dto;

import lombok.*;

import java.util.List;
import java.util.function.Function;

@Data
@With
@Builder
public class PageableResponse<T> {
    private int page;

    private int totalPages;
    private int totalResults;

    private List<T> results;

    public <U> PageableResponse<U> map(Function<T, U> mapper) {
        List<U> mappedResults = results.stream()
                .map(mapper)
                .toList();

        return new PageableResponse<>(
                page,
                totalPages,
                totalResults,
                mappedResults
        );
    }
}
