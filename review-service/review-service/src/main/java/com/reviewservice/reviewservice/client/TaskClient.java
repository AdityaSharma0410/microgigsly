package com.reviewservice.reviewservice.client;

import com.reviewservice.reviewservice.dto.TaskFeignResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "task-service", path = "/api/tasks")
public interface TaskClient {

    @GetMapping("/{id}")
    TaskFeignResponse getTask(@PathVariable Long id);
}
