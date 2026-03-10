package com.example.demo.controller;

import com.example.demo.model.TravelPackage;
import com.example.demo.repository.TravelPackageRepository;
import com.example.demo.service.TravelPackageService;
import com.example.demo.dto.PriceRequest;
import com.example.demo.dto.PriceResponse;
import com.example.demo.dto.PackageResponse;
import com.example.demo.service.PackageImageService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/packages")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TravelPackageController {

        private final TravelPackageRepository repository;
        private final TravelPackageService service;
        private final PackageImageService imageService;

        @GetMapping("/{id}")
        public ResponseEntity<PackageResponse> getById(@PathVariable UUID id) {

                TravelPackage pkg = repository.findByIdWithImages(id)
                                .orElseThrow(() -> new RuntimeException("Package not found"));

                                List<String> images = imageService.getImagesForDestination(
                        pkg.getToCity().getName()
                );
                PackageResponse response = PackageResponse.builder()
                                .id(pkg.getId())
                                .title(pkg.getFromCity().getName() + " to " + pkg.getToCity().getName())
                                .location(pkg.getToCity().getName())
                                .nights(pkg.getTotalDays())
                                .price(pkg.getPrice())
                                .rating(pkg.getRating() != null ? pkg.getRating() : 4.5)
                                .image(pkg.getBannerImageUrl())
                                .images(images.isEmpty()
                                                ? List.of("/images/packages/default.jpg")
                                                : images)
                                .overview(pkg.getOverview())
                                .build();

                return ResponseEntity.ok(response);
        }

        @PostMapping("/{id}/calculate")
        public PriceResponse calculatePrice(
                        @PathVariable UUID id,
                        @RequestBody PriceRequest request) {
                return service.calculatePrice(id, request.getActivityIds());
        }

}