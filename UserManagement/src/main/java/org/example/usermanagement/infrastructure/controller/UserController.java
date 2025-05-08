package org.example.usermanagement.infrastructure.controller;

import org.example.usermanagement.application.UserService;
import org.example.usermanagement.domain.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UUID> createUser(@Valid @RequestBody CreateUserRequest request) {
        UUID userId = userService.createUser(request.name(), request.email());
        return new ResponseEntity<>(userId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable UUID id) {
        User user = userService.getUserDetails(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/{userId}/assign-role/{roleId}")
    public ResponseEntity<String> assignRole(@PathVariable UUID userId, @PathVariable UUID roleId) {
        userService.assignRoleToUser(userId, roleId);
        return ResponseEntity.ok("Role assigned successfully");
    }
}

record CreateUserRequest(
        @jakarta.validation.constraints.NotBlank String name,
        @jakarta.validation.constraints.Email String email
) {}