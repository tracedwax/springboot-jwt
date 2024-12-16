package com.nouhoun.springboot.jwt.integration.service.impl;

import com.nouhoun.springboot.jwt.integration.domain.RandomCity;
import com.nouhoun.springboot.jwt.integration.domain.User;
import com.nouhoun.springboot.jwt.integration.repository.RandomCityRepository;
import com.nouhoun.springboot.jwt.integration.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GenericServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RandomCityRepository randomCityRepository;

    @InjectMocks
    private GenericServiceImpl genericService;

    @Test
    void findByUsername_WhenUserExists_ReturnsUser() {
        // Arrange
        User user = new User();
        user.setUsername("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(user);

        // Act
        User result = genericService.findByUsername("testuser");

        // Assert
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        verify(userRepository, times(1)).findByUsername("testuser");
    }

    @Test
    void findByUsername_WhenUserDoesNotExist_ReturnsNull() {
        // Arrange
        when(userRepository.findByUsername("nonexistent")).thenReturn(null);

        // Act
        User result = genericService.findByUsername("nonexistent");

        // Assert
        assertNull(result);
        verify(userRepository, times(1)).findByUsername("nonexistent");
    }

    @Test
    void findAllUsers_ReturnsListOfUsers() {
        // Arrange
        List<User> users = Arrays.asList(
            createUser("user1"),
            createUser("user2")
        );
        when(userRepository.findAll()).thenReturn(users);

        // Act
        List<User> result = genericService.findAllUsers();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("user1", result.get(0).getUsername());
        assertEquals("user2", result.get(1).getUsername());
    }

    @Test
    void findAllRandomCities_ReturnsListOfCities() {
        // Arrange
        List<RandomCity> cities = Arrays.asList(
            createCity("New York"),
            createCity("London")
        );
        when(randomCityRepository.findAll()).thenReturn(cities);

        // Act
        List<RandomCity> result = genericService.findAllRandomCities();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("New York", result.get(0).getName());
        assertEquals("London", result.get(1).getName());
    }

    private User createUser(String username) {
        User user = new User();
        user.setUsername(username);
        return user;
    }

    private RandomCity createCity(String name) {
        RandomCity city = new RandomCity();
        city.setName(name);
        return city;
    }
}
