package com.taskservice.taskservice.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<APIErrorResponse> handleTaskNotFound(
            TaskNotFoundException ex,
            HttpServletRequest request
    ) {

        APIErrorResponse error = APIErrorResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(InvalidTaskOperationException.class)
    public ResponseEntity<APIErrorResponse> handleInvalidOperation(
            InvalidTaskOperationException ex,
            HttpServletRequest request
    ) {

        APIErrorResponse error = APIErrorResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.badRequest().body(error);
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<APIErrorResponse> handleBadRequest(
            BadRequestException ex,
            HttpServletRequest request
    ) {

        APIErrorResponse error = APIErrorResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.badRequest().body(error);
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIErrorResponse> handleNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request
    ) {

        APIErrorResponse error = APIErrorResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}