package com.gnomeshift.mfixflow.fixRequest;

import com.gnomeshift.mfixflow.statusLogger.StatusLogDTO;
import com.gnomeshift.mfixflow.statusLogger.StatusLogService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class FixRequestController {
    @Autowired
    private FixRequestService fixRequestService;

    @Autowired
    private StatusLogService statusLogService;

    @GetMapping("/{id}/logs")
    public List<StatusLogDTO> getAllLogs(@PathVariable int id) {
        return statusLogService.getAllLogs(id);
    }

    @DeleteMapping("/logs")
    public ResponseEntity<StatusLogDTO> deleteLogs() {
        try {
            statusLogService.deleteAllLogs();
            return ResponseEntity.ok().build();
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public List<FixRequestDTO> getAllRequests() {
        return fixRequestService.getAllRequests();
    }

    @GetMapping("/{id}")
    public FixRequestDTO getRequestById(@PathVariable int id) {
        return fixRequestService.getRequestById(id);
    }

    @GetMapping("/by-device/{id}")
    public List<FixRequestDTO> getAllRequestsByDeviceId(@PathVariable int id) {
        return fixRequestService.getAllRequestsByDeviceId(id);
    }

    @GetMapping("/by-assignee/{id}")
    public List<FixRequestDTO> getAllRequestsByAssigneeId(@PathVariable int id) {
        return fixRequestService.getAllRequestsByAssigneeId(id);
    }

    @GetMapping("/by-defect/{id}")
    public List<FixRequestDTO> getAllRequestsByDefectId(@PathVariable int id) {
        return fixRequestService.getAllRequestsByDefectId(id);
    }

    @PostMapping
    public ResponseEntity<FixRequestDTO> addRequest(@RequestBody FixRequestDTO fixRequestDTO) {
        try {
            return ResponseEntity.ok(fixRequestService.addRequest(fixRequestDTO));
        }
        catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<FixRequestDTO> updateRequest(@PathVariable int id, @RequestBody FixRequestDTO fixRequestDTO) {
        try {
            return ResponseEntity.ok(fixRequestService.updateRequest(id, fixRequestDTO));
        }
        catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FixRequestDTO> deleteRequest(@PathVariable int id) {
        try {
            fixRequestService.deleteRequest(id);
            return ResponseEntity.ok().build();
        }
        catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
