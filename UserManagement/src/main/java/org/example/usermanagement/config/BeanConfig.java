package org.example.usermanagement.config;

import org.example.usermanagement.application.UserService;
import org.example.usermanagement.application.RoleService;
import org.example.usermanagement.application.interfaces.UserRepository;
import org.example.usermanagement.application.interfaces.RoleRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Bean
    public UserService userService(UserRepository userRepository, RoleRepository roleRepository) {
        return new UserService(userRepository, roleRepository);
    }

    @Bean
    public RoleService roleService(RoleRepository roleRepository) {
        return new RoleService(roleRepository);
    }
}