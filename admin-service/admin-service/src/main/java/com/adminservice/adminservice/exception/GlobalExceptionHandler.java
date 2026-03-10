package com.adminservice.adminservice.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AdminNotFoundException.class)
    public ResponseEntity<APIErrorResponse> handleAdminNotFound(
            AdminNotFoundException ex, HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(buildError(
                HttpStatus.NOT_FOUND, "Admin Not Found", ex.getMessage(), request.getRequestURI()
        ));
    }

    @ExceptionHandler(AdminAccessDeniedException.class)
    public ResponseEntity<APIErrorResponse> handleAdminAccessDenied(
            AdminAccessDeniedException ex, HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(buildError(
                HttpStatus.FORBIDDEN, "Access Denied", ex.getMessage(), request.getRequestURI()
        ));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<APIErrorResponse> handleBadRequest(
            BadRequestException ex, HttpServletRequest request
    ) {
        return ResponseEntity.badRequest().body(buildError(
                HttpStatus.BAD_REQUEST, "Bad Request", ex.getMessage(), request.getRequestURI()
        ));
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<APIErrorResponse> handleConflict(
            ConflictException ex, HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(buildError(
                HttpStatus.CONFLICT, "Conflict", ex.getMessage(), request.getRequestURI()
        ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIErrorResponse> handleValidation(
            MethodArgumentNotValidException ex, HttpServletRequest request
    ) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return ResponseEntity.badRequest().body(buildError(
                HttpStatus.BAD_REQUEST, "Validation Error", message, request.getRequestURI()
        ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIErrorResponse> handleGeneral(
            Exception ex, HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(buildError(
                HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", ex.getMessage(), request.getRequestURI()
        ));
    }

    private APIErrorResponse buildError(HttpStatus status, String error, String message, String path) {
        return APIErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(error)
                .message(message)
                .path(path)
                .build();
    }
}
