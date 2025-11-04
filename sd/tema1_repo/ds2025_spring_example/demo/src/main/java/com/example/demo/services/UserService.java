package com.example.demo.services;

import com.example.demo.dtos.UserDTO;
import com.example.demo.dtos.UserDetailsDTO;
import com.example.demo.dtos.builders.UserBuilder;
import com.example.demo.entities.User;
import com.example.demo.handlers.exceptions.model.ResourceNotFoundException;
import com.example.demo.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> findUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserBuilder::toUserDTO)
                .collect(Collectors.toList());
    }

    public UserDetailsDTO findUserById(UUID id) {
        return userRepository.findById(id)
                .map(UserBuilder::toUserDetailsDTO)
                .orElseThrow(() -> new ResourceNotFoundException("User with id: " + id));
    }

    // Modificat: primeste si authId
    public UUID insert(UserDetailsDTO userDTO) {
        User user = UserBuilder.toEntity(userDTO); // UserBuilder convertește authId string → UUID
        user = userRepository.save(user);
        LOGGER.debug("User with id {} inserted in db", user.getId());
        return user.getId();
    }



    public void update(UUID id, UserDetailsDTO userDTO) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id: " + id));

        existing.setName(userDTO.getName());
        existing.setAge(userDTO.getAge());
        existing.setAddress(userDTO.getAddress());

        userRepository.save(existing);
        LOGGER.debug("User with id {} updated", id);
    }

    public void delete(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User with id: " + id);
        }
        userRepository.deleteById(id);
        LOGGER.debug("User with id {} deleted", id);
    }
}
