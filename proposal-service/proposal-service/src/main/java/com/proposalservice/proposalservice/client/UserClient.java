package com.proposalservice.proposalservice.client;

import com.proposalservice.proposalservice.dto.UserFeignResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", path = "/api/users")
public interface UserClient {

    @GetMapping("/{id}")
    UserFeignResponse getUser(@PathVariable Long id);
}
