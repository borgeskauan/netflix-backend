package org.contoso.netflix.reviews.port.input;

import org.contoso.netflix.reviews.domain.entity.Review;
import org.contoso.netflix.reviews.domain.entity.ReviewDraft;

import java.util.List;

public interface ReviewsUseCase {
    ReviewDraft getCurrentReviewDraft(String userId, String movieId);

    ReviewDraft updateCurrentReviewDraft(String userId, String movieId, ReviewDraft draft);

    List<Review> listMovieReviews(String userId, String movieId);

    Review submitReviewDraft(String userId, String movieId, ReviewDraft reviewDraft);
}
