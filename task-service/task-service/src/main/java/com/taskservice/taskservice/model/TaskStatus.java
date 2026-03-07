package com.taskservice.taskservice.model;

public enum TaskStatus {
    OPEN,           // Task is open for proposals
    IN_PROGRESS,    // Task is assigned and in progress
    COMPLETED,      // Task is completed
    CANCELLED,      // Task is cancelled
    CLOSED          // Task is closed (no longer accepting proposals)
}

