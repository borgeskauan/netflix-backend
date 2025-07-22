package org.contoso.netflix.reviews.adapter.output;

import org.contoso.netflix.reviews.adapter.output.dto.ReviewDatabase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewJpaClient extends JpaRepository<ReviewDatabase, UUID> {
    List<ReviewDatabase> findReviewsByAuthor_IdAndMovieId(String authorId, String movieId);

    Optional<ReviewDatabase> findByAuthor_IdAndMovieId(String userId, String movieId);
}
