package com.nouhoun.springboot.jwt.integration.domain;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a random city.
 * This entity is used for demonstration purposes and is mapped to the "random_city" database table.
 */
@Entity
@Table(name = "random_city")
@Getter
@Setter
public class RandomCity {

    /**
     * The unique identifier for the city.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * The name of the city.
     */
    @Column(name = "name")
    private String name;
}
