package com.adminservice.adminservice.exception;
public class AdminNotFoundException extends RuntimeException {
    public AdminNotFoundException(String message) { super(message); }
    public AdminNotFoundException(Long userId) { super("Admin record not found for user id: " + userId); }
}
