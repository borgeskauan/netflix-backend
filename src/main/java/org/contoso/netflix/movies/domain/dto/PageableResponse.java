package org.contoso.netflix.movies.domain.dto;

import lombok.*;

import java.util.List;

@Data
@With
@Builder
public class PageableResponse<T> {
    private int page;

    private int totalPages;
    private int totalResults;

    private List<T> results;
}
