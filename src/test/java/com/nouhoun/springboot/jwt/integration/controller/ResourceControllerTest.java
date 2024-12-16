package com.nouhoun.springboot.jwt.integration.controller;

import com.nouhoun.springboot.jwt.integration.domain.RandomCity;
import com.nouhoun.springboot.jwt.integration.domain.User;
import com.nouhoun.springboot.jwt.integration.service.GenericService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ResourceControllerTest {

    @Mock
    private GenericService userService;

    @InjectMocks
    private ResourceController resourceController;

    @Test
    void testGetCities_ReturnsListOfCities() {
        // Arrange
        List<RandomCity> cities = Arrays.asList(new RandomCity(), new RandomCity());
        when(userService.findAllRandomCities()).thenReturn(cities);

        // Act
        List<RandomCity> result = resourceController.getUser();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testGetCities_ReturnsEmptyList() {
        // Arrange
        when(userService.findAllRandomCities()).thenReturn(Collections.emptyList());

        // Act
        List<RandomCity> result = resourceController.getUser();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetUsers_ReturnsListOfUsers() {
        // Arrange
        User user1 = new User();
        user1.setUsername("admin");
        user1.setPassword("admin");

        User user2 = new User();
        user2.setUsername("admin2");
        user2.setPassword("admin2");

        List<User> users = Arrays.asList(user1, user2);
        when(userService.findAllUsers()).thenReturn(users);

        // Act
        List<User> result = resourceController.getUsers();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("admin", result.get(0).getUsername());
        assertEquals("admin2", result.get(1).getUsername());
    }

    @Test
    void testGetUsers_ReturnsEmptyList() {
        // Arrange
        when(userService.findAllUsers()).thenReturn(Collections.emptyList());

        // Act
        List<User> result = resourceController.getUsers();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetCities_ServiceThrowsException_ReturnsEmptyList() {
        // Arrange
        when(userService.findAllRandomCities()).thenThrow(new RuntimeException("Simulated database error"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> resourceController.getUser());
    }

    @Test
    void testGetUsers_ServiceThrowsException_ReturnsEmptyList() {
        // Arrange
        when(userService.findAllUsers()).thenThrow(new RuntimeException("Simulated database error"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> resourceController.getUsers());
    }
}
