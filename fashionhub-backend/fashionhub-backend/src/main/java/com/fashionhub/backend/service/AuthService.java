package com.fashionhub.backend.service;

import com.fashionhub.backend.dto.LoginRequest;
import com.fashionhub.backend.dto.RegisterRequest;
import com.fashionhub.backend.dto.UserResponse;
import com.fashionhub.backend.entity.User;
import com.fashionhub.backend.exception.InvalidCredentialsException;
import com.fashionhub.backend.exception.UserNotFoundException;
import com.fashionhub.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public UserResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + loginRequest.getEmail()));

        if (!user.getPassword().equals(loginRequest.getPassword())) {
            throw new InvalidCredentialsException("Invalid password");
        }

        return new UserResponse(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber()
        );
    }

    public UserResponse register(RegisterRequest registerRequest) {
        try {
            System.out.println("Register request received: " + registerRequest.getEmail());

            
            if (userRepository.existsByEmail(registerRequest.getEmail())) {
                throw new RuntimeException("User already exists with email: " + registerRequest.getEmail());
            }

            
            if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
                throw new RuntimeException("Passwords do not match");
            }

            
            User user = new User(
                    registerRequest.getFirstName(),
                    registerRequest.getLastName(),
                    registerRequest.getPassword(),
                    registerRequest.getEmail(),
                    registerRequest.getPhoneNumber()
            );

            System.out.println("Saving user to database...");
            User savedUser = userRepository.save(user);
            System.out.println("User saved successfully with ID: " + savedUser.getUserId());

            return new UserResponse(
                    savedUser.getUserId(),
                    savedUser.getFirstName(),
                    savedUser.getLastName(),
                    savedUser.getEmail(),
                    savedUser.getPhoneNumber()
            );
        } catch (Exception e) {
            System.err.println("Error in register method: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

}
