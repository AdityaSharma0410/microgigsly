package com.contactservice.contactservice.dto;

import com.contactservice.contactservice.model.QueryStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ContactResponseRequest {

    @NotBlank(message = "Response message is required")
    private String response;

    @NotNull(message = "Status is required")
    private QueryStatus status;
}
