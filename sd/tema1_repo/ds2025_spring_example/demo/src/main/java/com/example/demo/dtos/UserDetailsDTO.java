package com.example.demo.dtos;

import com.example.demo.dtos.validators.annotation.AgeLimit;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;
import java.util.UUID;

public class UserDetailsDTO {

    private UUID id;

    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "address is required")
    private String address;

    @NotNull(message = "age is required")
    @AgeLimit(value = 18)
    private Integer age;

    @NotNull(message = "authId is required") // folosește @NotBlank pentru string
    private String authId;

    public UserDetailsDTO() {}

    public UserDetailsDTO(UUID id, String name, String address, int age, String authId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.age = age;
        this.authId = authId;
    }

    public UserDetailsDTO(String name, String address, int age, String authId) {
        this.name = name;
        this.address = address;
        this.age = age;
        this.authId = authId;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public String getAuthId() { return authId; }      // ✅ tipul String
    public void setAuthId(String authId) { this.authId = authId; }  // ✅ tipul String
}
