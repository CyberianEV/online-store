package org.store.auth.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.store.api.SignUpRequest;
import org.store.auth.services.UserService;
import org.store.auth.utils.UserValidator;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class SignUpController {
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserValidator userValidator;
    private final UserService userService;

    @PostMapping
    public void signUpUser(@RequestBody SignUpRequest request) {
        userValidator.validateFields(request);
        userValidator.validateUniqueness(request);
        userService.saveNewUser(request);
    }
}
