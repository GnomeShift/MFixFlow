package com.gnomeshift.mfixflow.statusLogger;

import com.gnomeshift.mfixflow.fixRequest.FixRequest;
import com.gnomeshift.mfixflow.fixRequest.FixRequestStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "status_logs")
@Data
public class StatusLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "fix_request_id", nullable = false)
    private FixRequest fixRequest;

    @NotNull
    @Enumerated(EnumType.STRING)
    private FixRequestStatus status;

    @NotNull
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
