package org.store.auth.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.store.api.JwtRequest;
import org.store.api.JwtResponse;
import org.store.auth.exceptions.AppError;
import org.store.auth.services.UserService;
import org.store.auth.utils.JwtUtil;

@RestController
@RequestMapping("/authenticate")
@RequiredArgsConstructor
@Tag(name = "Authorization", description = "Users Authorization Service")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Operation(
            summary = "Request to authorize user",
            responses = {
                    @ApiResponse(
                            description = "Successfully authorized", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = JwtResponse.class))
                    ),
                    @ApiResponse(
                            description = "Unsuccessful authorization attempt", responseCode = "401",
                            content = @Content(schema = @Schema(implementation = AppError.class),
                                    examples = @ExampleObject(value = "'BAD_CREDENTIALS', 'Invalid user name or password'"))
                    )
            }
    )
    @PostMapping
    public ResponseEntity<?> getJwToken(@RequestBody @Parameter(description = "JWT request", required = true) JwtRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),
                    request.getPassword())
            );
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AppError("BAD_CREDENTIALS", "Invalid user name or password"),
                    HttpStatus.UNAUTHORIZED
            );
        }
        UserDetails userDetails = userService.loadUserByUsername(request.getUsername());
        String token = jwtUtil.generateJwToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
