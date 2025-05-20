package com.gnomeshift.mfixflow.fixRequest;

import com.gnomeshift.mfixflow.defect.DefectRepository;
import com.gnomeshift.mfixflow.device.DeviceRepository;
import com.gnomeshift.mfixflow.statusLogger.StatusChangeEvent;
import com.gnomeshift.mfixflow.statusLogger.StatusLog;
import com.gnomeshift.mfixflow.statusLogger.StatusLogRepository;
import com.gnomeshift.mfixflow.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FixRequestService {
    @Autowired
    private DefectRepository defectRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private FixRequestRepository fixRequestRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatusLogRepository statusLogRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    private FixRequestDTO convertToDTO(FixRequest fixRequest) {
        FixRequestDTO dto = new FixRequestDTO();

        dto.setId(fixRequest.getId());
        dto.setTitle(fixRequest.getTitle());
        dto.setDescription(fixRequest.getDescription());
        dto.setStatus(fixRequest.getStatus());

        if (fixRequest.getAssignee() != null) {
            dto.setAssigneeId(fixRequest.getAssignee().getId());
        }

        if (fixRequest.getDevice() != null) {
            dto.setDeviceId(fixRequest.getDevice().getId());
        }

        if (fixRequest.getDefect() != null) {
            dto.setDefectId(fixRequest.getDefect().getId());
        }

        return dto;
    }

    public void updateStatus(FixRequest fixRequest, FixRequestDTO fixRequestDTO,  FixRequestStatus status) {
        if (!fixRequest.getStatus().equals(status)) {
            StatusLog log = new StatusLog();

            log.setFixRequest(fixRequest);
            log.setStatus(status);
            statusLogRepository.save(log);
            fixRequest.setStatus(status);
            eventPublisher.publishEvent(new StatusChangeEvent(this, fixRequestDTO.getId(), status, LocalDateTime.now()));
        }
    }

    public List<FixRequestDTO> getAllRequests() {
        return fixRequestRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public FixRequestDTO getRequestById(long id) {
        return fixRequestRepository.findById(id).map(this::convertToDTO)
                .orElseThrow(() -> new EntityNotFoundException("Fix request not found"));
    }

    public List<FixRequestDTO> getAllRequestsByDeviceId(long id) {
        return fixRequestRepository.findAllByDeviceId(id).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<FixRequestDTO> getAllRequestsByAssigneeId(long id) {
        return fixRequestRepository.findAllByAssigneeId(id).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<FixRequestDTO> getAllRequestsByDefectId(long id) {
        return fixRequestRepository.findAllByDefectId(id).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private FixRequestDTO fillFixRequestDTO(FixRequestDTO fixRequestDTO, FixRequest fixRequest) {
        fixRequest.setTitle(fixRequestDTO.getTitle());
        fixRequest.setDescription(fixRequestDTO.getDescription());
        fixRequest.setStatus(fixRequestDTO.getStatus());

        fixRequest.setAssignee(userRepository.findById(fixRequestDTO.getAssigneeId())
                .orElseThrow(() -> new EntityNotFoundException("User not found")));

        fixRequest.setDevice(deviceRepository.findById(fixRequestDTO.getDeviceId())
                .orElseThrow(() -> new EntityNotFoundException("Device not found")));

        fixRequest.setDefect(defectRepository.findById(fixRequestDTO.getDefectId())
                .orElseThrow(() -> new EntityNotFoundException("Defect not found")));

        return convertToDTO(fixRequestRepository.save(fixRequest));
    }

    public FixRequestDTO addRequest(FixRequestDTO fixRequestDTO) {
        FixRequest fixRequest = new FixRequest();
        return fillFixRequestDTO(fixRequestDTO, fixRequest);
    }

    public FixRequestDTO updateRequest(long id, FixRequestDTO fixRequestDTO) {
        FixRequest fixRequest = fixRequestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fix request not found"));
        updateStatus(fixRequest, fixRequestDTO, fixRequestDTO.getStatus());
        return fillFixRequestDTO(fixRequestDTO, fixRequest);
    }

    public void deleteRequest(long id) {
        if (!fixRequestRepository.existsById(id)) {
            throw new EntityNotFoundException("Fix request not found");
        }

        fixRequestRepository.deleteById(id);
    }
}
