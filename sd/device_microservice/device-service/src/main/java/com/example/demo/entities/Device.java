package com.example.demo.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "device")
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "max_consumption", nullable = false)
    private float maxConsumption;

    @Column(name = "owner_id", nullable = false)
    private UUID ownerId;

    public Device() {}

    public Device(String name, float maxConsumption, UUID ownerId) {
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
}
