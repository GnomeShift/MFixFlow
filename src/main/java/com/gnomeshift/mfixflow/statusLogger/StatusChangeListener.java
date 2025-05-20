package com.gnomeshift.mfixflow.statusLogger;

import com.gnomeshift.mfixflow.fixRequest.FixRequestStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class StatusChangeListener implements ApplicationListener<StatusChangeEvent> {
    private final Logger logger = LoggerFactory.getLogger(StatusChangeListener.class);

    @Async
    @Override
    public void onApplicationEvent(StatusChangeEvent event) {
        long fixRequestId = event.getFixRequestId();
        FixRequestStatus status = event.getStatus();

        logger.info("Заявка: {} - Новый статус: {}", fixRequestId, status);
    }
}
