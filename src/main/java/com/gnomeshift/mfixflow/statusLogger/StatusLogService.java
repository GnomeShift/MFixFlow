package com.gnomeshift.mfixflow.statusLogger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatusLogService {
    @Autowired
    private StatusLogRepository statusLogRepository;

    private StatusLogDTO convertToDTO(StatusLog statusLog) {
        StatusLogDTO dto = new StatusLogDTO();

        dto.setId(statusLog.getId());

        if (statusLog.getFixRequest() != null) {
            dto.setFixRequestId(statusLog.getFixRequest().getId());
        }

        dto.setStatus(statusLog.getStatus());
        dto.setUpdatedAt(statusLog.getUpdatedAt());

        return dto;
    }

    public List<StatusLogDTO> getAllLogs(long id) {
        return statusLogRepository.findByFixRequestId(id).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public void deleteAllLogs() {
        try {
            statusLogRepository.deleteAll();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
