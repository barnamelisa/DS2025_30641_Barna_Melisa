package com.example.demo.controllers;

import com.example.demo.dtos.DeviceDTO;
import com.example.demo.dtos.DeviceDetailsDTO;
import com.example.demo.services.DeviceService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/devices")
@Validated
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    // GET all devices
//    @GetMapping
//    public ResponseEntity<List<DeviceDTO>> getDevices() {
//        return ResponseEntity.ok(deviceService.findDevices());
//    }

    @GetMapping
    public ResponseEntity<List<DeviceDTO>> getDevices(
            @RequestParam(required = false) UUID ownerId) { // <<-- ACCEPTĂ UUID TRIMIS DE FRONTEND

        if (ownerId != null) {
            // Cazul Client: UUID-ul este furnizat de Frontend (ex: /devices?ownerId=UUID)
            return ResponseEntity.ok(deviceService.findDevicesByOwnerId(ownerId));
        }

        // Cazul Admin: UUID-ul nu este furnizat (ex: /devices)
        return ResponseEntity.ok(deviceService.findDevices());
    }

    // POST a new device (and assign an owner)
    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody DeviceDetailsDTO device) {
        UUID id = deviceService.insert(device);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location).build(); // 201 + Location header
    }

    // GET device by ID
    @GetMapping("/{id}")
    public ResponseEntity<DeviceDetailsDTO> getDevice(@PathVariable UUID id) {
        return ResponseEntity.ok(deviceService.findDeviceById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id,
                                       @Valid @RequestBody DeviceDetailsDTO device) {
        deviceService.update(id, device);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deviceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
