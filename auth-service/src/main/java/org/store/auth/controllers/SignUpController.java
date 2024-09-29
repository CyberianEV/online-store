package org.store.auth.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.store.api.SignUpRequest;
import org.store.auth.exceptions.AppError;
import org.store.auth.services.UserService;
import org.store.auth.utils.UserValidator;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
@Tag(name = "Registration", description = "New Users Registration Service")
public class SignUpController {
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserValidator userValidator;
    private final UserService userService;

    @Operation(
            summary = "Request to register new user",
            responses = {
                    @ApiResponse(
                            description = "Successfully registered", responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unsuccessful registration attempt", responseCode = "400",
                            content = @Content(schema = @Schema(implementation = AppError.class),
                                    examples = @ExampleObject(value = "'VALIDATION_ERROR', 'E-mail address cannot be empty, " +
                                            "Username must include only Latin letters, numbers, or underscores, Password and confirmation password do not match'")

                            )
                    )
            }
    )
    @PostMapping
    public void signUpUser(@RequestBody SignUpRequest request) {
        userValidator.validateFields(request);
        userValidator.validateUniqueness(request);
        userService.saveNewUser(request);
    }
}
