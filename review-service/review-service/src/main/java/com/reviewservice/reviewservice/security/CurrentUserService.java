package com.reviewservice.reviewservice.security;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserService {

    private final HttpServletRequest request;

    public Long getCurrentUserId() {
        String userId = request.getHeader("X-User-Id");
        if (userId == null) {
            throw new IllegalStateException("Missing X-User-Id header from gateway");
        }
        return Long.parseLong(userId);
    }

    public String getCurrentUserRole() {
        String role = request.getHeader("X-User-Role");
        if (role == null) {
            throw new IllegalStateException("Missing X-User-Role header from gateway");
        }
        return role;
    }
}
