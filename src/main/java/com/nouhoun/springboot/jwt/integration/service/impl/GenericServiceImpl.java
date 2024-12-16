package com.nouhoun.springboot.jwt.integration.service.impl;

import com.nouhoun.springboot.jwt.integration.domain.RandomCity;
import com.nouhoun.springboot.jwt.integration.domain.User;
import com.nouhoun.springboot.jwt.integration.repository.RandomCityRepository;
import com.nouhoun.springboot.jwt.integration.repository.UserRepository;
import com.nouhoun.springboot.jwt.integration.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link GenericService} interface.
 * Provides methods for accessing user and random city data.
 */
@Service
public class GenericServiceImpl implements GenericService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RandomCityRepository randomCityRepository;

    /**
     * Finds a user by their username.
     *
     * @param username The username to search for.
     * @return The user with the matching username, or null if not found.
     */
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Finds all users.
     *
     * @return A list of all users.
     */
    @Override
    public List<User> findAllUsers() {
        return (List<User>)userRepository.findAll();
    }

    /**
     * Finds all random cities.
     *
     * @return A list of all random cities.
     */
    @Override
    public List<RandomCity> findAllRandomCities() {
        return (List<RandomCity>)randomCityRepository.findAll();
    }
}
