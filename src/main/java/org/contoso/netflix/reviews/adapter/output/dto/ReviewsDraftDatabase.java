package org.contoso.netflix.reviews.adapter.output.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewsDraftDatabase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String userId;
    private String movieId;

    @Column(length = 500)
    private String content;

    private Double rating;
}
