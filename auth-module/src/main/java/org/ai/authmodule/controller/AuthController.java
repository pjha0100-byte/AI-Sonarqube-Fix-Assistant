package org.ai.authmodule.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ai.authmodule.dto.AuthResponse;
import org.ai.authmodule.dto.LoginRequest;
import org.ai.authmodule.dto.RegisterRequest;
import org.ai.authmodule.servive.AuthService;
import org.ai.common.dtos.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@Valid @RequestBody RegisterRequest request) {

        authService.register(request);

        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .success(true)
                        .message(
                                "User Registered")
                        .data("SUCCESS")
                        .build());
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {

        return ResponseEntity.ok(
                authService.login(request));
    }

    @GetMapping("/health")
    public String health() {
        return "AUTH MODULE WORKING";
    }
}
