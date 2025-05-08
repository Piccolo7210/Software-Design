package org.example.usermanagement.application.interfaces;

import org.example.usermanagement.domain.User;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(UUID id);
}