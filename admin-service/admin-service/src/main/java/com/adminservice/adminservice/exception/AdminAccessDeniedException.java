package com.adminservice.adminservice.exception;
public class AdminAccessDeniedException extends RuntimeException {
    public AdminAccessDeniedException(String message) { super(message); }
}
