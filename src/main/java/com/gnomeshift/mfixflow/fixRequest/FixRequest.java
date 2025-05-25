package com.gnomeshift.mfixflow.fixRequest;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gnomeshift.mfixflow.defect.Defect;
import com.gnomeshift.mfixflow.device.Device;
import com.gnomeshift.mfixflow.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "fix_requests")
@Data
public class FixRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Title must be specified")
    @Size(max = 100)
    private String title;

    @NotBlank(message = "Description must be specified")
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    private FixRequestStatus status;

    @NotNull(message = "User must be specified")
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference(value = "assigneeReference")
    private User assignee;

    @NotNull(message = "Device must be specified")
    @ManyToOne
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    @NotNull(message = "Defect must be specified")
    @ManyToOne
    @JoinColumn(name = "defect_id", nullable = false)
    private Defect defect;

    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
