package com.proposalservice.proposalservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ProposalRequest {

    @NotNull(message = "Task id is required")
    private Long taskId;

    @NotBlank(message = "Proposal message is required")
    private String message;

    private BigDecimal proposedAmount;

    private String estimatedDuration;
}
