package com.proposalservice.proposalservice.exception;

public class ProposalNotFoundException extends RuntimeException {

    public ProposalNotFoundException(Long id) {
        super("Proposal not found with id: " + id);
    }
}
