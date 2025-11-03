package com.example.demo.controllers;

import com.example.demo.dtos.AuthorizationUserDTO;
import com.example.demo.dtos.LoginRequestDTO;
import com.example.demo.dtos.LoginResponseDTO;
import com.example.demo.entities.AuthorizationUser;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.services.AuthorizationUserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthorizationUserService service;
    private final JwtTokenProvider tokenProvider;

    public AuthController(AuthorizationUserService service, JwtTokenProvider tokenProvider) {
        this.service = service;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody AuthorizationUserDTO dto) {
        service.register(dto);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        AuthorizationUser user = service.findByUsername(dto.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!service.checkPassword(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        String token = tokenProvider.generateToken(user);
        // 🚀 CORECȚIE ESENȚIALĂ: Includem UUID-ul în răspuns
        return ResponseEntity.ok(new LoginResponseDTO(
                token,
                user.getUsername(),
                user.getRoles().toArray(new String[0]),
                user.getId().toString() // <<-- Trimitem UUID-ul ca String
        ));

//        return ResponseEntity.ok(new LoginResponseDTO(token, user.getUsername(),
//                user.getRoles().toArray(new String[0])));
    }

}
