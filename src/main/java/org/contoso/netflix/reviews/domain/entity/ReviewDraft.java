package org.contoso.netflix.reviews.domain.entity;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewDraft {

    @Size(max = 500, message = "Content must be 500 characters or less")
    private String content;
    private Double rating;
}
