package com.proposalservice.proposalservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "proposals",
    uniqueConstraints = @UniqueConstraint(columnNames = {"task_id", "professional_id"}))
public class Proposal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "task_id", nullable = false)
    private Long taskId;

    @NotNull
    @Column(name = "professional_id", nullable = false)
    private Long professionalId;

    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(name = "proposed_amount", precision = 10, scale = 2)
    private BigDecimal proposedAmount;

    @Column(name = "estimated_duration")
    private String estimatedDuration;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ProposalStatus status = ProposalStatus.PENDING;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "accepted_at")
    private LocalDateTime acceptedAt;

    @Column(name = "rejected_at")
    private LocalDateTime rejectedAt;

    public void accept() {
        this.status = ProposalStatus.ACCEPTED;
        this.acceptedAt = LocalDateTime.now();
        this.rejectedAt = null;
    }

    public void reject() {
        this.status = ProposalStatus.REJECTED;
        this.rejectedAt = LocalDateTime.now();
        this.acceptedAt = null;
    }

    public void resetToPending() {
        this.status = ProposalStatus.PENDING;
        this.acceptedAt = null;
        this.rejectedAt = null;
    }
}
