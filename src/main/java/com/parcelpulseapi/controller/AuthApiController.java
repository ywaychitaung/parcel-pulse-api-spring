package com.parcelpulseapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.parcelpulseapi.dto.request.AuthRequest.LoginRequest;
import com.parcelpulseapi.dto.request.AuthRequest.RegisterRequest;
import com.parcelpulseapi.service.implementation.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Authentication API")
public class AuthApiController {
    private final AuthService authService;

    public AuthApiController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Login with username/email and password to get JWT token")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.authenticateUser(loginRequest));
    }

    @PostMapping("/register")
    @Operation(
        summary = "Register user",
        description = "Register a new user account. roleId: 1 for User, 3 for Courier. Admin role (2) is restricted."
    )
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authService.registerUser(registerRequest));
    }

    @GetMapping("/me")
    @Operation(summary = "Get current user", description = "Get the profile of the currently authenticated user")
    public ResponseEntity<?> getCurrentUser() {
        return ResponseEntity.ok(authService.getCurrentUser());
    }
}
