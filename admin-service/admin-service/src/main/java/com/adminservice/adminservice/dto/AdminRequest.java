package com.adminservice.adminservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminRequest {

    private String displayName;

    private Boolean isActive;
}
