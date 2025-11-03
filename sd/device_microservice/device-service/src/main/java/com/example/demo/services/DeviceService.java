package com.example.demo.services;

import com.example.demo.dtos.DeviceDTO;
import com.example.demo.dtos.DeviceDetailsDTO;
import com.example.demo.dtos.builders.DeviceBuilder;
import com.example.demo.entities.Device;
import com.example.demo.handlers.exceptions.model.ResourceNotFoundException;
import com.example.demo.repositories.DeviceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DeviceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceService.class);

    private final DeviceRepository deviceRepository;
    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public List<DeviceDTO> findDevices() {
        return deviceRepository.findAll().stream()
                .map(DeviceBuilder::toDeviceDTO)
                .collect(Collectors.toList());
    }

    public DeviceDetailsDTO findDeviceById(UUID id) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Device with id {} not found", id);
                    throw new ResourceNotFoundException("Device with id: " + id);
                });
        return DeviceBuilder.toDeviceDetailsDTO(device);
    }

    public UUID insert(DeviceDetailsDTO deviceDTO) {
        Device device = DeviceBuilder.toEntity(deviceDTO);
        device = deviceRepository.save(device);
        return device.getId();
    }

    public void update(UUID id, DeviceDetailsDTO deviceDTO) {
        Device existing = deviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Device with id: " + id));

        existing.setName(deviceDTO.getName());
        existing.setMaxConsumption(deviceDTO.getMaxConsumption());
        existing.setOwnerId(deviceDTO.getOwnerId());

        deviceRepository.save(existing);
        LOGGER.debug("Device with id {} updated", id);
    }

    public void delete(UUID id) {
        if (!deviceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Device with id: " + id);
        }
        deviceRepository.deleteById(id);
        LOGGER.debug("Device with id {} deleted", id);
    }

    // 🚀 NOU: Metodă pentru a găsi device-urile unui singur utilizator
    public List<DeviceDTO> findDevicesByOwnerId(UUID ownerId) {
        return deviceRepository.findByOwnerId(ownerId).stream()
                .map(DeviceBuilder::toDeviceDTO)
                .collect(Collectors.toList());
    }
}
