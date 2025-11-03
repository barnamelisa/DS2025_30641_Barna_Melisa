package com.example.demo.repositories;

import com.example.demo.entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DeviceRepository extends JpaRepository<Device, UUID> {
    // Puteți adăuga interogări custom aici (ex: findByOwnerId)
    List<Device> findByOwnerId(UUID ownerId);
}
