package com.example.librarysystem.service;

import com.example.librarysystem.dto.UserDTO;
import com.example.librarysystem.entity.User;
import com.example.librarysystem.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO createUser(User user) {
        User savedUser = userRepository.save(user);
        return mapToDTO(savedUser);
    }

    public Optional<UserDTO> getUserByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email)
                .map(this::mapToDTO);
    }

    private UserDTO mapToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRegistrationDate()
        );
    }
}
