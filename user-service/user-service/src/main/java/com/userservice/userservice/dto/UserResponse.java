package com.userservice.userservice.dto;
import com.userservice.userservice.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;

    private String fullName;

    private String email;

    private String mobile;

    private String primaryCategory;

    private String skills;

    private BigDecimal hourlyRate;

    private String location;

    private UserRole role;

    private String bio;

    private String profilePictureUrl;

    private Boolean isVerified;

    private Boolean isActive;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}