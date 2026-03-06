package com.userservice.userservice.mapper;

import com.userservice.userservice.dto.ProfessionalProfileRequest;
import com.userservice.userservice.dto.UserRequest;
import com.userservice.userservice.dto.UserResponse;
import com.userservice.userservice.dto.UserStatusRequest;
import com.userservice.userservice.model.User;

public class UserMapper {

    private UserMapper() {}

    public static User toEntity(UserRequest request) {

        User.UserBuilder builder = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(request.getPassword())
                .mobile(request.getMobile());

        if (request.getRole() != null) {
            builder.role(request.getRole());
        }

        return builder.build();
    }
    public static UserResponse toResponse(User user) {

        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .mobile(user.getMobile())
                .primaryCategory(user.getPrimaryCategory())
                .skills(user.getSkills())
                .hourlyRate(user.getHourlyRate())
                .location(user.getLocation())
                .role(user.getRole())
                .bio(user.getBio())
                .profilePictureUrl(user.getProfilePictureUrl())
                .isVerified(user.getIsVerified())
                .isActive(user.getIsActive())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public static void updateProfessionalProfile(
            User user,
            ProfessionalProfileRequest request
    ) {

        user.updateProfessionalProfile(
                request.getPrimaryCategory(),
                request.getSkills(),
                request.getHourlyRate(),
                request.getLocation(),
                request.getBio(),
                request.getProfilePictureUrl()
        );
    }

    public static void updateUserStatus(
            User user,
            UserStatusRequest request
    ) {

        user.updateStatus(
                request.getIsVerified(),
                request.getIsActive()
        );
    }
}