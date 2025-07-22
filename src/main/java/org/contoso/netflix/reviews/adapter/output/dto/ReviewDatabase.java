package org.contoso.netflix.reviews.adapter.output.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.contoso.netflix.auth.adapter.output.NetflixUserDatabase;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Data
@Entity
public class ReviewDatabase {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.UUID)
    private UUID id;

    private String movieId;

    @ManyToOne
    private NetflixUserDatabase author;

    private Double rating;
    private String content;
    private int likes;

    @CreationTimestamp
    private Instant createdAt;
}
