package org.example.usermanagement.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface RoleSpringDataRepository extends JpaRepository<RoleJpaEntity, UUID> {
}