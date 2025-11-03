package com.example.demo.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Objects;
import java.util.UUID;

public class DeviceDetailsDTO {
    private UUID id;

    @NotBlank(message = "Device name is required")
    private String name;

    @NotNull(message = "Max consumption is required")
    @Positive(message = "Max consumption must be positive")
    private Float maxConsumption;

    @NotNull(message = "Owner ID is required")
    private UUID ownerId;

    public DeviceDetailsDTO() {}

    public DeviceDetailsDTO(UUID id, String name, Float maxConsumption, UUID ownerId) {
        this.id = id;
        this.name = name;
        this.maxConsumption = maxConsumption;
        this.ownerId = ownerId;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Float getMaxConsumption() { return maxConsumption; }
    public void setMaxConsumption(Float maxConsumption) { this.maxConsumption = maxConsumption; }

    public UUID getOwnerId() { return ownerId; }
    public void setOwnerId(UUID ownerId) { this.ownerId = ownerId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceDetailsDTO that = (DeviceDetailsDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(maxConsumption, that.maxConsumption) &&
                Objects.equals(ownerId, that.ownerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, maxConsumption, ownerId);
    }
}
