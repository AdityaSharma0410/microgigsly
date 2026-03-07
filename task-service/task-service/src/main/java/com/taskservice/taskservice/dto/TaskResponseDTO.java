package com.taskservice.taskservice.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class TaskResponseDTO {

    private Long id;
    private String title;
    private String description;

    private Long clientId;
    private Long categoryId;

    private BigDecimal budgetMin;
    private BigDecimal budgetMax;

    private String status;
    private String priority;

    private LocalDateTime deadline;

    private String requiredSkills;
    private String location;
    private Boolean isRemote;

    private String estimatedDuration;

    private Long assignedProfessionalId;

    private LocalDateTime createdAt;
}