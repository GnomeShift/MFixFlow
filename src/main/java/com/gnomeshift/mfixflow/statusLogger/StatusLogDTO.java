package com.gnomeshift.mfixflow.statusLogger;

import com.gnomeshift.mfixflow.fixRequest.FixRequestStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StatusLogDTO {
    private long id;
    private long fixRequestId;
    private FixRequestStatus status;
    private LocalDateTime updatedAt;
}
