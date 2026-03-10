package com.contactservice.contactservice.dto;

import com.contactservice.contactservice.model.QueryStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactQueryResponse {

    private Long id;
    private String name;
    private String email;
    private String mobile;
    private String queryType;
    private String message;
    private QueryStatus status;
    private String adminResponse;
    private Long respondedById;
    private String respondedByName;
    private LocalDateTime respondedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
