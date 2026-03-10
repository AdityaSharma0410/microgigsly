package com.adminservice.adminservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Admin profile entity. One admin record per user (identified by userId from user-service).
 * Stores admin-specific metadata; authentication is handled by the API gateway.
 */
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "admins", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id"})
})
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "display_name", length = 100)
    private String displayName;

    @Builder.Default
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public void updateProfile(String displayName, Boolean isActive) {
        if (displayName != null) {
            this.displayName = displayName.isBlank() ? null : displayName.trim();
        }
        if (isActive != null) {
            this.isActive = isActive;
        }
    }
}
