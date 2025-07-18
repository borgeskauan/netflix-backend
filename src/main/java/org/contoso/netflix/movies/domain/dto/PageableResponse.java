package org.contoso.netflix.movies.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageableResponse<T> {
    private int page;
    private int totalPages;
    private int totalResults;
    private List<T> results;

    public <U> PageableResponse<U> mapResults(List<U> newResults) {
        return PageableResponse.<U>builder()
                .page(this.page)
                .totalPages(this.totalPages)
                .totalResults(this.totalResults)
                .results(newResults)
                .build();
    }
}
