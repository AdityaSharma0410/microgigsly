package com.taskservice.taskservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AssignProfessionalRequest {

    @NotNull(message = "Professional id is required")
    private Long professionalId;

}