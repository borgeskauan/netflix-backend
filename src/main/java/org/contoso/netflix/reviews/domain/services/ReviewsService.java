package org.contoso.netflix.reviews.domain.services;

import org.contoso.netflix.reviews.domain.entity.Review;
import org.contoso.netflix.reviews.domain.entity.ReviewDraft;
import org.contoso.netflix.reviews.port.input.ReviewsUseCase;
import org.contoso.netflix.reviews.port.output.ReviewsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewsService implements ReviewsUseCase {

    private final ReviewsRepository reviewsRepository;

    public ReviewsService(ReviewsRepository reviewsRepository) {
        this.reviewsRepository = reviewsRepository;
    }

    @Override
    public ReviewDraft getCurrentReviewDraft(String userId, String movieId) {
        var reviewDraft = reviewsRepository.getCurrentReviewDraft(userId, movieId);
        return reviewDraft.orElseGet(() -> ReviewDraft.builder()
                .content("")
                .rating(0.0)
                .build());
    }

    @Override
    public ReviewDraft updateCurrentReviewDraft(String userId, String movieId, ReviewDraft draft) {
        return reviewsRepository.putCurrentReviewDraft(userId, movieId, draft);
    }

    @Override
    public List<Review> listMovieReviews(String userId, String movieId) {
        return reviewsRepository.listMovieReviews(userId, movieId);
    }

    @Override
    public Review submitReviewDraft(String userId, String movieId, ReviewDraft reviewDraft) {
        updateCurrentReviewDraft(userId, movieId, reviewDraft);

        var review = reviewsRepository.getReview(userId, movieId)
                .orElse(Review.builder().build());

        review.setContent(reviewDraft.getContent());
        review.setRating(reviewDraft.getRating());
        review.setLikes(0);

        return reviewsRepository.putReview(userId, movieId, review);
    }
}
