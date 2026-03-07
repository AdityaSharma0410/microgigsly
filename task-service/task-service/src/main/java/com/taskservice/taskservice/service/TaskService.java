package com.taskservice.taskservice.service;

import com.taskservice.taskservice.dto.TaskCreateRequest;
import com.taskservice.taskservice.dto.TaskResponseDTO;
import com.taskservice.taskservice.dto.TaskStatusUpdateRequest;
import com.taskservice.taskservice.model.TaskStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {

    TaskResponseDTO createTask(TaskCreateRequest request);

    TaskResponseDTO updateStatus(Long taskId, TaskStatusUpdateRequest request);

    TaskResponseDTO getTask(Long id);

    List<TaskResponseDTO> getTasks(TaskStatus status, Long categoryId, Long clientId);

    List<TaskResponseDTO> getTasksForCurrentUser();

    TaskResponseDTO assignProfessional(Long taskId, Long professionalId);
}