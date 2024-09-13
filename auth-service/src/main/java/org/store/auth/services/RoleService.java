package org.store.auth.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.store.auth.entities.Role;
import org.store.auth.repositories.RoleRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }
}
