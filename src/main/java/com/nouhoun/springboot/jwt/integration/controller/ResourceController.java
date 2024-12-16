package com.nouhoun.springboot.jwt.integration.controller;

import com.nouhoun.springboot.jwt.integration.domain.RandomCity;
import com.nouhoun.springboot.jwt.integration.domain.User;
import com.nouhoun.springboot.jwt.integration.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST Controller for accessing protected resources.
 * This controller provides endpoints for retrieving cities and users, secured using OAuth2 and Spring Security.
 */
@RestController
@RequestMapping("/springjwt")
public class ResourceController {

    @Autowired
    private GenericService userService;

    /**
     * Retrieves a list of random cities.
     * This endpoint is accessible to users with either "ADMIN_USER" or "STANDARD_USER" authority.
     *
     * @return A list of {@link RandomCity} objects.
     */
    @RequestMapping(value ="/cities")
    @PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
    public List<RandomCity> getUser(){
        return userService.findAllRandomCities();
    }

    /**
     * Retrieves a list of users.
     * This endpoint is accessible only to users with "ADMIN_USER" authority.
     *
     * @return A list of {@link User} objects.
     */
    @RequestMapping(value ="/users", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    public List<User> getUsers(){
        return userService.findAllUsers();
    }
}
