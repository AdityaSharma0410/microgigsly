package com.proposalservice.proposalservice.mapper;

import com.proposalservice.proposalservice.dto.ProposalRequest;
import com.proposalservice.proposalservice.dto.ProposalResponse;
import com.proposalservice.proposalservice.dto.TaskFeignResponse;
import com.proposalservice.proposalservice.dto.UserFeignResponse;
import com.proposalservice.proposalservice.model.Proposal;

public final class ProposalMapper {

    private ProposalMapper() {}

    public static Proposal toEntity(ProposalRequest request, Long professionalId) {
        return Proposal.builder()
                .taskId(request.getTaskId())
                .professionalId(professionalId)
                .message(request.getMessage())
                .proposedAmount(request.getProposedAmount())
                .estimatedDuration(request.getEstimatedDuration())
                .build();
    }

    public static ProposalResponse toResponse(
            Proposal proposal,
            TaskFeignResponse task,
            UserFeignResponse professional
    ) {
        return ProposalResponse.builder()
                .id(proposal.getId())
                .taskId(proposal.getTaskId())
                .taskTitle(task != null ? task.title() : null)
                .professionalId(proposal.getProfessionalId())
                .professionalName(professional != null ? professional.fullName() : null)
                .message(proposal.getMessage())
                .proposedAmount(proposal.getProposedAmount())
                .estimatedDuration(proposal.getEstimatedDuration())
                .status(proposal.getStatus())
                .createdAt(proposal.getCreatedAt())
                .updatedAt(proposal.getUpdatedAt())
                .acceptedAt(proposal.getAcceptedAt())
                .rejectedAt(proposal.getRejectedAt())
                .build();
    }

    public static ProposalResponse toResponse(Proposal proposal) {
        return ProposalResponse.builder()
                .id(proposal.getId())
                .taskId(proposal.getTaskId())
                .professionalId(proposal.getProfessionalId())
                .message(proposal.getMessage())
                .proposedAmount(proposal.getProposedAmount())
                .estimatedDuration(proposal.getEstimatedDuration())
                .status(proposal.getStatus())
                .createdAt(proposal.getCreatedAt())
                .updatedAt(proposal.getUpdatedAt())
                .acceptedAt(proposal.getAcceptedAt())
                .rejectedAt(proposal.getRejectedAt())
                .build();
    }
}
