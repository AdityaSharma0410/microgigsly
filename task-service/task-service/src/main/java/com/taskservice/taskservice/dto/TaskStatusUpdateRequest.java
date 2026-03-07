package com.taskservice.taskservice.dto;

import com.taskservice.taskservice.model.TaskStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TaskStatusUpdateRequest {

    @NotNull(message = "Task status is required")
    private TaskStatus status;

    /*
        Optional professional assignment when
        moving task to IN_PROGRESS or COMPLETED
     */
    private Long assignedProfessionalId;

}