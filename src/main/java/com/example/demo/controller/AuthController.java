package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.security.JwtService;
import com.example.demo.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.example.demo.model.BlacklistedToken;
import com.example.demo.repository.BlacklistedTokenRepository;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;
    private final BlacklistedTokenRepository blacklistedTokenRepository;
    private final AuthService authService;

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring(7);

            LocalDateTime expiry = jwtService.extractExpiration(token)
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();

            blacklistedTokenRepository.save(
                    BlacklistedToken.builder()
                            .token(token)
                            .expiryDate(expiry)
                            .build()
            );
        }

        SecurityContextHolder.clearContext();

        return "Logged out successfully";
    }

    @PostMapping("/register")
    public CompletableFuture<ResponseEntity<String>> register(
            @Valid @RequestBody RegisterRequest request) {

        return authService.register(request)
                .thenApply(msg -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(msg));
    }

    @PostMapping("/login")
    public CompletableFuture<ResponseEntity<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        return authService.login(request)
                .thenApply(ResponseEntity::ok);
    }
}