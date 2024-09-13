package org.store.auth.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.store.api.SignUpRequest;
import org.store.auth.exceptions.ValidationException;
import org.store.auth.services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class UserValidator {
    private final UserService userService;

    public void validateFields(SignUpRequest userDetails) {
        List<String> errors = new ArrayList<>();
        String username = userDetails.getUsername();
        String password = userDetails.getPassword();

        Pattern usernameExclude = Pattern.compile("[^a-z0-9_]", Pattern.CASE_INSENSITIVE);
        Pattern passExclude = Pattern.compile("[^a-zA-Z0-9!@#$%&*()_+=|<>?{}\\[\\]~-]");
        int minLength = 3;
        int maxLength = 15;

        if (username == null || username.isBlank() || username.length() < minLength || username.length() > maxLength) {
            errors.add("Username must contain " + minLength + " - " + maxLength + " characters");
        }
        if (userDetails.getEmail() == null || userDetails.getEmail().isBlank()) {
            errors.add("E-mail address cannot be empty");
        }
        if (password == null || password.isBlank() || password.length() < minLength || password.length() > maxLength) {
            errors.add("Password must contain " + minLength + " - " + maxLength + " characters");
        }
        if (userDetails.getPasswordConfirmation() == null || userDetails.getPasswordConfirmation().isBlank()) {
            errors.add("Password confirmation cannot be empty");
        }

        try {
            Matcher usernameMatcher = usernameExclude.matcher(username);
            Matcher passwordMatcher = passExclude.matcher(password);
            if (usernameMatcher.find()) {
                errors.add("Username must include only Latin letters, numbers, or underscores");
            }
            if (passwordMatcher.find()) {
                errors.add("Password must include only Latin letters, numbers, or special characters");
            }
            if (!password.equals(userDetails.getPasswordConfirmation())) {
                errors.add("Password and confirmation password do not match");
            }
        } catch (NullPointerException e) {
            throw new ValidationException(errors);
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    public void validateUniqueness(SignUpRequest userDetails) {
        List<String> errors = new ArrayList<>();
        String username = userDetails.getUsername();
        String email = userDetails.getEmail();
        if (userService.findByUsername(username).isPresent()) {
            errors.add(String.format("User with username %s already exists", username));
        }
        if (userService.findByEmail(email).isPresent()) {
            errors.add(String.format("User with e-mail address %s already exists", email));
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
