package com.taskservice.taskservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 5, max = 200)
    @Column(nullable = false, length = 200)
    private String title;

    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    /*
        Instead of ManyToOne User relationship
        we store the user ID from the user-service
     */
    @Column(name = "client_id", nullable = false)
    private Long clientId;

    /*
        Category belongs to another service
     */
    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "budget_min", precision = 10, scale = 2)
    private BigDecimal budgetMin;

    @Column(name = "budget_max", precision = 10, scale = 2)
    private BigDecimal budgetMax;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TaskStatus status = TaskStatus.OPEN;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "priority_level", length = 20)
    private TaskPriority priority = TaskPriority.MEDIUM;

    private LocalDateTime deadline;

    @Column(name = "required_skills", columnDefinition = "TEXT")
    private String requiredSkills;

    private String location;

    @Builder.Default
    @Column(name = "is_remote")
    private Boolean isRemote = false;

    @Column(name = "estimated_duration")
    private String estimatedDuration;

    /*
        Assigned professional from user-service
     */
    @Column(name = "assigned_professional_id")
    private Long assignedProfessionalId;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /*
        Domain Methods
     */

    public void updateTaskDetails(
            String title,
            String description,
            Long categoryId,
            BigDecimal budgetMin,
            BigDecimal budgetMax,
            LocalDateTime deadline,
            String requiredSkills,
            String location,
            Boolean isRemote,
            String estimatedDuration
    ) {
        this.title = title;
        this.description = description;
        this.categoryId = categoryId;
        this.budgetMin = budgetMin;
        this.budgetMax = budgetMax;
        this.deadline = deadline;
        this.requiredSkills = requiredSkills;
        this.location = location;
        this.isRemote = isRemote;
        this.estimatedDuration = estimatedDuration;
    }

    public void assignProfessional(Long professionalId) {
        this.assignedProfessionalId = professionalId;
    }

    public void updateStatus(TaskStatus status) {
        this.status = status;

        if (status == TaskStatus.COMPLETED) {
            this.completedAt = LocalDateTime.now();
        }
    }

    public void updatePriority(TaskPriority priority) {
        this.priority = priority;
    }
}