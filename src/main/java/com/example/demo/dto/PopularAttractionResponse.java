package com.example.demo.dto;

import java.util.UUID;

public record PopularAttractionResponse(
        UUID id,
        String title,
        String city,
        String subtitle,
        String imageUrl,
        double rating,
        int reviews
) {}
