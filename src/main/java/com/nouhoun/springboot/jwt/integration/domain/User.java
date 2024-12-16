package com.nouhoun.springboot.jwt.integration.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.List;

/**
 * Represents a user in the application.
 * This entity is mapped to the "app_user" database table.
 */
@Entity
@Table(name = "app_user")
@Getter
@Setter
public class User {

    /**
     * The unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * The username of the user.
     */
    @Column(name = "username")
    private String username;

    /**
     * The password of the user.
     * The `@JsonIgnore` annotation prevents this field from being serialized in responses.
     */
    @Column(name = "password")
    @JsonIgnore
    private String password;

    /**
     * The first name of the user.
     */
    @Column(name = "first_name")
    private String firstName;

    /**
     * The last name of the user.
     */
    @Column(name = "last_name")
    private String lastName;

    /**
     * The roles assigned to the user.
     * Roles are eagerly loaded in this example due to the expected small size of the collection.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns
            = @JoinColumn(name = "user_id",
            referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",
                    referencedColumnName = "id"))
    private List<Role> roles;
}
