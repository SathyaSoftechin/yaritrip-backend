package com.example.demo.controller;

import com.example.demo.model.City;
import com.example.demo.model.TravelPackage;
import com.example.demo.repository.CityRepository;
import com.example.demo.repository.TravelPackageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SearchController {

    private final CityRepository cityRepository;
    private final TravelPackageRepository travelPackageRepository;

    // 1️⃣ Get all cities (From City dropdown)
    @GetMapping("/cities")
    public List<City> getCities() {
        return cityRepository.findAll();
    }

    // 2️⃣ Get destinations based on selected fromCity
    @GetMapping("/destinations")
    public List<City> getDestinations(@RequestParam UUID fromCityId) {
        return travelPackageRepository.findDestinationsByFromCity(fromCityId);
    }

    // 3️⃣ Search packages
    @GetMapping("/packages/search")
    public List<TravelPackage> searchPackages(
            @RequestParam UUID fromCity,
            @RequestParam UUID destination,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam int rooms,
            @RequestParam int guests
    ) {
        return travelPackageRepository.searchPackages(
                fromCity,
                destination,
                date,
                rooms,
                guests
        );
    }
}