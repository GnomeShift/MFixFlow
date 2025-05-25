package com.gnomeshift.mfixflow.device;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeviceService {
    @Autowired
    private DeviceRepository deviceRepository;

    private DeviceDTO convertToDTO(Device device) {
        DeviceDTO deviceDTO = new DeviceDTO();

        deviceDTO.setId(device.getId());
        deviceDTO.setName(device.getName());
        deviceDTO.setDescription(device.getDescription());

        return deviceDTO;
    }

    public List<DeviceDTO> getAllDevices() {
        return deviceRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public DeviceDTO getDeviceById(long id) {
        return deviceRepository.findById(id).map(this::convertToDTO)
                .orElseThrow(() -> new EntityNotFoundException("Device not found"));
    }

    private DeviceDTO fillDeviceDTO(Device device, DeviceDTO deviceDTO) {
        device.setName(deviceDTO.getName());
        device.setDescription(deviceDTO.getDescription());

        return convertToDTO(deviceRepository.save(device));
    }

    public DeviceDTO addDevice(DeviceDTO deviceDTO) {
        Device device = new Device();
        return fillDeviceDTO(device, deviceDTO);
    }

    public DeviceDTO updateDevice(long id, DeviceDTO deviceDTO) {
        Device device = deviceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Device not found"));
        return fillDeviceDTO(device, deviceDTO);
    }

    public void deleteDevice(long id) {
        if (!deviceRepository.existsById(id)) {
            throw new EntityNotFoundException("Device not found");
        }

        deviceRepository.deleteById(id);
    }
}
