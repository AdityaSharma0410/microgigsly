package com.proposalservice.proposalservice.service;

import com.proposalservice.proposalservice.dto.ProposalRequest;
import com.proposalservice.proposalservice.dto.ProposalResponse;
import com.proposalservice.proposalservice.dto.ProposalStatusUpdateRequest;

import java.util.List;

public interface ProposalService {

    ProposalResponse createProposal(ProposalRequest request);

    ProposalResponse updateStatus(Long proposalId, ProposalStatusUpdateRequest request);

    ProposalResponse getProposal(Long id);

    List<ProposalResponse> getProposals(Long taskId, Long professionalId);

    List<ProposalResponse> getProposalsForCurrentUser();

    List<ProposalResponse> getProposalsForTask(Long taskId);
}
