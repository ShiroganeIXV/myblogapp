package com.dianawu.blogApp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL) // if a User entity is deleted, all its associated Role entities in the join table users_roles will also be deleted. Similarly, if a new Role is added to a User, this change will be reflected in the join table.
    @JoinTable(
            name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")}, // the column in the users_roles table that references the user
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")} // the column in the users_roles table that references the role
    )
    private List<Role> roles = new ArrayList<>();


}
