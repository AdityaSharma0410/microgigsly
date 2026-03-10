package com.adminservice.adminservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** DTO used by admin-service when calling user-service to create a new user. */
@Getter
@Setter
@NoArgsConstructor
public class UserCreateRequest {

    @NotBlank
    private String fullName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6)
    private String password;

    private String role = "ADMIN";

    private String bio;
}
