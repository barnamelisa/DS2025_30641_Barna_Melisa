package com.example.demo.services;

import com.example.demo.dtos.AuthorizationUserDTO;
import com.example.demo.entities.AuthorizationUser;
import com.example.demo.repositories.AuthorizationUserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthorizationUserService {

    private final AuthorizationUserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthorizationUserService(AuthorizationUserRepository repository,
                                    BCryptPasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UUID register(AuthorizationUserDTO dto) {
        if (repository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        AuthorizationUser user = new AuthorizationUser();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEmail(dto.getEmail());
        user.setRoles(dto.getRoles());
        repository.save(user);
        return user.getId();
    }

    public Optional<AuthorizationUser> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
