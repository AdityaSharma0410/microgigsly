package com.proposalservice.proposalservice.dto;

import lombok.Builder;

@Builder
public record UserFeignResponse(
        Long id,
        String fullName,
        String role,
        Boolean isActive
) {}
