package org.store.auth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.store.auth.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
