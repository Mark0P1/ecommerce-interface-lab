package com.ws101.maningcay.ecommerceapi.controller;

import com.ws101.maningcay.ecommerceapi.dto.RegisterUserDto;
import com.ws101.maningcay.ecommerceapi.model.User;
import com.ws101.maningcay.ecommerceapi.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Valid
            @RequestBody
            RegisterUserDto dto
    ) {

        User user = new User();

        user.setUsername(
                dto.getUsername()
        );

        user.setPassword(
                passwordEncoder.encode(
                        dto.getPassword()
                )
        );

        user.setRole(
                dto.getRole()
        );

        userRepository.save(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("User registered");
    }
}

@PostMapping("/login")
public ResponseEntity<?> login(
        @RequestBody
        LoginRequestDto dto
) {

    UserDetails user =
            userDetailsService
                    .loadUserByUsername(
                            dto.getUsername()
                    );

    if (
            !passwordEncoder.matches(
                    dto.getPassword(),
                    user.getPassword()
            )
    ) {

        return ResponseEntity
                .status(401)
                .body(
                        "Invalid credentials"
                );
    }

    String token =
            jwtUtil.generateToken(
                    user
            );

    return ResponseEntity.ok(
            Map.of(
                    "token",
                    token
            )
    );
}