package com.taskservice.taskservice.mapper;

import com.taskservice.taskservice.dto.TaskCreateRequest;
import com.taskservice.taskservice.dto.TaskResponseDTO;
import com.taskservice.taskservice.model.Task;

public final class TaskMapper {

    public static Task toEntity(TaskCreateRequest request) {

        return Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .categoryId(request.getCategoryId())
                .budgetMin(request.getBudgetMin())
                .budgetMax(request.getBudgetMax())
                .deadline(request.getDeadline())
                .requiredSkills(request.getRequiredSkills())
                .location(request.getLocation())
                .isRemote(request.getIsRemote())
                .estimatedDuration(request.getEstimatedDuration())
                .build();
    }

    public static TaskResponseDTO toDTO(Task task) {

        return TaskResponseDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .clientId(task.getClientId())
                .categoryId(task.getCategoryId())
                .budgetMin(task.getBudgetMin())
                .budgetMax(task.getBudgetMax())
                .status(task.getStatus().name())
                .priority(task.getPriority().name())
                .deadline(task.getDeadline())
                .requiredSkills(task.getRequiredSkills())
                .location(task.getLocation())
                .isRemote(task.getIsRemote())
                .estimatedDuration(task.getEstimatedDuration())
                .assignedProfessionalId(task.getAssignedProfessionalId())
                .createdAt(task.getCreatedAt())
                .build();
    }
}