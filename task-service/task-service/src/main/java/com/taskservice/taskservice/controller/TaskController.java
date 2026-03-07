package com.taskservice.taskservice.controller;

import com.taskservice.taskservice.dto.AssignProfessionalRequest;
import com.taskservice.taskservice.dto.TaskCreateRequest;
import com.taskservice.taskservice.dto.TaskResponseDTO;
import com.taskservice.taskservice.dto.TaskStatusUpdateRequest;
import com.taskservice.taskservice.model.TaskStatus;
import com.taskservice.taskservice.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponseDTO createTask(@Valid @RequestBody TaskCreateRequest request) {
        return taskService.createTask(request);
    }

    @PostMapping("/{id}/status")
    public TaskResponseDTO updateStatus(
            @PathVariable @NonNull Long id,
            @Valid @RequestBody TaskStatusUpdateRequest request
    ) {
        return taskService.updateStatus(id, request);
    }

    @GetMapping("/{id}")
    public TaskResponseDTO getTask(@PathVariable @NonNull Long id) {
        return taskService.getTask(id);
    }

    @GetMapping
    public List<TaskResponseDTO> listTasks(
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long clientId
    ) {
        return taskService.getTasks(status, categoryId, clientId);
    }

    @GetMapping("/mine")
    public List<TaskResponseDTO> myTasks() {
        return taskService.getTasksForCurrentUser();
    }

    @PostMapping("/{id}/assign")
    public TaskResponseDTO assignProfessional(
            @PathVariable @NonNull Long id,
            @Valid @RequestBody AssignProfessionalRequest request
    ) {
        return taskService.assignProfessional(id, request.getProfessionalId());
    }
}