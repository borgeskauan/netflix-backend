package org.contoso.netflix.reviews.domain.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewDraft {
    private String content;
    private Double rating;
}
