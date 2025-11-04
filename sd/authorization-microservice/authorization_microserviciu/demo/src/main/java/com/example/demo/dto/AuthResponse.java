package com.example.demo.dto;

import java.util.List;

public class AuthResponse {
    private String token;
    private String username;
    private List<String> roles;
    private String id; // UUID-ul user-ului

    public AuthResponse() {}

    public AuthResponse(String token, String username, List<String> roles, String id) {
        this.token = token;
        this.username = username;
        this.roles = roles;
        this.id = id;
    }

    public AuthResponse(String token) {
        this.token = token;
        this.username = null;
        this.roles = null;
        this.id = null;
    }

    // getter & setter pentru toate câmpurile
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public List<String> getRoles() { return roles; }
    public void setRoles(List<String> roles) { this.roles = roles; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
}
