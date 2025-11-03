package com.example.demo.dtos;

import java.util.Objects;
import java.util.UUID;

public class DeviceDTO {
    private UUID id;
    private String name;
    private float maxConsumption;
    private UUID ownerId;

    public DeviceDTO() {}

    public DeviceDTO(UUID id, String name, float maxConsumption, UUID ownerId) {
        this.id = id;
        this.name = name;
        this.maxConsumption = maxConsumption;
        this.ownerId = ownerId;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public float getMaxConsumption() { return maxConsumption; }
    public void setMaxConsumption(float maxConsumption) { this.maxConsumption = maxConsumption; }

    public UUID getOwnerId() { return ownerId; }
    public void setOwnerId(UUID ownerId) { this.ownerId = ownerId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceDTO deviceDTO = (DeviceDTO) o;
        return Float.compare(maxConsumption, deviceDTO.maxConsumption) == 0 &&
                Objects.equals(id, deviceDTO.id) &&
                Objects.equals(name, deviceDTO.name) &&
                Objects.equals(ownerId, deviceDTO.ownerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, maxConsumption, ownerId);
    }
}
