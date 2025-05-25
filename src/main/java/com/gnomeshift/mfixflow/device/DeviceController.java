package com.gnomeshift.mfixflow.device;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    @GetMapping
    public List<DeviceDTO> getAllDevices() {
        return deviceService.getAllDevices();
    }

    @GetMapping("/{id}")
    public DeviceDTO getDeviceById(@PathVariable long id) {
        return deviceService.getDeviceById(id);
    }

    @PostMapping
    public ResponseEntity<DeviceDTO> addDevice(@RequestBody DeviceDTO deviceDTO) {
        try {
            return ResponseEntity.ok(deviceService.addDevice(deviceDTO));
        }
        catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeviceDTO> updateDevice(@PathVariable long id, @RequestBody DeviceDTO deviceDTO) {
        try {
            return ResponseEntity.ok(deviceService.updateDevice(id, deviceDTO));
        }
        catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeviceDTO> deleteDevice(@PathVariable long id) {
        try {
            deviceService.deleteDevice(id);
            return ResponseEntity.noContent().build();
        }
        catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
