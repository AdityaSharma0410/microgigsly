package com.taskservice.taskservice.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class TaskCreateRequest {

    @NotBlank
    @Size(min = 5, max = 200)
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private Long categoryId;

    @DecimalMin("0.0")
    private BigDecimal budgetMin;

    @DecimalMin("0.0")
    private BigDecimal budgetMax;

    private LocalDateTime deadline;

    private String requiredSkills;

    private String location;

    private Boolean isRemote;

    private String estimatedDuration;

    // optional assignment at creation
    private Long assignedProfessionalId;
}