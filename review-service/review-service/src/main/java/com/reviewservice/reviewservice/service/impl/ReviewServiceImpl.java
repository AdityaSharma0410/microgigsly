package com.reviewservice.reviewservice.service.impl;

import com.reviewservice.reviewservice.client.TaskClient;
import com.reviewservice.reviewservice.client.UserClient;
import com.reviewservice.reviewservice.dto.*;
import com.reviewservice.reviewservice.exception.AccessDeniedException;
import com.reviewservice.reviewservice.exception.BadRequestException;
import com.reviewservice.reviewservice.exception.ConflictException;
import com.reviewservice.reviewservice.exception.ResourceNotFoundException;
import com.reviewservice.reviewservice.exception.ReviewNotFoundException;
import com.reviewservice.reviewservice.mapper.ReviewMapper;
import com.reviewservice.reviewservice.model.Review;
import com.reviewservice.reviewservice.repository.ReviewRepository;
import com.reviewservice.reviewservice.security.CurrentUserService;
import com.reviewservice.reviewservice.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final TaskClient taskClient;
    private final UserClient userClient;
    private final CurrentUserService currentUserService;

    @Override
    @Transactional
    public ReviewResponse createReview(ReviewRequest request) {
        Long reviewerId = currentUserService.getCurrentUserId();

        if (reviewerId.equals(request.getRevieweeId())) {
            throw new BadRequestException("You cannot review yourself");
        }

        TaskFeignResponse task = getTaskOrThrow(request.getTaskId());
        UserFeignResponse reviewee = getUserOrThrow(request.getRevieweeId());

        // Validate both participants are part of the task
        boolean reviewerParticipated = reviewerId.equals(task.clientId()) ||
                (task.assignedProfessionalId() != null && reviewerId.equals(task.assignedProfessionalId()));
        boolean revieweeParticipated = request.getRevieweeId().equals(task.clientId()) ||
                (task.assignedProfessionalId() != null && request.getRevieweeId().equals(task.assignedProfessionalId()));

        if (!reviewerParticipated || !revieweeParticipated) {
            throw new BadRequestException("Only task participants can review each other");
        }

        if (reviewRepository.existsByTaskIdAndReviewerIdAndRevieweeId(
                request.getTaskId(), reviewerId, request.getRevieweeId())) {
            throw new ConflictException("You have already reviewed this user for this task");
        }

        Review review = ReviewMapper.toEntity(request, reviewerId);
        Review saved = reviewRepository.save(review);

        UserFeignResponse reviewer = getUserQuietly(reviewerId);
        return ReviewMapper.toResponse(saved, task, reviewer, reviewee);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewResponse> getReviewsForUser(Long userId) {
        return reviewRepository.findByRevieweeId(userId).stream()
                .map(r -> {
                    TaskFeignResponse task = getTaskQuietly(r.getTaskId());
                    UserFeignResponse reviewer = getUserQuietly(r.getReviewerId());
                    UserFeignResponse reviewee = getUserQuietly(r.getRevieweeId());
                    return ReviewMapper.toResponse(r, task, reviewer, reviewee);
                })
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewResponse> getAllReviews() {
        String role = currentUserService.getCurrentUserRole();
        if (!"ADMIN".equals(role)) {
            throw new AccessDeniedException("Only admins can list all reviews");
        }
        return reviewRepository.findAll().stream()
                .map(ReviewMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public void deleteReview(Long id) {
        String role = currentUserService.getCurrentUserRole();
        if (!"ADMIN".equals(role)) {
            throw new AccessDeniedException("Only admins can delete reviews");
        }
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException(id));
        reviewRepository.delete(review);
    }

    private TaskFeignResponse getTaskOrThrow(Long taskId) {
        try {
            return taskClient.getTask(taskId);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Task not found with id: " + taskId);
        }
    }

    private TaskFeignResponse getTaskQuietly(Long taskId) {
        try { return taskClient.getTask(taskId); } catch (Exception e) { return null; }
    }

    private UserFeignResponse getUserOrThrow(Long userId) {
        try {
            return userClient.getUser(userId);
        } catch (Exception e) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }
    }

    private UserFeignResponse getUserQuietly(Long userId) {
        try { return userClient.getUser(userId); } catch (Exception e) { return null; }
    }
}
