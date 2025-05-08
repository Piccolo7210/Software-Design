package org.example.usermanagement.domain;

import java.util.UUID;

public class Role {
    private final UUID id;
    private final String roleName;

    public Role(String roleName) {
        this(UUID.randomUUID(), roleName);
    }

    public Role(UUID id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public UUID getId() {
        return id;
    }

    public String getRoleName() {
        return roleName;
    }
}