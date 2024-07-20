package org.store.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.store.core.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
