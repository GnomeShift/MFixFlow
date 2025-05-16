package com.gnomeshift.mfixflow.user;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public User updateUser(long id, User user) {
        Optional<User> userOptional = userRepository.findById(user.getId());

        if (userOptional.isPresent()) {
            user.setId(id);
            return userRepository.save(user);
        }
        else {
            return null;
        }
    }

    public void deleteUser(long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found");
        }

        userRepository.deleteById(id);
    }
}
