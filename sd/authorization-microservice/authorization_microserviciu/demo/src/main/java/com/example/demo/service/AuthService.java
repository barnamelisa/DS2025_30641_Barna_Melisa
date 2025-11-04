package com.example.demo.service;

import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    // Înregistrare user
    public AuthResponse register(RegisterRequest request) {
        // Verifică dacă username-ul există deja
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists: " + request.getUsername());
        }

        // Creează user nou
        User user = new User(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                request.getRole() // presupunem că este un singur rol la înregistrare
        );
        userRepository.save(user);

        // Generează token
        String token = jwtService.generateToken(user.getUsername(), user.getRole().name());

        // Listează rolurile (poate fi și un singur rol)
        List<String> roles = List.of(user.getRole().name());

        // UUID-ul userului
        String id = user.getId().toString();

        return new AuthResponse(token, user.getUsername(), roles, id);
    }

    // Login user
    public AuthResponse login(LoginRequest request) {
        // 1. Caută user în DB
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Verifică parola
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // 3. Generează token (folosind username + rol)
        String token = jwtService.generateToken(user.getUsername(), user.getRole().name());

        // 4. Pregătește lista de roluri
        List<String> roles = List.of(user.getRole().name());

        // 5. Returnează AuthResponse complet
        return new AuthResponse(token, user.getUsername(), roles, user.getId().toString());
    }

    // Validare token
    public boolean validateToken(String token) {
        return jwtService.validateToken(token);
    }
}
