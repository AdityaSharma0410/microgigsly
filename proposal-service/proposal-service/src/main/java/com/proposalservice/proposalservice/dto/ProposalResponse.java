package com.proposalservice.proposalservice.dto;

import com.proposalservice.proposalservice.model.ProposalStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProposalResponse {

    private Long id;
    private Long taskId;
    private String taskTitle;
    private Long professionalId;
    private String professionalName;
    private String message;
    private BigDecimal proposedAmount;
    private String estimatedDuration;
    private ProposalStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime acceptedAt;
    private LocalDateTime rejectedAt;
}
