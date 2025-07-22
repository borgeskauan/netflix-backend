package org.contoso.netflix.reviews.adapter.output;

import org.contoso.netflix.reviews.adapter.output.dto.ReviewsDraftDatabase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ReviewDraftJpaClient extends JpaRepository<ReviewsDraftDatabase, UUID> {
    Optional<ReviewsDraftDatabase> findByUserIdAndMovieId(String userId, String movieId);
}
