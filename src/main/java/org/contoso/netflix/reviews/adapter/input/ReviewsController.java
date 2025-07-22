package org.contoso.netflix.reviews.adapter.input;

import org.contoso.netflix.reviews.domain.entity.ReviewDraft;
import org.contoso.netflix.reviews.domain.entity.Review;
import org.contoso.netflix.reviews.port.input.ReviewsUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews/{userId}")
public class ReviewsController {

    private final ReviewsUseCase reviewsUseCase;

    public ReviewsController(ReviewsUseCase reviewsUseCase) {
        this.reviewsUseCase = reviewsUseCase;
    }

    @GetMapping("/movies/{movieId}/draft")
    public ReviewDraft getCurrentReviewDraft(@PathVariable String userId, @PathVariable String movieId) {
        return reviewsUseCase.getCurrentReviewDraft(userId, movieId);
    }

    @PutMapping("/movies/{movieId}/draft")
    public ReviewDraft updateCurrentReviewDraft(@PathVariable String userId, @PathVariable String movieId, @RequestBody ReviewDraft draft) {
        return reviewsUseCase.updateCurrentReviewDraft(userId, movieId, draft);
    }

    @GetMapping("/movies/{movieId}")
    public List<Review> listMovieReviews(@PathVariable String userId, @PathVariable String movieId) {
        return reviewsUseCase.listMovieReviews(userId, movieId);
    }

    @PostMapping("/movies/{movieId}/submit")
    public Review submitReviewDraft(@PathVariable String userId, @PathVariable String movieId, @RequestBody ReviewDraft review) {
        return reviewsUseCase.submitReviewDraft(userId, movieId, review);
    }
}
