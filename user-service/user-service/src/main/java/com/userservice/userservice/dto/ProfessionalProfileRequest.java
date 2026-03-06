package com.userservice.userservice.dto;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalProfileRequest {

    @Size(max = 100, message = "Primary category must not exceed 100 characters")
    private String primaryCategory;

    @Size(max = 500, message = "Skills must not exceed 500 characters")
    private String skills;

    @DecimalMin(value = "0.0", inclusive = false, message = "Hourly rate must be greater than 0")
    private BigDecimal hourlyRate;

    @Size(max = 150, message = "Location must not exceed 150 characters")
    private String location;

    @Size(max = 1000, message = "Bio must not exceed 1000 characters")
    private String bio;

    @Pattern(
            regexp = "^(http|https)://.*$",
            message = "Profile picture URL must be valid"
    )
    private String profilePictureUrl;
}