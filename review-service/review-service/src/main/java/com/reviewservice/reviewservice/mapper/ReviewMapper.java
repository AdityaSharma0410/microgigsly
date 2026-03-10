package com.reviewservice.reviewservice.mapper;

import com.reviewservice.reviewservice.dto.ReviewRequest;
import com.reviewservice.reviewservice.dto.ReviewResponse;
import com.reviewservice.reviewservice.dto.TaskFeignResponse;
import com.reviewservice.reviewservice.dto.UserFeignResponse;
import com.reviewservice.reviewservice.model.Review;

public final class ReviewMapper {

    private ReviewMapper() {}

    public static Review toEntity(ReviewRequest request, Long reviewerId) {
        return Review.builder()
                .taskId(request.getTaskId())
                .reviewerId(reviewerId)
                .revieweeId(request.getRevieweeId())
                .rating(request.getRating())
                .comment(request.getComment())
                .build();
    }

    public static ReviewResponse toResponse(
            Review review,
            TaskFeignResponse task,
            UserFeignResponse reviewer,
            UserFeignResponse reviewee
    ) {
        return ReviewResponse.builder()
                .id(review.getId())
                .taskId(review.getTaskId())
                .taskTitle(task != null ? task.title() : null)
                .reviewerId(review.getReviewerId())
                .reviewerName(reviewer != null ? reviewer.fullName() : null)
                .revieweeId(review.getRevieweeId())
                .revieweeName(reviewee != null ? reviewee.fullName() : null)
                .rating(review.getRating())
                .comment(review.getComment())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .build();
    }

    public static ReviewResponse toResponse(Review review) {
        return ReviewResponse.builder()
                .id(review.getId())
                .taskId(review.getTaskId())
                .reviewerId(review.getReviewerId())
                .revieweeId(review.getRevieweeId())
                .rating(review.getRating())
                .comment(review.getComment())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .build();
    }
}
