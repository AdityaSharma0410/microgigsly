package com.adminservice.adminservice.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminResponse {

    private Long id;
    private Long userId;
    private String userEmail;
    private String fullName;
    private String displayName;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
