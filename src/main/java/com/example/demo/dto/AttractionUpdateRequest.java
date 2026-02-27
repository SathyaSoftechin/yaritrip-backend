package com.example.demo.dto;

public record AttractionUpdateRequest(
        String name,
        String location,
        String description,
        String imageUrl,
        Double rating,
        Boolean isPopular
) {}
