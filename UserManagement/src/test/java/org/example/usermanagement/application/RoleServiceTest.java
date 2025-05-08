package org.example.usermanagement.application;

import org.example.usermanagement.application.interfaces.RoleRepository;
import org.example.usermanagement.domain.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {
    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    private Role role;

    @BeforeEach
    void setUp() {
        role = new Role("ADMIN");
    }

    @Test
    void createRole_validInput_returnsRoleId() {
        when(roleRepository.save(any(Role.class))).thenReturn(role);
        UUID roleId = roleService.createRole("ADMIN");
        assertNotNull(roleId);
        verify(roleRepository).save(any(Role.class));
    }
}