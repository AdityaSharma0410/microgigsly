package com.proposalservice.proposalservice.client;

import com.proposalservice.proposalservice.dto.TaskFeignResponse;
import com.proposalservice.proposalservice.dto.AssignProfessionalRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "task-service", path = "/api/tasks")
public interface TaskClient {

    @GetMapping("/{id}")
    TaskFeignResponse getTask(@PathVariable Long id);

    @PostMapping("/{id}/assign")
    TaskFeignResponse assignProfessional(
            @PathVariable Long id,
            @RequestBody AssignProfessionalRequest request
    );
}
