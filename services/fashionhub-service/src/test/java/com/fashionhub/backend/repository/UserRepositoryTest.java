package com.fashionhub.backend.repository;

import com.fashionhub.backend.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        testUser = new User();
        testUser.setFirstName("John");
        testUser.setLastName("Doe");
        testUser.setEmail("john.doe@test.com");
        testUser.setPassword("password123");
        testUser.setPhoneNumber("1234567890");

        userRepository.save(testUser);
    }

    @Test
    void testFindByEmail() {
        
        Optional<User> found = userRepository.findByEmail("john.doe@test.com");

        
        assertThat(found).isPresent();
        assertThat(found.get().getFirstName()).isEqualTo("John");
        assertThat(found.get().getLastName()).isEqualTo("Doe");
    }

    @Test
    void testExistsByEmail() {
        
        boolean exists = userRepository.existsByEmail("john.doe@test.com");

        
        assertThat(exists).isTrue();
    }

    @Test
    void testFindByEmailNotFound() {
        
        Optional<User> found = userRepository.findByEmail("nonexistent@test.com");

        
        assertThat(found).isEmpty();
    }

    @Test
    void testExistsByEmailNotFound() {
        
        boolean exists = userRepository.existsByEmail("nonexistent@test.com");

        
        assertThat(exists).isFalse();
    }
}
