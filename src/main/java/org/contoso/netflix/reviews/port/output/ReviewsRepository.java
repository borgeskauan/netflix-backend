package org.contoso.netflix.reviews.port.output;

import org.contoso.netflix.reviews.domain.entity.Review;
import org.contoso.netflix.reviews.domain.entity.ReviewDraft;

import java.util.List;
import java.util.Optional;

public interface ReviewsRepository {
    Optional<ReviewDraft> getCurrentReviewDraft(String userId, String movieId);

    ReviewDraft putCurrentReviewDraft(String userId, String movieId, ReviewDraft draft);

    List<Review> listMovieReviews(String userId, String movieId);

    Review putReview(String userId, String movieId, Review review);

    Optional<Review> getReview(String userId, String movieId);
}
