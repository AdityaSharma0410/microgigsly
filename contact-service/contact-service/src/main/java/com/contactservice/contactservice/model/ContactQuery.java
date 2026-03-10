package com.contactservice.contactservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "contact_queries")
public class ContactQuery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank
    @Email
    @Column(nullable = false, length = 100)
    private String email;

    @Column(length = 15)
    private String mobile;

    @NotBlank
    @Column(name = "query_type", nullable = false, length = 50)
    private String queryType;

    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private QueryStatus status = QueryStatus.PENDING;

    @Column(name = "admin_response", columnDefinition = "TEXT")
    private String adminResponse;

    @Column(name = "responded_at")
    private LocalDateTime respondedAt;

    /** ID of the admin user (from user-service) who responded. */
    @Column(name = "responded_by_id")
    private Long respondedById;

    /** Name of the admin who responded (denormalized for display). */
    @Column(name = "responded_by_name", length = 100)
    private String respondedByName;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public void respond(String response, QueryStatus newStatus, Long adminId, String adminName) {
        this.adminResponse = response;
        this.status = newStatus;
        this.respondedAt = LocalDateTime.now();
        this.respondedById = adminId;
        this.respondedByName = adminName;
    }
}
