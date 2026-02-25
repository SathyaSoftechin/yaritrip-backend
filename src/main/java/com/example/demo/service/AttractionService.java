package com.example.demo.service;

import com.example.demo.dto.AttractionDetailResponse;
import com.example.demo.dto.AttractionUpdateRequest;
import com.example.demo.dto.PopularAttractionResponse;
import com.example.demo.model.Attraction;
import com.example.demo.repository.AttractionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttractionService {

    private final AttractionRepository attractionRepository;

    public List<PopularAttractionResponse> getPopularByCity(String city) {
        return attractionRepository
                .findByIsPopularTrueAndLocationIgnoreCaseOrderByRatingDesc(city)
                .stream()
                .map(this::mapToPopularResponse)
                .toList();
    }

    public AttractionDetailResponse getAttractionById(UUID id) {
        Attraction attraction = attractionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attraction not found"));
        return mapToDetailResponse(attraction);
    }

    public AttractionDetailResponse updateAttraction(UUID id, AttractionUpdateRequest request) {
        Attraction attraction = attractionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attraction not found"));

        attraction.setName(request.name());
        attraction.setLocation(request.location());
        attraction.setDescription(request.description());
        attraction.setImageUrl(request.imageUrl());
        attraction.setRating(request.rating());
        attraction.setIsPopular(request.isPopular());

        return mapToDetailResponse(attractionRepository.save(attraction));
    }

    // ── private helpers ──────────────────────────────────────────

    private PopularAttractionResponse mapToPopularResponse(Attraction a) {
        return new PopularAttractionResponse(
                a.getId(),
                a.getName(),        // → title  (matches frontend item.title)
                a.getLocation(),    // → city   (used for tab filtering)
                a.getDescription(), // → subtitle
                a.getImageUrl(),
                a.getRating()  != null ? a.getRating()  : 0.0,
                a.getReviews() != null ? a.getReviews() : 0
        );
    }

    private AttractionDetailResponse mapToDetailResponse(Attraction a) {
        return new AttractionDetailResponse(
                a.getId(),
                a.getName(),        // → title
                a.getLocation(),    // → city
                a.getDescription(),
                a.getImageUrl(),
                a.getRating()  != null ? a.getRating()  : 0.0,
                a.getReviews() != null ? a.getReviews() : 0
        );
    }
}