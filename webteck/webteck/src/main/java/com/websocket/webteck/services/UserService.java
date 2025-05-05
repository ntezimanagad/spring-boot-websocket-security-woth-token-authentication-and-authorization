package com.websocket.webteck.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.websocket.webteck.dto.UserDTO;
import com.websocket.webteck.jwt.JwtUtil;
import com.websocket.webteck.model.Blacklist;
import com.websocket.webteck.model.Role;
import com.websocket.webteck.model.User;
import com.websocket.webteck.repository.TokenBlacklist;
import com.websocket.webteck.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenBlacklist tokenBlacklist;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private  JwtUtil jwtUtil;

    public List<String> getAllEmail(){
        return userRepository.findAll()
            .stream()
            .map(User::getEmail)
            .collect(Collectors.toList());
    }
    public void createUser(UserDTO userDTO) {
        // Validate that the password is not null or empty
        if (userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
    
        // Check if a user already exists with the given email
        Optional<User> optionalUser = userRepository.findByEmail(userDTO.getEmail());
        
        // If user exists, throw an exception
        if (optionalUser.isPresent()) {
            throw new RuntimeException("User Already Exists");
        }
        
        // If the user doesn't exist, create a new user
        User user = new User(); // Create a new user object
        user.setFullname(userDTO.getFullname());
        user.setEmail(userDTO.getEmail());
    
        // Encrypt password before saving
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        user.setPassword(encodedPassword); // Set the encrypted password
    
        user.setRole(Role.USER); // Set default role or modify based on your logic
        
        // Save the new user
        userRepository.save(user);
    }
    

    public String loginUser(UserDTO userDTO){
        Optional<User> optionalUser = userRepository.findByEmail(userDTO.getEmail());
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User Not Found");
        }
        User user = optionalUser.get();
        if (!passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("Password Combination Don't match");
        }
        return jwtUtil.generateToken(user.getEmail(), user.getRole());
    }
    public void logout(String token){
        Blacklist blacklist = new Blacklist(token, Instant.now());
        tokenBlacklist.save(blacklist);
    }
}
