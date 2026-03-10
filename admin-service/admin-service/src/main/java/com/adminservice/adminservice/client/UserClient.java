package com.adminservice.adminservice.client;

import com.adminservice.adminservice.dto.UserCreateRequest;
import com.adminservice.adminservice.dto.UserFeignResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user-service", path = "/api/users")
public interface UserClient {

    @GetMapping("/{id}")
    UserFeignResponse getUser(@PathVariable Long id);

    @PostMapping
    UserFeignResponse createUser(@RequestBody UserCreateRequest request);
}
