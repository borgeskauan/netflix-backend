package org.contoso.netflix.reviews.domain.services;

import org.contoso.netflix.plans.domain.exception.PlanFeatureNotAvailableException;
import org.contoso.netflix.plans.domain.services.PlanService;
import org.contoso.netflix.reviews.domain.entity.Review;
import org.contoso.netflix.reviews.domain.entity.ReviewDraft;
import org.contoso.netflix.reviews.port.input.ReviewsUseCase;
import org.contoso.netflix.reviews.port.output.ReviewsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewsService implements ReviewsUseCase {

    private final ReviewsRepository reviewsRepository;
    private final PlanService planService;

    public ReviewsService(ReviewsRepository reviewsRepository, PlanService planService) {
        this.reviewsRepository = reviewsRepository;
        this.planService = planService;
    }

    @Override
    public ReviewDraft getCurrentReviewDraft(String userId, String movieId) {
        validateWritingPermission(userId);

        var reviewDraft = reviewsRepository.getCurrentReviewDraft(userId, movieId);
        return reviewDraft.orElseGet(() -> ReviewDraft.builder()
                .content("")
                .rating(0.0)
                .build());
    }

    @Override
    public ReviewDraft updateCurrentReviewDraft(String userId, String movieId, ReviewDraft draft) {
        validateWritingPermission(userId);

        return reviewsRepository.putCurrentReviewDraft(userId, movieId, draft);
    }

    @Override
    public List<Review> listMovieReviews(String userId, String movieId) {
        if (!planService.getUserPlanDetails(userId).getFeatures().getReviews().getCanView()) {
            throw new PlanFeatureNotAvailableException("Your plan does not allow viewing reviews");
        }

        return reviewsRepository.listMovieReviews(userId, movieId);
    }

    @Override
    public Review submitReviewDraft(String userId, String movieId, ReviewDraft reviewDraft) {
        validateWritingPermission(userId);

        updateCurrentReviewDraft(userId, movieId, reviewDraft);

        var review = reviewsRepository.getReview(userId, movieId)
                .orElse(Review.builder().build());

        review.setContent(reviewDraft.getContent());
        review.setRating(reviewDraft.getRating());
        review.setLikes(0);

        return reviewsRepository.putReview(userId, movieId, review);
    }

    private void validateWritingPermission(String userId) {
        if (!planService.getUserPlanDetails(userId).getFeatures().getReviews().getCanWrite()) {
            throw new PlanFeatureNotAvailableException("Your plan does not allow writing reviews");
        }
    }
}
