package com.yaritrip.backend.controller;

import com.yaritrip.backend.model.City;
import com.yaritrip.backend.model.TravelPackage;
import com.yaritrip.backend.repository.CityRepository;
import com.yaritrip.backend.repository.TravelPackageRepository;
import com.yaritrip.backend.dto.PackageResponse;
import com.yaritrip.backend.service.PackageImageService;
import com.yaritrip.backend.service.TravelPackageService;
import com.yaritrip.backend.model.PackageOption;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import java.util.Comparator;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;
import java.util.Comparator;
import java.util.stream.Collectors;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/packages")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SearchController {

        private final CityRepository cityRepository;
        private final TravelPackageRepository travelPackageRepository;
        private final TravelPackageService service;
        private final PackageImageService imageService;

        // Api to get all cities
        @GetMapping("/cities")
        public List<City> getCities() {
                return cityRepository.findAll();
        }

        // Api to get all Destinations
        @GetMapping("/destinations")
        public List<City> getDestinations(@RequestParam UUID fromCityId) {
                return travelPackageRepository.findDestinationsByFromCity(fromCityId);
        }

        // Api to search packages
        @GetMapping("/search")
        public List<PackageResponse> searchPackages(
                        @RequestParam String fromCode,
                        @RequestParam String toCode,
                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                        @RequestParam int rooms,
                        @RequestParam int guests) {

                List<TravelPackage> packages = service.searchPackages(fromCode, toCode, date, rooms, guests);

                return packages.stream()

                                // REMOVE DUPLICATES
                                .collect(Collectors.toMap(
                                                TravelPackage::getId,
                                                pkg -> pkg,
                                                (existing, duplicate) -> existing))
                                .values()
                                .stream()

                                // ONE PACKAGE = ONE CARD
                                .map(pkg -> {

                                        String toCity = pkg.getToCity().getName();

                                        List<String> images = imageService.getImagesForDestination(toCity);

                                        String imageUrl = (images != null && !images.isEmpty())
                                                        ? "http://localhost:8082" + images.get(0)
                                                        : "http://localhost:8082/images/packages/default.jpg";

                                        double minPrice = pkg.getOptions().stream()
                                                        .mapToDouble(opt -> opt.getPrice())
                                                        .min()
                                                        .orElse(0);

                                        // ITINERARY (example)
                                        List<String> itinerary = List.of(
                                                        "1N " + toCity,
                                                        "2N Hill Stay",
                                                        "1N Resort");

                                        // FEATURES
                                        List<String> features = List.of(
                                                        "Intercity Transfers",
                                                        "3 Star Hotels",
                                                        "Airport Pickup & Drop",
                                                        "Selected Meals");

                                        // HIGHLIGHTS
                                        List<String> highlights = List.of(
                                                        "Boating Experience",
                                                        "Guided Tour",
                                                        "Waterfalls Visit");

                                        return PackageResponse.builder()
                                                        .id(pkg.getId())
                                                        .title("Premium " + toCity + " Deal")
                                                        .location(toCity)
                                                        .nights(pkg.getTotalDays())
                                                        .price(minPrice)
                                                        .rating(pkg.getRating() != null ? pkg.getRating() : 4.5)
                                                        .image(imageUrl)
                                                        .images(images)
                                                        .itinerary(itinerary)
                                                        .features(features)
                                                        .highlights(highlights)
                                                        .optionsCount(pkg.getOptions().size())

                                                        .build();
                                })

                                // SORT BY PRICE
                                .sorted(Comparator.comparingDouble(PackageResponse::getPrice))

                                .toList();
        }
}