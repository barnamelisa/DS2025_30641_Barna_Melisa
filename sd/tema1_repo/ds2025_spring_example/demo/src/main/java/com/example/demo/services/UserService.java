package com.example.demo.services;


import com.example.demo.dtos.UserDTO;
import com.example.demo.dtos.UserDetailsDTO;
import com.example.demo.dtos.builders.UserBuilder;
import com.example.demo.entities.User;
import com.example.demo.handlers.exceptions.model.ResourceNotFoundException;
import com.example.demo.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRepository personRepository;

    @Autowired
    public UserService(UserRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<UserDTO> findUsers() {
        List<User> personList = personRepository.findAll();
        return personList.stream()
                .map(UserBuilder::toUserDTO)
                .collect(Collectors.toList());
    }

    public UserDetailsDTO findUserById(UUID id) {
        Optional<User> prosumerOptional = personRepository.findById(id);
        if (!prosumerOptional.isPresent()) {
            LOGGER.error("Person with id {} was not found in db", id);
            throw new ResourceNotFoundException(User.class.getSimpleName() + " with id: " + id);
        }
        return UserBuilder.toUserDetailsDTO(prosumerOptional.get());
    }

    public UUID insert(UserDetailsDTO userDTO) {
        User user = UserBuilder.toEntity(userDTO);
        user = personRepository.save(user);
        LOGGER.debug("Person with id {} was inserted in db", user.getId());

        return user.getId();
    }

    public void update(UUID id, UserDetailsDTO userDTO) {
        User existing = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id: " + id));

        existing.setName(userDTO.getName());
        existing.setAge(userDTO.getAge());
        existing.setAddress(userDTO.getAddress());

        personRepository.save(existing);
        LOGGER.debug("User with id {} updated", id);
    }

    public void delete(UUID id) {
        if (!personRepository.existsById(id)) {
            throw new ResourceNotFoundException("User with id: " + id);
        }
        personRepository.deleteById(id);
        LOGGER.debug("User with id {} deleted", id);
    }

}
