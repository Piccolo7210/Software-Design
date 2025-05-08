package org.example.usermanagement.infrastructure.persistence;

import org.example.usermanagement.application.interfaces.RoleRepository;
import org.example.usermanagement.domain.Role;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class RoleJpaRepository implements RoleRepository {
    private final RoleSpringDataRepository springDataRepository;

    public RoleJpaRepository(RoleSpringDataRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public Role save(Role role) {
        RoleJpaEntity entity = new RoleJpaEntity();
        entity.setId(role.getId());
        entity.setRoleName(role.getRoleName());
        RoleJpaEntity savedEntity = springDataRepository.save(entity);
        return new Role(savedEntity.getId(), savedEntity.getRoleName());
    }

    @Override
    public Optional<Role> findById(UUID id) {
        return springDataRepository.findById(id).map(entity ->
                new Role(entity.getId(), entity.getRoleName())
        );
    }
}