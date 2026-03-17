package com.example.demo.service;

import com.example.demo.dto.CreateAttractionPackageRequest;
import com.example.demo.model.Attraction;
import com.example.demo.model.AttractionPackage;
import com.example.demo.repository.AttractionPackageRepository;
import com.example.demo.repository.AttractionRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttractionPackageService {

    private final AttractionPackageRepository repository;
    private final AttractionRepository attractionRepository;

    public AttractionPackage createPackage(CreateAttractionPackageRequest request) {

        List<Attraction> attractions =
                attractionRepository.findAllById(request.getAttractionIds());

        if (attractions.isEmpty()) {
            throw new RuntimeException("No valid attractions found for given IDs");
        }

        AttractionPackage pkg = AttractionPackage.builder()
                .title(request.getTitle())
                .location(request.getLocation())
                .nights(request.getNights())
                .price(request.getPrice())
                .rating(request.getRating())
                .imageUrl(request.getImageUrl())
                .overview(request.getOverview())
                .attractions(attractions)
                .build();

        return repository.save(pkg);
    }

    public AttractionPackage getPackage(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Package not found"));
    }

    public AttractionPackage getPackageByAttractionId(UUID attractionId) {
        return repository.findByAttractionId(attractionId)
                .orElseThrow(() -> new RuntimeException("Package not found for attraction"));
    }

    public List<AttractionPackage> getAll() {
        return repository.findAll();
    }
}