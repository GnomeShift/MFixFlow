package com.gnomeshift.mfixflow.statusLogger;

import com.gnomeshift.mfixflow.fixRequest.FixRequestStatus;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

@Getter
public class StatusChangeEvent extends ApplicationEvent {
    private final long fixRequestId;
    private FixRequestStatus status;
    private LocalDateTime eventTimestamp;

    public StatusChangeEvent(Object source, long fixRequestId, FixRequestStatus status, LocalDateTime eventTimestamp) {
        super(source);
        this.fixRequestId = fixRequestId;
        this.status = status;
        this.eventTimestamp = eventTimestamp;
    }
}
