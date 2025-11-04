package com.example.demo.dtos.builders;

import com.example.demo.dtos.UserDTO;
import com.example.demo.dtos.UserDetailsDTO;
import com.example.demo.entities.User;

import java.util.UUID;

public class UserBuilder {

    private UserBuilder() {}

    public static UserDTO toUserDTO(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getAge());
    }

    public static UserDetailsDTO toUserDetailsDTO(User user) {
        return new UserDetailsDTO(
                user.getId(),
                user.getName(),
                user.getAddress(),
                user.getAge(),
                user.getAuthId().toString() // convertim UUID → String
        );
    }

    public static User toEntity(UserDetailsDTO userDetailsDTO) {
        UUID authUUID = UUID.fromString(userDetailsDTO.getAuthId()); // convertim String → UUID
        return new User(
                userDetailsDTO.getName(),
                userDetailsDTO.getAddress(),
                userDetailsDTO.getAge(),
                authUUID
        );
    }


}
