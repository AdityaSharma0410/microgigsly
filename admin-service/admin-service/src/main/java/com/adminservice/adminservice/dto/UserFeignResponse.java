package com.adminservice.adminservice.dto;

import lombok.Builder;

@Builder
public record UserFeignResponse(
        Long id,
        String fullName,
        String email,
        String role,
        Boolean isActive,
        Boolean isVerified
) {}
