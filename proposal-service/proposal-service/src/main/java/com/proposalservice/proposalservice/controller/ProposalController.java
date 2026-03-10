package com.proposalservice.proposalservice.controller;

import com.proposalservice.proposalservice.dto.ProposalRequest;
import com.proposalservice.proposalservice.dto.ProposalResponse;
import com.proposalservice.proposalservice.dto.ProposalStatusUpdateRequest;
import com.proposalservice.proposalservice.service.ProposalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proposals")
@RequiredArgsConstructor
public class ProposalController {

    private final ProposalService proposalService;

    @PostMapping
    public ResponseEntity<ProposalResponse> createProposal(
            @Valid @RequestBody ProposalRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(proposalService.createProposal(request));
    }

    @PostMapping("/{id}/status")
    public ResponseEntity<ProposalResponse> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody ProposalStatusUpdateRequest request
    ) {
        return ResponseEntity.ok(proposalService.updateStatus(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProposalResponse> getProposal(@PathVariable Long id) {
        return ResponseEntity.ok(proposalService.getProposal(id));
    }

    @GetMapping
    public ResponseEntity<List<ProposalResponse>> getProposals(
            @RequestParam(required = false) Long taskId,
            @RequestParam(required = false) Long professionalId
    ) {
        return ResponseEntity.ok(proposalService.getProposals(taskId, professionalId));
    }

    @GetMapping("/mine")
    public ResponseEntity<List<ProposalResponse>> getMyProposals() {
        return ResponseEntity.ok(proposalService.getProposalsForCurrentUser());
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<ProposalResponse>> getProposalsForTask(@PathVariable Long taskId) {
        return ResponseEntity.ok(proposalService.getProposalsForTask(taskId));
    }
}
