package com.gnomeshift.mfixflow.user;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setFixRequests(user.getFixRequests());

        return userDTO;
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public UserDTO getUserById(long id) {
        return userRepository.findById(id).map(this::convertToDTO)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public UserDTO updateUser(long id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));

        existingUser.setName(userDTO.getName());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setFixRequests(userDTO.getFixRequests());

        User updatedUser = userRepository.save(existingUser);
        return convertToDTO(updatedUser);
    }

    public void deleteUser(long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found");
        }

        userRepository.deleteById(id);
    }
}
