package com.proposalservice.proposalservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AssignProfessionalRequest {

    @NotNull(message = "Professional id is required")
    private Long professionalId;
}
