package com.taskservice.taskservice.service.impl;

import com.taskservice.taskservice.client.CategoryClient;
import com.taskservice.taskservice.client.UserClient;
import com.taskservice.taskservice.dto.TaskCreateRequest;
import com.taskservice.taskservice.dto.TaskResponseDTO;
import com.taskservice.taskservice.dto.TaskStatusUpdateRequest;
import com.taskservice.taskservice.exception.BadRequestException;
import com.taskservice.taskservice.exception.ResourceNotFoundException;
import com.taskservice.taskservice.mapper.TaskMapper;
import com.taskservice.taskservice.model.Task;
import com.taskservice.taskservice.model.TaskStatus;
import com.taskservice.taskservice.repository.TaskRepository;
import com.taskservice.taskservice.security.CurrentUserService;
import com.taskservice.taskservice.service.TaskService;
import org.springframework.transaction.annotation.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserClient userClient;
    private final CategoryClient categoryClient;
    private final CurrentUserService currentUserService;

    @Override
    @Transactional
    public TaskResponseDTO createTask(TaskCreateRequest request) {

        validateBudgetRange(request);

        Long currentUserId = currentUserService.getCurrentUserId();
        String role = currentUserService.getCurrentUserRole();

        if (!"CLIENT".equals(role) && !"ADMIN".equals(role)) {
            throw new BadRequestException("Only clients or admins can post tasks");
        }

        if (!categoryClient.categoryExists(request.getCategoryId())) {
            throw new ResourceNotFoundException("Category not found");
        }

        // DTO → Entity
        Task task = TaskMapper.toEntity(request);

        // Inject authenticated client
        task.builder().clientId(currentUserId);

        Long professionalId = request.getAssignedProfessionalId();

        if (professionalId != null) {

            validateProfessional(professionalId);

            task.assignProfessional(professionalId);
            task.updateStatus(TaskStatus.IN_PROGRESS);

        } else {
            task.updateStatus(TaskStatus.OPEN);
        }

        Task saved = taskRepository.save(task);

        return TaskMapper.toDTO(saved);
    }
    @Override
    @Transactional
    public TaskResponseDTO updateStatus(
            @NonNull Long taskId,
            TaskStatusUpdateRequest request
    ) {

        Task task = findTask(taskId);

        Long currentUserId = currentUserService.getCurrentUserId();
        String role = currentUserService.getCurrentUserRole();

        if (!role.equals("CLIENT") && !role.equals("ADMIN")) {
            throw new BadRequestException("Only clients or admins can update task status");
        }

        if (!role.equals("ADMIN") && !task.getClientId().equals(currentUserId)) {
            throw new BadRequestException("You are not allowed to update this task");
        }

        TaskStatus newStatus = request.getStatus();

        if (newStatus == TaskStatus.IN_PROGRESS || newStatus == TaskStatus.COMPLETED) {

            Long professionalId = request.getAssignedProfessionalId();

            if (task.getAssignedProfessionalId() == null && professionalId == null) {
                throw new BadRequestException("Assign a professional first");
            }

            if (professionalId != null) {

                validateProfessional(professionalId);
                task.assignProfessional(professionalId);

            }

            if (newStatus == TaskStatus.COMPLETED) {
                task.builder().completedAt(LocalDateTime.now());
            }
        }

        task.updateStatus(newStatus);

        return TaskMapper.toDTO(taskRepository.save(task));
    }

    @Override
    @Transactional(readOnly = true)
    public TaskResponseDTO getTask(@NonNull Long id) {
        return TaskMapper.toDTO(findTask(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponseDTO> getTasks(
            TaskStatus status,
            Long categoryId,
            Long clientId
    ) {

        List<Task> tasks = taskRepository.findAll();

        return tasks.stream()
                .filter(t -> status == null || t.getStatus() == status)
                .filter(t -> categoryId == null || t.getCategoryId().equals(categoryId))
                .filter(t -> clientId == null || t.getClientId().equals(clientId))
                .map(TaskMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponseDTO> getTasksForCurrentUser() {

        Long userId = currentUserService.getCurrentUserId();
        String role = currentUserService.getCurrentUserRole();

        List<Task> tasks;

        if (role.equals("CLIENT") || role.equals("ADMIN")) {
            tasks = taskRepository.findByClientId(userId);
        } else {
            tasks = taskRepository.findByAssignedProfessionalId(userId);
        }

        return tasks.stream()
                .map(TaskMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public TaskResponseDTO assignProfessional(
            @NonNull Long taskId,
            @NonNull Long professionalId
    ) {

        Task task = findTask(taskId);

        Long currentUserId = currentUserService.getCurrentUserId();
        String role = currentUserService.getCurrentUserRole();

        if (!role.equals("CLIENT") && !role.equals("ADMIN")) {
            throw new BadRequestException("Only clients or admins can assign professionals");
        }

        if (!role.equals("ADMIN") && !task.getClientId().equals(currentUserId)) {
            throw new BadRequestException("You are not allowed to assign professionals to this task");
        }

        if (task.getStatus() == TaskStatus.COMPLETED) {
            throw new BadRequestException("Cannot assign professional to completed task");
        }

        validateProfessional(professionalId);

        task.assignProfessional(professionalId);

        if (task.getStatus() == TaskStatus.OPEN) {
            task.updateStatus(TaskStatus.IN_PROGRESS);
        }

        return TaskMapper.toDTO(taskRepository.save(task));
    }

    private Task findTask(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + id));
    }

    private void validateProfessional(Long professionalId) {

        var user = userClient.getUser(professionalId);

        if (!user.role().equals("PROFESSIONAL")) {
            throw new BadRequestException("User is not a professional");
        }
    }

    private void validateBudgetRange(TaskCreateRequest request) {

        if (request.getBudgetMin() != null
                && request.getBudgetMax() != null
                && request.getBudgetMin().compareTo(request.getBudgetMax()) > 0) {

            throw new BadRequestException("Minimum budget cannot exceed maximum budget");
        }
    }
}