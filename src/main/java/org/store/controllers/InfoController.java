package org.store.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.store.exceptions.ResourceNotFoundException;
import org.store.services.UserService;
import org.store.entities.User;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class InfoController {
    private final UserService userService;

    @GetMapping("/get_my_email")
    public String getEmail(Principal principal) {
        String username = principal.getName();
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User %s does not exist", username)));
        return user.getEmail();
    }
}
