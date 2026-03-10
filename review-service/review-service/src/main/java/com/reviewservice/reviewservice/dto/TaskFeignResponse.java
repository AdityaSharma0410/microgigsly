package com.reviewservice.reviewservice.dto;

import lombok.Builder;

@Builder
public record TaskFeignResponse(
        Long id,
        String title,
        Long clientId,
        Long assignedProfessionalId,
        String status
) {}
