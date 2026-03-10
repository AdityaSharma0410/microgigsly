package com.proposalservice.proposalservice.config;

import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Propagates the gateway-injected X-User-Id and X-User-Role headers
 * to downstream Feign calls so services can perform their own auth checks.
 */
@Configuration
@RequiredArgsConstructor
public class FeignHeaderInterceptor {

    private final HttpServletRequest httpServletRequest;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            String userId = httpServletRequest.getHeader("X-User-Id");
            String userRole = httpServletRequest.getHeader("X-User-Role");
            if (userId != null) {
                requestTemplate.header("X-User-Id", userId);
            }
            if (userRole != null) {
                requestTemplate.header("X-User-Role", userRole);
            }
        };
    }
}
