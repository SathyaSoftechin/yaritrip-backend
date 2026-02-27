package com.example.demo.dto;

import java.util.UUID;

public record UserProfileResponse(
        UUID id,
        String name,
        String email,
        String mobile
) {}
