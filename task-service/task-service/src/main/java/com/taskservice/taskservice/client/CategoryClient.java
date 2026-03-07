package com.taskservice.taskservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "category-service", path = "/api/categories")
public interface CategoryClient {

    @GetMapping("/{id}/exists")
    boolean categoryExists(@PathVariable Long id);

}