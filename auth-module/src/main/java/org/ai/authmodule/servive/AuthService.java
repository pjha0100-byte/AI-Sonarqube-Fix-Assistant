package org.ai.authmodule.servive;

import lombok.RequiredArgsConstructor;
import org.ai.authmodule.dto.AuthResponse;
import org.ai.authmodule.dto.LoginRequest;
import org.ai.authmodule.dto.RegisterRequest;
import org.ai.authmodule.entity.User;
import org.ai.authmodule.repository.UserRepository;
import org.ai.authmodule.security.JwtService;
import org.ai.authmodule.security.UserPrincipal;
import org.ai.common.enums.Roles;
import org.ai.common.exceptions.BusinessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public void register(RegisterRequest request) {

        if(repository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user =
                User.builder()
                        .firstName(request.getFirstName())
                        .lastName(request.getLastName())
                        .email(request.getEmail())
                        .password(
                                passwordEncoder.encode(
                                        request.getPassword()
                                )
                        )
                        .role(Roles.DEVELOPER)
                        .active(true)
                        .enabled(true)
                        .build();

        repository.save(user);
    }

    public AuthResponse login(LoginRequest request) {

        User user =
                repository.findByEmail(request.getEmail())
                        .orElseThrow(
                                () -> new RuntimeException("User not found")
                        );

        if(!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new RuntimeException("Invalid credentials");
        }

        String token =
                jwtService.generateToken(user.getEmail());

        return AuthResponse.builder()
                .accessToken(token)
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}
