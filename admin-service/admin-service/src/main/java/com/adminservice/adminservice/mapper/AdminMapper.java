package com.adminservice.adminservice.mapper;

import com.adminservice.adminservice.dto.AdminResponse;
import com.adminservice.adminservice.dto.UserFeignResponse;
import com.adminservice.adminservice.model.Admin;

public final class AdminMapper {

    private AdminMapper() {}

    public static AdminResponse toResponse(Admin admin, UserFeignResponse user) {
        return AdminResponse.builder()
                .id(admin.getId())
                .userId(admin.getUserId())
                .userEmail(user != null ? user.email() : null)
                .fullName(user != null ? user.fullName() : null)
                .displayName(admin.getDisplayName())
                .isActive(admin.getIsActive())
                .createdAt(admin.getCreatedAt())
                .updatedAt(admin.getUpdatedAt())
                .build();
    }

    public static AdminResponse toResponse(Admin admin) {
        return AdminResponse.builder()
                .id(admin.getId())
                .userId(admin.getUserId())
                .displayName(admin.getDisplayName())
                .isActive(admin.getIsActive())
                .createdAt(admin.getCreatedAt())
                .updatedAt(admin.getUpdatedAt())
                .build();
    }
}
