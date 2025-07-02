package com.fashionhub.backend.service;

import com.fashionhub.backend.dto.LoginRequest;
import com.fashionhub.backend.dto.RegisterRequest;
import com.fashionhub.backend.dto.UserResponse;
import com.fashionhub.backend.entity.User;
import com.fashionhub.backend.exception.InvalidCredentialsException;
import com.fashionhub.backend.exception.UserNotFoundException;
import com.fashionhub.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    private User testUser;
    private LoginRequest loginRequest;
    private RegisterRequest registerRequest;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        testUser = new User();
        testUser.setFirstName("John");
        testUser.setLastName("Doe");
        testUser.setEmail("john.doe@example.com");
        testUser.setPassword("password123");
        testUser.setPhoneNumber("1234567890");
        userRepository.save(testUser);

        loginRequest = new LoginRequest();
        loginRequest.setEmail("john.doe@example.com");
        loginRequest.setPassword("password123");

        registerRequest = new RegisterRequest();
        registerRequest.setFirstName("Jane");
        registerRequest.setLastName("Smith");
        registerRequest.setEmail("jane.smith@example.com");
        registerRequest.setPassword("password123");
        registerRequest.setConfirmPassword("password123");
        registerRequest.setPhoneNumber("0987654321");
    }

    @Test
    void testLoginSuccess() {
        
        UserResponse response = authService.login(loginRequest);

        
        assertThat(response).isNotNull();
        assertThat(response.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(response.getFirstName()).isEqualTo("John");
        assertThat(response.getLastName()).isEqualTo("Doe");
    }

    @Test
    void testLoginUserNotFound() {
        
        loginRequest.setEmail("nonexistent@example.com");

        
        assertThatThrownBy(() -> authService.login(loginRequest))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User not found with email");
    }

    @Test
    void testLoginInvalidPassword() {
        
        loginRequest.setPassword("wrongpassword");

        
        assertThatThrownBy(() -> authService.login(loginRequest))
                .isInstanceOf(InvalidCredentialsException.class)
                .hasMessage("Invalid password");
    }

    @Test
    void testRegisterSuccess() {
        
        UserResponse response = authService.register(registerRequest);

        
        assertThat(response).isNotNull();
        assertThat(response.getEmail()).isEqualTo("jane.smith@example.com");
        assertThat(response.getFirstName()).isEqualTo("Jane");
        assertThat(response.getLastName()).isEqualTo("Smith");
    }

    @Test
    void testRegisterUserAlreadyExists() {
        
        registerRequest.setEmail("john.doe@example.com"); 

        
        assertThatThrownBy(() -> authService.register(registerRequest))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("User already exists");
    }

    @Test
    void testRegisterPasswordMismatch() {
        
        registerRequest.setConfirmPassword("differentPassword");

        
        assertThatThrownBy(() -> authService.register(registerRequest))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Passwords do not match");
    }
}
