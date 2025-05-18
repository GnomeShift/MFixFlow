package com.gnomeshift.mfixflow.device;

import org.springframework.beans.factory.annotation.Autowired;
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
    public DeviceDTO addDevice(@RequestBody DeviceDTO deviceDTO) {
        return deviceService.addDevice(deviceDTO);
    }

    @PutMapping("/{id}")
    public DeviceDTO updateDevice(@PathVariable long id, @RequestBody DeviceDTO deviceDTO) {
        return deviceService.updateDevice(id, deviceDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteDevice(@PathVariable long id) {
        deviceService.deleteDevice(id);
    }
}
