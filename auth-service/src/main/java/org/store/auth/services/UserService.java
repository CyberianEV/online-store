package org.store.auth.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.store.api.SignUpRequest;
import org.store.auth.entities.Role;
import org.store.auth.entities.User;
import org.store.auth.exceptions.ResourceNotFoundException;
import org.store.auth.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User %s does not exist", username)
        ));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void saveNewUser(SignUpRequest request) {
        User user = new User();
        String encryptedPassword = passwordEncoder.encode(request.getPassword());

        Role role = roleService.findByName("ROLE_USER").orElseThrow(() -> new ResourceNotFoundException(
                "User creation error, non-existent role: ROLE_USER"
        ));
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(role);

        user.setId(null);
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(encryptedPassword);
        user.setRoles(roles);

        userRepository.save(user);
    }
}
