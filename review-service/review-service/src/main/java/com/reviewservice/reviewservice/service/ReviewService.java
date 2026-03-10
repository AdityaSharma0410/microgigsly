package com.reviewservice.reviewservice.service;

import com.reviewservice.reviewservice.dto.ReviewRequest;
import com.reviewservice.reviewservice.dto.ReviewResponse;

import java.util.List;

public interface ReviewService {

    ReviewResponse createReview(ReviewRequest request);

    List<ReviewResponse> getReviewsForUser(Long userId);

    List<ReviewResponse> getAllReviews();

    void deleteReview(Long id);
}
