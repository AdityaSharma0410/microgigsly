package com.taskservice.taskservice.dto;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class TaskUpdateRequest {

    @Size(min = 5, max = 200)
    private String title;

    private String description;

    private Long categoryId;

    private BigDecimal budgetMin;

    private BigDecimal budgetMax;

    private LocalDateTime deadline;

    private String requiredSkills;

    private String location;

    private Boolean isRemote;

    private String estimatedDuration;
}