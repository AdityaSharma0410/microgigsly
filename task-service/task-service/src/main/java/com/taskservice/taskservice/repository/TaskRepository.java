package com.taskservice.taskservice.repository;

import com.taskservice.taskservice.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByClientId(Long clientId);

    List<Task> findByAssignedProfessionalId(Long professionalId);

}