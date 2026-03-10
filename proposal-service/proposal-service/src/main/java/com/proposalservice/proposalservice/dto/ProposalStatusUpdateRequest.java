package com.proposalservice.proposalservice.dto;

import com.proposalservice.proposalservice.model.ProposalStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProposalStatusUpdateRequest {

    @NotNull(message = "Status is required")
    private ProposalStatus status;
}
