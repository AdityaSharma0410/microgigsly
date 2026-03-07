package com.taskservice.taskservice.dto;

import lombok.Builder;

@Builder
public record UserFeignResponse(
        Long id,
        String role,
        Boolean isActive
) {}