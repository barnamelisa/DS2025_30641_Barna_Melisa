package com.example.demo.repositories;

import com.example.demo.entities.AuthorizationUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AuthorizationUserRepository extends JpaRepository<AuthorizationUser, UUID> {
    Optional<AuthorizationUser> findByUsername(String username);
    boolean existsByUsername(String username);
}