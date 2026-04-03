package com.yaritrip.backend.controller;

import com.yaritrip.backend.model.TravelPackage;
import com.yaritrip.backend.repository.TravelPackageRepository;
import com.yaritrip.backend.service.TravelPackageService;
import com.yaritrip.backend.dto.PriceRequest;
import com.yaritrip.backend.dto.PriceResponse;
import com.yaritrip.backend.dto.PackageResponse;
import com.yaritrip.backend.service.PackageImageService;
import com.yaritrip.backend.dto.PackageOptionDTO;
import lombok.extern.slf4j.Slf4j;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.Collections;

@Slf4j
@RestController
@RequestMapping("/api/packages")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class TravelPackageController {

        private final TravelPackageRepository repository;
        private final TravelPackageService service;
        private final PackageImageService imageService;

        @GetMapping
        public ResponseEntity<List<PackageResponse>> getAllPackages() {

                List<TravelPackage> packages = repository.findAll();

                List<PackageResponse> response = packages.stream().map(pkg -> {

                        String fromCity = pkg.getFromCity() != null ? pkg.getFromCity().getName() : "Unknown";
                        String toCity = pkg.getToCity() != null ? pkg.getToCity().getName() : "Unknown";

                        List<String> images;
                        try {
                                images = imageService.getImagesForDestination(toCity);
                        } catch (Exception e) {
                                images = Collections.emptyList();
                        }

                        List<PackageOptionDTO> options = (pkg.getOptions() != null && !pkg.getOptions().isEmpty())
                                        ? pkg.getOptions().stream()
                                                        .map(opt -> PackageOptionDTO.builder()
                                                                        .title(opt.getTitle())
                                                                        .price(opt.getPrice())
                                                                        .hotelType(opt.getHotelType())
                                                                        .flightIncluded(opt.isFlightIncluded())
                                                                        .build())
                                                        .toList()
                                        : Collections.emptyList();

                        return PackageResponse.builder()
                                        .id(pkg.getId())
                                        .title(fromCity + " to " + toCity)
                                        .location(toCity)
                                        .nights(pkg.getTotalDays())
                                        .rating(pkg.getRating() != null ? pkg.getRating() : 4.5)
                                        .image(pkg.getBannerImageUrl() != null
                                                        ? "http://localhost:8082" + pkg.getBannerImageUrl()
                                                        : "")
                                        .images(images.isEmpty()
                                                        ? List.of("/images/packages/default.jpg")
                                                        : images)
                                        .overview(pkg.getOverview())
                                        .options(options)
                                        .build();

                }).toList();

                return ResponseEntity.ok(response);
        }

        @PostMapping("/{id}/calculate")
        public PriceResponse calculatePrice(
                        @PathVariable UUID id,
                        @RequestBody PriceRequest request) {
                return service.calculatePrice(id, request.getActivityIds());
        }

        @GetMapping("/{id}")
        public ResponseEntity<PackageResponse> getPackageById(@PathVariable UUID id,
                        @RequestParam(required = false) UUID optionId) {
                // ✅ FIRST FETCH (images)
                TravelPackage pkg = repository.findByIdWithImages(id)
                                .orElseThrow(() -> new RuntimeException("Package not found"));

                // ✅ SECOND FETCH (options)
                TravelPackage optPkg = repository.findByIdWithOptions(id)
                                .orElseThrow(() -> new RuntimeException("Package not found"));

                // ✅ MERGE DATA (CRITICAL)
                pkg.setOptions(optPkg.getOptions());

                String fromCity = pkg.getFromCity() != null ? pkg.getFromCity().getName() : "Unknown";
                String toCity = pkg.getToCity() != null ? pkg.getToCity().getName() : "Unknown";

                List<String> images;
                try {
                        images = imageService.getImagesForDestination(toCity);
                } catch (Exception e) {
                        images = Collections.emptyList();
                }

                List<PackageOptionDTO> options = (pkg.getOptions() != null && !pkg.getOptions().isEmpty())
                                ? pkg.getOptions().stream()
                                                .map(opt -> PackageOptionDTO.builder()
                                                                .title(opt.getTitle())
                                                                .price(opt.getPrice())
                                                                .hotelType(opt.getHotelType())
                                                                .flightIncluded(opt.isFlightIncluded())
                                                                .build())
                                                .toList()
                                : Collections.emptyList();
                // ✅ FILTER ONLY SELECTED OPTION
                if (optionId != null && pkg.getOptions() != null) {
                        pkg.setOptions(
                                        pkg.getOptions().stream()
                                                        .filter(opt -> opt.getId().equals(optionId))
                                                        .toList());
                }

                double startingPrice;

                if (optionId != null && !options.isEmpty()) {
                        startingPrice = options.get(0).getPrice(); // selected option
                } else {
                        startingPrice = options.stream()
                                        .mapToDouble(PackageOptionDTO::getPrice)
                                        .min()
                                        .orElse(0);
                }
                PackageResponse response = PackageResponse.builder()
                                .id(pkg.getId())
                                .title(fromCity + " to " + toCity)
                                .location(toCity)
                                .nights(pkg.getTotalDays())
                                .price(startingPrice)
                                .rating(pkg.getRating() != null ? pkg.getRating() : 4.5)
                                .image(pkg.getBannerImageUrl() != null
                                                ? "http://localhost:8082" + pkg.getBannerImageUrl()
                                                : "")
                                .images(images.isEmpty()
                                                ? List.of("/images/packages/default.jpg")
                                                : images)
                                .overview(pkg.getOverview())
                                .options(options)
                                .build();

                return ResponseEntity.ok(response);
        }
}