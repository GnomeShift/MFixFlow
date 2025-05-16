package com.gnomeshift.mfixflow.fixRequest;

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

    @GetMapping
    public List<FixRequestDTO> getAllRequests() {
        return fixRequestService.getAllRequests();
    }

    @GetMapping("/{id}")
    public FixRequestDTO getRequestById(@PathVariable int id) {
        return fixRequestService.getRequestById(id);
    }

    @GetMapping("/bydevice/{id}")
    public List<FixRequestDTO> getAllRequestsByDeviceId(@PathVariable int id) {
        return fixRequestService.getAllRequestsByDeviceId(id);
    }

    @GetMapping("/bymaster/{id}")
    public List<FixRequestDTO> getAllRequestsByMasterId(@PathVariable int id) {
        return fixRequestService.getAllRequestsByMasterId(id);
    }

    @GetMapping("/bydefect/{id}")
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
