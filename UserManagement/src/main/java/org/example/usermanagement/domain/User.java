package org.example.usermanagement.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {
    private final UUID id;
    private final String name;
    private final String email;
    private final List<Role> roles;

    public User(String name, String email) {
        this(UUID.randomUUID(), name, email);
    }

    public User(UUID id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.roles = new ArrayList<>();
    }

    public void assignRole(Role role) {
        roles.add(role);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<Role> getRoles() {
        return new ArrayList<>(roles);
    }
}