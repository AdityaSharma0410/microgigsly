package com.userservice.userservice.dto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserStatusRequest {

    @NotNull(message = "Verification status must be provided")
    private Boolean isVerified;

    @NotNull(message = "Active status must be provided")
    private Boolean isActive;
}