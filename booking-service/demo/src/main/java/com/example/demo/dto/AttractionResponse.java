package com.example.demo.dto;

import java.util.UUID;

public record AttractionResponse(
        UUID id,
        UUID packageId,
        String title,
        String city,
        String subtitle,
        String image,
        double rating,
        int reviews
) {}