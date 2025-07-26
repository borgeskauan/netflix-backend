package org.contoso.netflix.reviews.adapter.output;

import org.contoso.netflix.auth.adapter.output.UserJpaClient;
import org.contoso.netflix.reviews.adapter.output.dto.ReviewDraftMapper;
import org.contoso.netflix.reviews.adapter.output.dto.ReviewMapper;
import org.contoso.netflix.reviews.adapter.output.dto.ReviewsDraftDatabase;
import org.contoso.netflix.reviews.domain.entity.Review;
import org.contoso.netflix.reviews.domain.entity.ReviewDraft;
import org.contoso.netflix.reviews.domain.exceptions.InvalidReviewRequestException;
import org.contoso.netflix.reviews.port.output.ReviewsRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ReviewsJpaAdapter implements ReviewsRepository {

    private final ReviewDraftJpaClient reviewDraftJpaClient;
    private final ReviewJpaClient reviewJpaClient;

    private final ReviewDraftMapper reviewDraftMapper;
    private final ReviewMapper reviewMapper;

    private final UserJpaClient userJpaClient;

    public ReviewsJpaAdapter(ReviewDraftJpaClient reviewDraftJpaClient, ReviewJpaClient reviewJpaClient, ReviewDraftMapper reviewDraftMapper, ReviewMapper reviewMapper, UserJpaClient userJpaClient) {
        this.reviewDraftJpaClient = reviewDraftJpaClient;
        this.reviewJpaClient = reviewJpaClient;
        this.reviewDraftMapper = reviewDraftMapper;
        this.reviewMapper = reviewMapper;
        this.userJpaClient = userJpaClient;
    }

    @Override
    public Optional<ReviewDraft> getCurrentReviewDraft(String userId, String movieId) {
        return reviewDraftJpaClient.findByUserIdAndMovieId(userId, movieId)
                .map(reviewDraftMapper::toDomain);
    }

    @Override
    public ReviewDraft putCurrentReviewDraft(String userId, String movieId, ReviewDraft draft) {
        var draftDatabase = reviewDraftJpaClient.findByUserIdAndMovieId(userId, movieId)
                .orElse(ReviewsDraftDatabase.builder()
                        .userId(userId)
                        .movieId(movieId)
                        .build());

        draftDatabase.setContent(draft.getContent());
        draftDatabase.setRating(draft.getRating());

        var savedDraft = reviewDraftJpaClient.save(draftDatabase);

        return reviewDraftMapper.toDomain(savedDraft);
    }

    @Override
    public List<Review> listMovieReviews(String userId, String movieId) {
        var reviews = reviewJpaClient.findReviewsByMovieId(movieId);
        return reviews.stream()
                .map(reviewMapper::toDomain)
                .toList();
    }

    @Override
    public Review putReview(String userId, String movieId, Review review) {
        var reviewDatabase = reviewMapper.toDatabase(review);
        var author = userJpaClient.findById(userId)
                .orElseThrow(() -> new InvalidReviewRequestException("User not found: " + userId));

        reviewDatabase.setAuthor(author);
        reviewDatabase.setMovieId(movieId);

        var savedReview = reviewJpaClient.save(reviewDatabase);
        return reviewMapper.toDomain(savedReview);
    }

    @Override
    public Optional<Review> getReview(String userId, String movieId) {
        return reviewJpaClient.findByAuthor_IdAndMovieId(userId, movieId)
                .map(reviewMapper::toDomain);
    }
}
