package com.reviewservice.reviewservice.config;

import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
