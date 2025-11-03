package com.example.demo.dtos;

public class LoginResponseDTO {

    private String token;
    private String username;
    private String[] roles;
    private String id; // nou

    public LoginResponseDTO(String token, String username, String[] roles, String id) { // 🚀 Adaugă 'id'
        this.token = token;
        this.username = username;
        this.roles = roles;
        this.id = id; // Setează UUID-ul
    }

    // Getters
    public String getToken() { return token; }
    public String getUsername() { return username; }
    public String[] getRoles() { return roles; }
    public String getId() { return id; }
}
