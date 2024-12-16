package com.nouhoun.springboot.jwt.integration.domain;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Represents a role in the application.
 * This entity is mapped to the "app_role" database table.
 */
@Entity
@Table(name = "app_role")
@Getter
@Setter
public class Role {

    private static final long serialVersionUID = 1L;

    /**
     * The unique identifier for the role.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the role (e.g., "ADMIN_USER").
     */
    @Column(name = "role_name")
    private String roleName;

    /**
     * A description of the role.
     */
    @Column(name = "description")
    private String description;
}
