package com.taskservice.taskservice.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class APIErrorResponse {

    private String message;
    private String path;
    private int status;
    private LocalDateTime timestamp;
}