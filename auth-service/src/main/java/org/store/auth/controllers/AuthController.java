package org.store.auth.controllers;

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
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<?> getJwToken(@RequestBody JwtRequest request) {
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
