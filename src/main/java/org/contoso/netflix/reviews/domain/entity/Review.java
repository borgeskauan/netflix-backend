package org.contoso.netflix.reviews.domain.entity;

import lombok.Builder;
import lombok.Data;
import org.contoso.netflix.auth.domain.dto.PublicUserResponse;

import java.time.Instant;

@Data
@Builder
public class Review {

    private String id;

    private PublicUserResponse author;

    private Double rating;
    private String content;
    private int likes;

    private Instant createdAt;
}
